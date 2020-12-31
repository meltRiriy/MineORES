package ardririy.mineones;

import org.bukkit.Server;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MineORES_timer implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("timer")){
            if(args.length !=0){
                int min = Integer.parseInt(args[0]);
                int sec = Integer.parseInt(args[1]);
            }
            return false;
        }else if(cmd.getName().equalsIgnoreCase("start")) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int preCount = 5;
                public void run() {
                    if(preCount >0){
                        Bukkit.getServer().broadcastMessage(String.valueOf(preCount)+"...");
                        preCount--;
                    }else{
                        Bukkit.getServer().broadcastMessage("start!");
                        timer.cancel();
                    }
                }
            };timer.scheduleAtFixedRate(task, 0, 1000);
        }
        return false;
    }
}
