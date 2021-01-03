package ardririy.mineones;

import org.bukkit.plugin.java.JavaPlugin;



public class MineONES extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("timer").setExecutor(new MineORES_timer());
        getCommand("start").setExecutor(new MineORES_timer());
        getCommand("score").setExecutor(new MineORES_timer());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
