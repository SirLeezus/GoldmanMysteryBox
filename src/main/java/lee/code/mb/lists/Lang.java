package lee.code.mb.lists;

import lee.code.core.util.bukkit.BukkitUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
    PREFIX("&5&lMysteryBox &6➔ &r"),
    USAGE("&6&lUsage: &e{0}"),
    REWARDED_MYSTERY_BOX("&dYou have received &a{0} &5Mystery Box&d!"),
    ERROR_PLAYER_NOT_ONLINE("&cThe player &6{0} &cis not online."),
    ERROR_NOT_CONSOLE_COMMAND("&cThis command does not work in console."),
    ERROR_PLAYER_NOT_FOUND("&cThe player &6{0} &ccould not be found."),
    MENU_MYSTERY_BOX_TITLE("&5&lMystery Box"),
    ERROR_USING_BOX("&cYou'll have to wait until you finish opening your current mystery box."),
    MYSTERY_BOX_REWARD("&dYou have been rewarded {0}&d!"),
    ;

    @Getter private final String string;

    public String getString(String[] variables) {
        String value = string;
        if (variables == null || variables.length == 0) return BukkitUtils.parseColorString(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return BukkitUtils.parseColorString(value);
    }

    public Component getComponent(String[] variables) {
        String value = string;
        if (variables == null || variables.length == 0) return BukkitUtils.parseColorComponent(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return BukkitUtils.parseColorComponent(value);
    }
}
