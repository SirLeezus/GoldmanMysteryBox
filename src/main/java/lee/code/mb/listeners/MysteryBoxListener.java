package lee.code.mb.listeners;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.mb.Data;
import lee.code.mb.GoldmanMysteryBox;
import lee.code.mb.lists.Lang;
import lee.code.mb.menusystem.menu.MysteryBoxMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MysteryBoxListener implements Listener {

    @EventHandler
    public void onMysteryBox(PlayerInteractEvent e) {
        GoldmanMysteryBox plugin = GoldmanMysteryBox.getPlugin();
        Data data = plugin.getData();
        Player player = e.getPlayer();
        ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
        if (handItem.hasItemMeta() && handItem.getItemMeta().hasCustomModelData()) {
            if (handItem.getItemMeta().getCustomModelData() == 1337 && e.getAction().isRightClick()) {
                if (BukkitUtils.hasClickDelay(player)) return;
                e.setCancelled(true);
                UUID uuid = player.getUniqueId();
                if (!data.isUsingBox(uuid)) {
                    data.addUsingBox(uuid);
                    BukkitUtils.removePlayerItems(player, handItem, 1, true);
                    new MysteryBoxMenu(data.getPlayerMU(uuid)).open();
                } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_USING_BOX.getComponent(null)));
            }
        }
    }

    @EventHandler
    public void onAnvilRenameDeny(InventoryClickEvent e) {
        if (e.getInventory() instanceof AnvilInventory anvilInventory) {
            if (e.getSlotType() != InventoryType.SlotType.RESULT) return;
            ItemStack[] contents = anvilInventory.getContents();
            ItemStack firstSlot = contents[0];
            if (firstSlot!= null) {
                if (firstSlot.getItemMeta().hasCustomModelData()) {
                    if (firstSlot.getItemMeta().getCustomModelData() == 1337) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}