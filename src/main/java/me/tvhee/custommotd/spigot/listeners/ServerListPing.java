package me.tvhee.custommotd.spigot.listeners;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.custommotd.spigot.CustomMOTDPlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.File;

public class ServerListPing implements Listener
{
    @EventHandler
    public void onPing(ServerListPingEvent e)
    {
        if(CustomMOTDPlugin.getInstance().getConfig().getBoolean("plugin.motd.enabled"))
        {
            String motd;
            if(CustomMOTDPlugin.getInstance().getConfig().getBoolean("plugin.motd.center"))
            {
                motd = ChatUtils.format(ChatUtils.centerText(CustomMOTDPlugin.getInstance().getConfig().getString("plugin.motd.line1"), 125) + "§r\n" + ChatUtils.centerText(CustomMOTDPlugin.getInstance().getConfig().getString("plugin.motd.line2"), 125));
            }
            else
            {
                motd = ChatUtils.format(CustomMOTDPlugin.getInstance().getConfig().getString("plugin.motd.line1") + "§r\n" + CustomMOTDPlugin.getInstance().getConfig().getString("plugin.motd.line2"));
            }

            e.setMotd(motd);
        }

        if(CustomMOTDPlugin.getInstance().getConfig().getBoolean("plugin.favicon.enabled"))
        {
            File favicon = new File(CustomMOTDPlugin.getInstance().getDataFolder(), "/favicons/" + CustomMOTDPlugin.getInstance().getConfig().getString("plugin.favicon.file"));

            try
            {
                e.setServerIcon(Bukkit.loadServerIcon(favicon));
            }
            catch(Exception ex)
            {
                CustomMOTDPlugin.getInstance().getLogger().warning("There was an problem with converting the favicon");
                ex.printStackTrace();
            }
        }
    }
}