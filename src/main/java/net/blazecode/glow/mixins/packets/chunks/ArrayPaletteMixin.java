package net.blazecode.glow.mixins.packets.chunks;

import net.blazecode.glow.api.simulated.proxies.GlowBlockState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.ArrayPalette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin( ArrayPalette.class)
public abstract class ArrayPaletteMixin
{
    @ModifyArg( method = "writePacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;writeVarInt(I)Lnet/minecraft/network/PacketByteBuf;", ordinal = 1), index = 0 )
    private int proxyBlockIDInjection(int original)
    {
        BlockState state = Block.getStateFromRawId( original );
        Block blk = state.getBlock();

        if( blk instanceof GlowBlockState )
        {
            return Block.getRawIdFromState( ((GlowBlockState)blk).getSimulatedBlockState( state ) );
        }

        return original;
    }
}
