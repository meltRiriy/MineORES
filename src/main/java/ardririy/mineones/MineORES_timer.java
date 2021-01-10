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

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MineORES_timer implements CommandExecutor{

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("timer")){
            if(args[0].equalsIgnoreCase("set")){
                int min = Integer.parseInt(args[1]);
                int sec = Integer.parseInt(args[2]) + 5;

                try{
                    FileWriter csv = new FileWriter("timer.csv");
                    PrintWriter pw = new PrintWriter(new BufferedWriter(csv));
                    pw.write("timer" + "," + min + "," + sec);
                    pw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }

                int pre_sec = sec - 5;
                Bukkit.broadcastMessage("タイマーを"+min+"分"+ pre_sec +"秒に設定しました！");

            }else if(args[0].equalsIgnoreCase("start")){

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

                try{
                    FileInputStream fi = new FileInputStream("timer.csv");
                    InputStreamReader is = new InputStreamReader(fi);
                    BufferedReader br = new BufferedReader(is);
                    String line;
                    String[] data ;
                    while ((line = br.readLine()) != null ) {
                        data = line.split(",");

                        String min = data[1];
                        String sec = data[2];
                        final int[] all_sec = {Integer.parseInt(min) * 60 + Integer.parseInt(sec)};

                        Objective objective = board.registerNewObjective("time", "dummy");
                        objective.setDisplayName("Information");
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        Timer timerA = new Timer();
                        TimerTask taskA = new TimerTask() {
                            public void run() {
                                Score score = objective.getScore("残り時間：");
                                score.setScore(all_sec[0]);
                                if (all_sec[0] > 0) {
                                    all_sec[0]--;
                                } else {
                                    Bukkit.getServer().broadcastMessage("終了！");
                                    timerA.cancel();
                                }
                            }
                        };
                        timerA.scheduleAtFixedRate(taskA, 0, 1000);
                    }br.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }else if(cmd.getName().equalsIgnoreCase("score")) {
            Bukkit.getServer().broadcastMessage("***結果発表***");

            int max_score = 0;

            Map<Integer,String> map = new HashMap<>();
            for ( Player player : Bukkit.getOnlinePlayers() ) {
                Inventory inv = player.getInventory();
                player.setScoreboard(board);

                int diamond_amount = 0;
                int iron_amount = 0;
                int gold_amount = 0;
                int coal_amount = 0;
                int emerald_amount = 0;
                int netherrite_amount = 0;
                for (int i = 0, size = inv.getSize(); i < size; ++i){
                    ItemStack item = inv.getItem(i);
                    if(item == null || item.getType() != Material.DIAMOND){
                        continue;
                    }
                    diamond_amount += item.getAmount();
                }
                for (int i = 0, size = inv.getSize(); i < size; ++i){
                    ItemStack item = inv.getItem(i);
                    if(item == null || item.getType() != Material.IRON_INGOT){
                        continue;
                    }
                    iron_amount += item.getAmount();
                }
                for (int i = 0, size = inv.getSize(); i < size; ++i){
                    ItemStack item = inv.getItem(i);
                    if(item == null || item.getType() != Material.GOLD_INGOT){
                        continue;
                    }
                    gold_amount += item.getAmount();
                }
                for (int i = 0, size = inv.getSize(); i < size; ++i){
                    ItemStack item = inv.getItem(i);
                    if(item == null || item.getType() != Material.COAL){
                        continue;
                    }
                    coal_amount += item.getAmount();
                }
                for (int i = 0, size = inv.getSize(); i < size; ++i){
                    ItemStack item = inv.getItem(i);
                    if(item == null || item.getType() != Material.EMERALD){
                        continue;
                    }
                    emerald_amount += item.getAmount();
                }
                for (int i = 0, size = inv.getSize(); i < size; ++i){
                    ItemStack item = inv.getItem(i);
                    if(item == null || item.getType() != Material.NETHERITE_INGOT){
                        continue;
                    }
                    netherrite_amount += item.getAmount();
                }


                int player_score = diamond_amount*20 + iron_amount*5 + gold_amount*15 + coal_amount +emerald_amount*30 + netherrite_amount*10;

                Bukkit.getServer().broadcastMessage(player.getDisplayName()+":"+player_score+"ポイント");

                if(player_score > max_score){
                    max_score = player_score;
                    String first_place = player.getDisplayName();
                    map.put(max_score,first_place);
                }

            }
            Bukkit.broadcastMessage("------------------------------");
            Bukkit.broadcastMessage("\n優勝は"+map.get(max_score)+"さんでした！");
        }return false;
    }
}
