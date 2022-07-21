package lee.code.mb;

import lee.code.mb.lists.Loot;
import lee.code.mb.menusystem.PlayerMU;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Data {

    private final ConcurrentHashMap<UUID, PlayerMU> playerMU = new ConcurrentHashMap<>();

    @Getter private final List<ItemStack> lootItems = new ArrayList<>();
    @Getter private final List<UUID> usingBox = new ArrayList<>();

    public void addUsingBox(UUID uuid) { usingBox.add(uuid); }
    public void removeUsingBox(UUID uuid) { usingBox.remove(uuid); }
    public boolean isUsingBox(UUID uuid) { return usingBox.contains(uuid); }

    public PlayerMU getPlayerMU(UUID uuid) {
        if (playerMU.containsKey(uuid)) {
            return playerMU.get(uuid);
        } else {
            PlayerMU pmu = new PlayerMU(uuid);
            playerMU.put(uuid, pmu);
            return pmu;
        }
    }

    public void loadData() {
        lootItems.addAll(EnumSet.allOf(Loot.class).stream().map(Loot::getItem).toList());
    }
}
