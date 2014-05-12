package me.RyanWild.CJFreedomMod.Listener;

import me.RyanWild.CJFreedomMod.CJFM_Util;
import me.StevenLawson.TotalFreedomMod.TFM_AdminList;
import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class CJFM_PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public static void onPlayerJoinEvent(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (CJFM_Util.SYSADMINS.contains(player.getName()))
        {
            player.setPlayerListName(ChatColor.DARK_RED + player.getName());
            TFM_PlayerData.getPlayerData(player).setTag("&8[&4System-Admin&8]");
        }
        else if (CJFM_Util.EXECUTIVES.contains(player.getName()))
        {
            player.setPlayerListName(ChatColor.BLUE + player.getName());
            TFM_PlayerData.getPlayerData(player).setTag("&8[&9Executive&8]");
        }
        else if (CJFM_Util.DEVELOPERS.contains(player.getName()))
        {
            player.setPlayerListName(ChatColor.DARK_PURPLE + player.getName());
            TFM_PlayerData.getPlayerData(player).setTag("&8[&5Developer&8]");
        }
        else if (TFM_AdminList.isSeniorAdmin(player))
        {
            player.setPlayerListName(ChatColor.LIGHT_PURPLE + player.getName());
            TFM_PlayerData.getPlayerData(player).setTag("&8[&dSenior Admin&8]");
        }
        else if (TFM_AdminList.isTelnetAdmin(player, true))
        {
            player.setPlayerListName(ChatColor.GREEN + player.getName());
            TFM_PlayerData.getPlayerData(player).setTag("&8[&2Telnet Admin&8]");
        }
        else if (TFM_AdminList.isSuperAdmin(player))
        {
            player.setPlayerListName(ChatColor.AQUA + player.getName());
            TFM_PlayerData.getPlayerData(player).setTag("&8[&BSuper Admin&8]");
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        Player player = event.getPlayer();
        String command = event.getMessage().toLowerCase().trim();
        
        if (TFM_AdminList.isSuperAdmin(player))
        {
            for (Player pl : Bukkit.getOnlinePlayers())
            {
                if (TFM_AdminList.isSeniorAdmin(pl) && pl != player)
                {
                    TFM_Util.playerMsg(pl, player.getName() + ": " + command);
                }
            }
        }
    }
}