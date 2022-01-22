package net.blazecode.glow.mixins.packets.sim.generic;

import net.blazecode.glow.api.simulated.proxies.GlowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin( PacketByteBuf.class)
public class StackSerializerMixin
{
    @ModifyVariable(method = "writeItemStack(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/network/PacketByteBuf;", at = @At("HEAD"), ordinal = 0, argsOnly = true )
    private ItemStack proxyItemStack( ItemStack stack)
    {
        if(stack.getItem() instanceof GlowItem )
        {
            return ((GlowItem)stack.getItem()).getSimulatedItemStack( stack );
        }
        return stack;
    }
}
