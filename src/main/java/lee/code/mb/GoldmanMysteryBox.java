package lee.code.mb;

import lee.code.mb.commands.cmds.GiveMysteryBoxCMD;
import lee.code.mb.commands.tabs.GiveMysteryBoxTab;
import lee.code.mb.listeners.MenuListener;
import lee.code.mb.listeners.MysteryBoxListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class GoldmanMysteryBox extends JavaPlugin {

    @Getter private Data data;

    @Override
    public void onEnable() {
        this.data = new Data();
        registerListeners();
        registerCommands();

        data.loadData();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new MysteryBoxListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    private void registerCommands() {
        getCommand("givemysterybox").setExecutor(new GiveMysteryBoxCMD());
        getCommand("givemysterybox").setTabCompleter(new GiveMysteryBoxTab());
    }

    public static GoldmanMysteryBox getPlugin() {
        return GoldmanMysteryBox.getPlugin(GoldmanMysteryBox.class);
    }
}
