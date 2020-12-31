package ardririy.mineones;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


public final class MineONES extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("timer").setExecutor(new MineORES_timer());
        getCommand("start").setExecutor(new MineORES_timer());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
