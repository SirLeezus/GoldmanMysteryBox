package lee.code.mb.menusystem.menu;

import lee.code.mb.Data;
import lee.code.mb.GoldmanMysteryBox;
import lee.code.mb.lists.Lang;
import lee.code.mb.lists.Loot;
import lee.code.mb.menusystem.Menu;
import lee.code.mb.menusystem.PlayerMU;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MysteryBoxMenu extends Menu {

    public MysteryBoxMenu(PlayerMU pmu) {
        super(pmu);
    }

    @Override
    public Component getMenuName() {
        return Lang.MENU_MYSTERY_BOX_TITLE.getComponent(null);
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {
        GoldmanMysteryBox plugin = GoldmanMysteryBox.getPlugin();
        Data data = plugin.getData();
        setFillerGlass();
        Player player = pmu.getOwner();

        player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK, (float) 3, (float) 1);

        inventory.setItem(4, prizePointer);
        inventory.setItem(22, prizePointer);

        List<ItemStack> items = data.getLootItems();
        Collections.shuffle(items);
        int startIndex = ThreadLocalRandom.current().nextInt(items.size());
        for (int index = 0; index < startIndex; index++) {
            for (int itemStacks = 9; itemStacks < 18; itemStacks++){
                inventory.setItem(itemStacks, items.get((itemStacks + pmu.getItemIndex()) % items.size()));
            }
            pmu.setItemIndex(pmu.getItemIndex() + 1);
        }

        Random random = new Random();
        double seconds = 7.0 + (12.0 - 7.0) * random.nextDouble();

        new BukkitRunnable() {
            double delay = 0;
            int ticks = 0;
            boolean done = false;

            @Override
            public void run() {
                if (done) return;

                ticks++;
                delay += 1 / (20 * seconds);
                if (ticks > delay * 10) {
                    ticks = 0;

                    for (int itemStacks = 9; itemStacks < 18; itemStacks++) {
                        inventory.setItem(itemStacks, items.get((itemStacks + pmu.getItemIndex()) % items.size()));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, (float) 0.1, (float) 2);
                    }
                    pmu.setItemIndex(pmu.getItemIndex() + 1);

                    if (delay >= .5) {
                        done = true;
                        new BukkitRunnable() {
                            public void run() {
                                ItemStack item = inventory.getItem(13);
                                if (item != null) {
                                    Loot loot = getLootKey(item);
                                    if (loot != null) {
                                        String[] commands = loot.getCommands();
                                        for (String command : commands) {
                                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                                            String run = command.replaceAll("%player%", player.getName());
                                            Bukkit.dispatchCommand(console, run);
                                        }
                                        data.removeUsingBox(player.getUniqueId());
                                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, (float) 1, (float) 1);
                                        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MYSTERY_BOX_REWARD.getComponent(new String[] { loot.getName() })));
                                    }
                                }
                            }
                        }.runTaskLater(plugin, 30);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }



    private Loot getLootKey(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(GoldmanMysteryBox.getPlugin(), "key");
        String loot = dataContainer.get(key, PersistentDataType.STRING);
        if (loot != null) return Loot.valueOf(loot);
        else return null;
    }
}
