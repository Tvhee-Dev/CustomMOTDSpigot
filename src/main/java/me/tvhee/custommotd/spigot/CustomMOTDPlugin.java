package me.tvhee.custommotd.spigot;

import me.tvhee.api.bukkit.files.CustomFile;
import me.tvhee.custommotd.spigot.commands.CMCommand;
import me.tvhee.custommotd.spigot.listeners.v1_16_R3.PingListener;
import me.tvhee.custommotd.spigot.listeners.PlayerJoin;
import me.tvhee.custommotd.spigot.listeners.ServerListPing;
import me.tvhee.custommotd.spigot.util.ConfigFiles;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomMOTDPlugin extends JavaPlugin
{
    private static CustomMOTDPlugin instance;

    @Override
    public void onEnable()
    {
        if(Bukkit.getServer().getPluginManager().getPlugin("tvheeAPI") == null)
        {
            getLogger().warning("Error: Could not find API, disabling...");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        setInstance(this);
        ConfigFiles.saveDefaultMessagesConfig();
        this.saveDefaultConfig();
        new CustomFile(this, "/favicons/default.png").saveDefaultFile();

        Bukkit.getServer().getPluginCommand("cm").setExecutor(new CMCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new ServerListPing(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        try
        {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            String version = name.substring(name.lastIndexOf('.') + 1);
            Class<?> event = Class.forName("me.tvhee.custommotd.spigot.listeners." + version + ".PingListener");
            Bukkit.getServer().getPluginManager().registerEvents((Listener) event.newInstance(), CustomMOTDPlugin.getInstance());
        }
        catch(ClassNotFoundException | IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }

        Bukkit.getServer().getPluginManager().registerEvents(new PingListener(), this);

        this.getLogger().info("has been enabled!");
        this.getLogger().info("made by tvhee");
    }

    public static void setInstance(CustomMOTDPlugin instance)
    {
        CustomMOTDPlugin.instance = instance;
    }

    public static CustomMOTDPlugin getInstance()
    {
        return instance;
    }

    @Override
    public void onDisable()
    {
        this.getLogger().info("made by tvhee");
        this.getLogger().info("has been disabled!");
    }
}