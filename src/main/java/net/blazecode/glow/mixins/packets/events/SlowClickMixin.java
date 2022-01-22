package net.blazecode.glow.mixins.packets.events;

import io.netty.handler.logging.LogLevel;
import net.blazecode.glow.api.simulated.GlowScreenHandler;
import net.blazecode.glow.api.simulated.SIM_SLOT_CLICK_RESULT;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.logging.Level;
import java.util.logging.Logger;

@Mixin( ScreenHandler.class )
public class SlowClickMixin
{
    @Inject( method = "onSlotClick", at = @At(value = "HEAD"), cancellable = true )
    void onClickSlotInjection( int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci )
    {
        if(player.world.isClient)
        {
            return;
        }
        if( player.currentScreenHandler instanceof GlowScreenHandler )
        {
            GlowScreenHandler serverScreenHandler = (GlowScreenHandler ) player.currentScreenHandler;
            ClickType clickType = button == 0 ? ClickType.LEFT : ClickType.RIGHT;

            SIM_SLOT_CLICK_RESULT clickResult = serverScreenHandler.checkSlotClick( slotIndex, button, actionType, ( ServerPlayerEntity ) player );

            if(clickResult == SIM_SLOT_CLICK_RESULT.DENY_CLICK)
            {
                // DONT PASS THE CLICK THROUGH
                ci.cancel();
            }
        }

    }
}
