package me.tvhee.custommotd.spigot.commands;

import me.tvhee.api.bukkit.chat.ChatUtils;

import me.tvhee.custommotd.spigot.updater.Updater;
import me.tvhee.custommotd.spigot.util.ConfigFiles;
import me.tvhee.custommotd.spigot.CustomMOTDPlugin;
import me.tvhee.custommotd.spigot.util.Messages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMCommand implements CommandExecutor
{
    private CustomMOTDPlugin plugin = CustomMOTDPlugin.getInstance();

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args)
    {
        if(args.length == 0)
        {
            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu1));
            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu2) + plugin.getDescription().getVersion());
            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu3));
            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu4) + " tvhee");
            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.pluginMenu5));
            return true;
        }
        else
        {
            if(args[0].equalsIgnoreCase("help"))
            {
                sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu1));
                sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu2));
                sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu3));
                sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu4));
                sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu5));
                sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.helpMenu6));
                return true;
            }
            else if(args[0].equalsIgnoreCase("reload"))
            {
                if(sender.hasPermission("custommotd.reload"))
                {
                    plugin.reloadConfig();
                    ConfigFiles.reloadMessagesConfig();
                    sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.commandReload));

                    if(CustomMOTDPlugin.getInstance().getConfig().getBoolean("plugin.update-check"))
                    {
                        if(Updater.getInstance().updateAvailable())
                        {
                            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable).replaceAll("%version%", String.valueOf(Updater.getInstance().getCurrentVersion())).replaceAll("%new-version%", String.valueOf(Updater.getInstance().getNewestVersion())));
                            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable2));
                            return true;
                        }
                        else
                        {
                            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noUpdateAvailable));
                            return true;
                        }
                    }
                    else
                    {
                        sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateCheckDeactivated));
                        return true;
                    }
                }
                else
                {
                    sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.commandNoPermission));
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("update"))
            {
                if(sender.hasPermission("custommotd.updates"))
                {
                    if(CustomMOTDPlugin.getInstance().getConfig().getBoolean("plugin.update-check"))
                    {
                        if(Updater.getInstance().updateAvailable())
                        {
                            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable).replaceAll("%version%", String.valueOf(Updater.getInstance().getCurrentVersion())).replaceAll("%new-version%", String.valueOf(Updater.getInstance().getNewestVersion())));
                            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateAvailable2));
                            return true;
                        }
                        else
                        {
                            sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.noUpdateAvailable));
                            return true;
                        }
                    }
                    else
                    {
                        sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.updateCheckDeactivated));
                        return true;
                    }
                }
                else
                {
                    sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.commandNoPermission));
                    return true;
                }
            }
            else
            {
                sender.sendMessage(ChatUtils.format(Messages.igprefix + Messages.commandNotFound));
                return true;
            }
        }
    }
}