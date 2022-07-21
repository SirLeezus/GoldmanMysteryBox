package lee.code.mb.lists;

import lee.code.core.util.bukkit.BukkitUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum MysteryBox {
    MYSTERY_BOX("&5&lMystery Box", "&e> &aTo use this item right-click it!", 1337);

    @Getter private final String name;
    @Getter private final String lore;
    @Getter private final int id;

    public ItemStack getItem() {
        return BukkitUtils.getCustomItem(Material.GUNPOWDER, name, lore, id, true);
    }
}
