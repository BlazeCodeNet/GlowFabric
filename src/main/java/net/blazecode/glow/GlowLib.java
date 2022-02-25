package net.blazecode.glow;

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
	public static final String VERSION = "0.0.2";
	public static final Logger LOGGER = Logger.getLogger( MODID );

	@Override
	public void onInitializeServer( )
	{
		CommandRegistrationCallback.EVENT.register( ( dispatcher, dedicated) ->
		{
			if(dedicated)
			{
				GlowCommand.register(dispatcher);
			}
		});
	}
}
