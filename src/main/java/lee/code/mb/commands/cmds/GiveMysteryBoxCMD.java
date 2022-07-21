package lee.code.mb.commands.cmds;

import lee.code.core.util.bukkit.BukkitUtils;
import lee.code.mb.lists.Lang;
import lee.code.mb.lists.MysteryBox;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveMysteryBoxCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length > 0) {
            int amount = 1;
            if (args.length > 1) {
                String amountString = args[1].toUpperCase();
                if (BukkitUtils.containOnlyNumbers(amountString)) {
                    amount = Integer.parseInt(amountString);
                }
            }

            String targetString = args[0].toUpperCase();
            OfflinePlayer oTarget = Bukkit.getOfflinePlayerIfCached(targetString);
            if (oTarget != null) {
                if (oTarget.isOnline()) {
                    Player target = oTarget.getPlayer();
                    if (target != null) {
                        BukkitUtils.givePlayerItem(target, MysteryBox.MYSTERY_BOX.getItem(), amount);
                        target.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.REWARDED_MYSTERY_BOX.getComponent(new String[] { String.valueOf(amount) })));
                    } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(null)));
                } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(null)));
            } else sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_ONLINE.getComponent(null)));
        } else sender.sendMessage(Lang.USAGE.getComponent(new String[] { command.getUsage() }));
        return true;
    }
}
