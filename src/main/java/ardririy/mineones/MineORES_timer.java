package ardririy.mineones;

import org.bukkit.Server;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Timer;
import java.util.TimerTask;

public class MineORES_timer implements CommandExecutor{
    int min = 30;
    int sec = 0;
    int all_sec = min*60 + sec;
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

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        for ( Player player : Bukkit.getOnlinePlayers() ) {
            player.setScoreboard(board);
        }

        Objective objective = board.registerNewObjective("time", "dummy");
        objective.setDisplayName("Information");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Timer timerA = new Timer();
        TimerTask taskA = new TimerTask() {
            public void run() {
                Score score = objective.getScore("残り時間：");
                score.setScore(all_sec);
                if(all_sec > 0){
                    all_sec--;
                }else{
                    Bukkit.getServer().broadcastMessage("終了！");
                    timerA.cancel();
                }
            }
        };timerA.scheduleAtFixedRate(taskA,0,1000);
        return false;
    }
}
