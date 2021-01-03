package ardririy.mineones;

import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;

import java.util.Timer;
import java.util.TimerTask;

public class MineORES_timer implements CommandExecutor{
    int min = 30;
    int sec = 0;
    int all_sec = min*60 + sec;

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();
    Objective objective = board.registerNewObjective("Information", "dummy");


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("timer")){
            if(args.length !=0){
                int min = Integer.parseInt(args[0]);
                int sec = Integer.parseInt(args[1]);
            }
            return false;
        }else if(cmd.getName().equalsIgnoreCase("score")) {
            Bukkit.getServer().broadcastMessage("***結果発表***");

            for ( Player player : Bukkit.getOnlinePlayers() ) {
                Inventory inv = player.getInventory();
                player.setScoreboard(board);
                int diamond_amount = 0;
                for(int i = 1; inv.contains(Material.DIAMOND)==true; ++i){
                    if(inv.contains(new ItemStack(Material.DIAMOND,i)) == true) {
                        diamond_amount = diamond_amount + i;
                        int diamond_place = inv.first(new ItemStack(Material.DIAMOND,i));
                        inv.clear(diamond_place);
                        i = 1;
                    }else{
                        continue;
                    }
                }
                int iron_amount = 0;
                for(int i2 = 1;inv.contains(Material.IRON_INGOT)==true;++i2){
                    if(inv.contains(new ItemStack(Material.IRON_INGOT,i2)) == true) {
                        iron_amount = iron_amount + i2;
                        int diamond_place = inv.first(new ItemStack(Material.IRON_INGOT,i2));
                        inv.clear(diamond_place);
                        i2 = 1;
                    }else{
                        continue;
                    }
                }
                int gold_amount = 0;
                for(int i3 = 1;inv.contains(Material.GOLD_INGOT)==true;++i3){
                    if(inv.contains(new ItemStack(Material.GOLD_INGOT,i3)) == true) {
                        gold_amount = gold_amount + i3;
                        int diamond_place = inv.first(new ItemStack(Material.GOLD_INGOT,i3));
                        inv.clear(diamond_place);
                        i3 = 1;
                    }else{
                        continue;
                    }
                }
                int coal_amount = 0;
                for(int i4 = 1;inv.contains(Material.COAL)==true;++i4){
                    if(inv.contains(new ItemStack(Material.COAL,i4)) == true) {
                        coal_amount = coal_amount + i4;
                        int diamond_place = inv.first(new ItemStack(Material.COAL,i4));
                        inv.clear(diamond_place);
                        i4 = 1;
                    }else{
                        continue;
                    }
                }
                int emerald_amount = 0;
                for(int i5 = 1;inv.contains(Material.EMERALD)==true;++i5){
                    if(inv.contains(new ItemStack(Material.EMERALD,i5)) == true) {
                        emerald_amount = emerald_amount + i5;
                        int diamond_place = inv.first(new ItemStack(Material.EMERALD,i5));
                        inv.clear(diamond_place);
                        i5 = 1;
                    }else{
                        continue;
                    }
                }
                int netherrite_amount = 0;
                for(int i6 = 1;inv.contains(Material.COAL)==true;++i6){
                    if(inv.contains(new ItemStack(Material.COAL,i6)) == true) {
                        netherrite_amount = netherrite_amount + i6;
                        int diamond_place = inv.first(new ItemStack(Material.NETHERITE_INGOT,i6));
                        inv.clear(diamond_place);
                        i6 = 1;
                    }else{
                        continue;
                    }
                }
                int player_score = diamond_amount*20 + iron_amount*5 + gold_amount*15 + coal_amount*1 +emerald_amount*30 + netherrite_amount*10;

                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    int cnt = 0;
                    @Override
                    public void run() {
                        cnt += 1 ;
                        if(cnt==1){
                            timer.cancel();
                        }
                    }
                };
                timer.schedule(task,0,100);

                Bukkit.getServer().broadcastMessage(player.getDisplayName()+":"+player_score+"ポイント");

                objective.setDisplayName("***points***");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                Score score = objective.getScore(player.getDisplayName());
                score.setScore(player_score);
            }
        }else if(cmd.getName().equalsIgnoreCase("start")) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int preCount = 5;

                public void run() {
                    if (preCount > 0) {
                        Bukkit.getServer().broadcastMessage(String.valueOf(preCount) + "...");
                        preCount--;
                    } else {
                        Bukkit.getServer().broadcastMessage("start!");
                        timer.cancel();
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, 1000);


            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();

            for (Player player : Bukkit.getOnlinePlayers()) {
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
                    if (all_sec > 0) {
                        all_sec--;
                    } else {
                        Bukkit.getServer().broadcastMessage("終了！");
                        timerA.cancel();
                    }
                }
            };
            timerA.scheduleAtFixedRate(taskA, 0, 1000);
        }return false;
    }
}
