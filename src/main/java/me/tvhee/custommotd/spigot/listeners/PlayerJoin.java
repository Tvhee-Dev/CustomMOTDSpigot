package me.tvhee.custommotd.spigot.listeners;

import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.custommotd.spigot.CustomMOTDPlugin;
import me.tvhee.custommotd.spigot.updater.Updater;
import me.tvhee.custommotd.spigot.util.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        if(player.hasPermission("custommotd.updates"))
        {
            if(CustomMOTDPlugin.getInstance().getConfig().getBoolean("plugin.update-check"))
            {
                if(Updater.getInstance().updateAvailable())
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable).replaceAll("%version%", String.valueOf(Updater.getInstance().getCurrentVersion())).replaceAll("%new-version%", String.valueOf(Updater.getInstance().getNewestVersion())));
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable2));
                    return;
                }
                else
                {
                    player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noUpdateAvailable));
                    return;
                }
            }
            else
            {
                player.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateCheckDeactivated));
                return;
            }
        }
    }
}
