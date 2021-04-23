package me.tvhee.custommotd.spigot.listeners.v1_15_R1;

import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import me.tvhee.api.bukkit.chat.ChatUtils;
import me.tvhee.api.bukkit.reflection.ReflectedObject;
import me.tvhee.custommotd.spigot.CustomMOTDPlugin;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class PingListener implements Listener
{
    private Gson a;

    public PingListener()
    {
        a = new GsonBuilder().registerTypeAdapter(ServerPing.ServerData.class, new OverriddenServerDataSerializer())
                .registerTypeAdapter(ServerPing.ServerPingPlayerSample.class, new OverriddenServerSampleSerializer())
                .registerTypeAdapter(ServerPing.class, new ServerPingEvent())
                .registerTypeHierarchyAdapter(IChatBaseComponent.class, new IChatBaseComponent.ChatSerializer())
                .registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer())
                .registerTypeAdapterFactory(new ChatTypeAdapterFactory()).create();
        ReflectedObject.setStaticField(PacketStatusOutServerInfo.class, "a", a);
    }

    private static GameProfile[] convertHover(List<String> toConvertHover)
    {
        if(toConvertHover == null)
        {
            return null;
        }

        GameProfile[] hover = new GameProfile[toConvertHover.size()];

        int index = 0;
        for(String s : toConvertHover)
        {
            GameProfile hoverLine = new GameProfile(new UUID(0, 0), ChatUtils.format(s));
            hover[index] = hoverLine;
            index ++;
        }
        return hover;
    }

    public static class ServerPingEvent extends ServerPing.Serializer
    {
        @Override
        public JsonElement serialize(ServerPing serverPing, Type type, JsonSerializationContext jsonSerializationContext)
        {
            ReflectedObject ping = new ReflectedObject(serverPing);
            ReflectedObject playerSample = ping.get("b");

            if(CustomMOTDPlugin.getInstance().getConfig().getBoolean("plugin.hover.enabled"))
            {
                playerSample.setField("c", convertHover(CustomMOTDPlugin.getInstance().getConfig().getStringList("plugin.hover.text")));
            }

            ReflectedObject serverData = ping.get("c");

            if(CustomMOTDPlugin.getInstance().getConfig().getBoolean("plugin.player-format.enabled"))
            {
                serverData.setField("a", CustomMOTDPlugin.getInstance().getConfig().getString("plugin.player-format.text").replaceAll("%online-players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size())).replaceAll("%max-players%", String.valueOf(Bukkit.getServer().getMaxPlayers())));
            }

            return super.serialize(serverPing, type, jsonSerializationContext);
        }
    }

    public static class OverriddenServerDataSerializer extends ServerPing.ServerData.Serializer
    {
        @Override
        public JsonElement serialize(ServerPing.ServerData serverData, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return super.serialize(serverData, type, jsonSerializationContext);
        }
    }

    public static class OverriddenServerSampleSerializer extends ServerPing.ServerPingPlayerSample.Serializer
    {
        @Override
        public JsonElement serialize(ServerPing.ServerPingPlayerSample serverPingPlayerSample, Type type, JsonSerializationContext jsonSerializationContext)
        {
            JsonObject jsonObject = (JsonObject) super.serialize(serverPingPlayerSample, type, jsonSerializationContext);
            return jsonObject;
        }
    }
}