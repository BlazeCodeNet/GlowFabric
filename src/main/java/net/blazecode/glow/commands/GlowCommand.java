package net.blazecode.glow.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.blazecode.glow.GlowLib;
import net.blazecode.glow.api.GlowUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GlowCommand
{
    public static void register( CommandDispatcher<ServerCommandSource> dispatcher)
    {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("glow")
                .requires( src -> src.hasPermissionLevel(3))
                .then(literal("setmodeldata")
                        .then( argument("id", IntegerArgumentType.integer(1, 256))
                                .executes( ctx -> executeSetModelData( ctx, IntegerArgumentType.getInteger(ctx, "id")))))
                .then(literal("head")
                        .then(argument( "id", StringArgumentType.string() )
                                .executes( ctx -> executeGiveHead(ctx, StringArgumentType.getString( ctx, "id" ))  )))
                .then(literal( "?" )
                        .executes( ctx -> executeHelp( ctx ) ))
                .then(literal("help")
                        .executes( ctx -> executeHelp( ctx ) ))
                .executes( ctx -> executeInfo(ctx) );

        dispatcher.register(builder);
    }

    private static int executeInfo( CommandContext<ServerCommandSource> ctx )
    {
        ctx.getSource().sendFeedback( GlowUtils.getText( "-= Glow Library =-", Formatting.YELLOW ), false );
        ctx.getSource().sendFeedback( GlowUtils.getText( "Version: v" + GlowLib.VERSION, Formatting.YELLOW ), false );
        MutableText linkText = GlowUtils.getLinkedText( GlowUtils.getText( "GitHub", Formatting.AQUA ), "https://github.com/BlazeCodeNet/GlowFabric" );
        ctx.getSource().sendFeedback( GlowUtils.getText( "Author: BlazeCode [", Formatting.YELLOW).append( linkText ).append( GlowUtils.getText( "]", Formatting.YELLOW ) ), false );

        return Command.SINGLE_SUCCESS;
    }

    private static int executeHelp( CommandContext<ServerCommandSource> ctx )
    {
        ctx.getSource().sendFeedback( GlowUtils.getText( "-= Glow Help =-", Formatting.YELLOW ), false );
        ctx.getSource().sendFeedback( GlowUtils.getText( "/glow : Shows data about GlowLibrary", Formatting.YELLOW ), false );
        ctx.getSource().sendFeedback( GlowUtils.getText( "/glow head <head id> : Gives a playerhead with the raw id given.", Formatting.YELLOW ), false );
        ctx.getSource().sendFeedback( GlowUtils.getText( "/glow setmodeldata <number> : Sets held item's model data number to the given number.", Formatting.YELLOW ), false );

        return Command.SINGLE_SUCCESS;
    }

    private static int executeGiveHead( CommandContext<ServerCommandSource> ctx , String id ) throws CommandSyntaxException
    {
        ServerPlayerEntity plr = ctx.getSource().getPlayer();

        ItemStack headStack = GlowUtils.getHeadFromRawId( id );
        headStack.setCount( 1 );
        plr.giveItemStack( headStack );

        ctx.getSource().sendFeedback( GlowUtils.getText( "Head was successfully spawned.", Formatting.GREEN ), false );

        return Command.SINGLE_SUCCESS;
    }

    public static int executeSetModelData(CommandContext<ServerCommandSource> ctx, int id) throws CommandSyntaxException
    {
        ServerPlayerEntity srvPlr = ctx.getSource().getPlayer();

        ItemStack handStack = srvPlr.getInventory().getMainHandStack();
        if(handStack != ItemStack.EMPTY)
        {
            GlowUtils.setStackModelData(handStack, id);
            ctx.getSource().sendFeedback(GlowUtils.getText("Set Held Stack's CustomModelData to " + id, Formatting.GREEN), false);
        }

        return Command.SINGLE_SUCCESS;
    }

}
