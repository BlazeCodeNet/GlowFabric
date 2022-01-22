package net.blazecode.glow;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.blazecode.glow.commands.GlowCommand;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

import java.util.logging.Logger;

@Environment( EnvType.SERVER )
public class GlowLib implements DedicatedServerModInitializer
{
	public static final String MODID = "glow";
	public static final String VERSION = "0.0.1";
	public static final Logger LOGGER = Logger.getLogger( MODID );

	@Override
	public void onInitializeServer( )
	{
		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		CommandRegistrationCallback.EVENT.register( ( dispatcher, dedicated) ->
		{
			if(dedicated)
			{
				GlowCommand.register(dispatcher);
			}
		});
	}

	public static ModConfig getConfig()
	{
		if (config == null)
		{
			config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
		}
		return config;
	}

	private static ModConfig config;

	@Config(name = MODID)
	public static class ModConfig implements ConfigData
	{
		@Comment("Toggles the entire mod on or off - NOT IMPLEMENTED YET!")
		boolean enabled = true;
	}
}
