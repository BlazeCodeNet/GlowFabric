package net.blazecode.glow.mixins.packets;

import net.blazecode.glow.api.simulated.proxies.GlowBlockState;
import net.blazecode.glow.api.simulated.proxies.GlowEntity;
import net.blazecode.glow.api.simulated.proxies.GlowItem;
import net.blazecode.glow.api.simulated.proxies.GlowRecipe;
import net.fabricmc.fabric.impl.registry.sync.RegistrySyncManager;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( RegistrySyncManager.class )
public class RegistrySyncManagerMixin
{
    @Redirect( method = "createAndPopulateRegistryMap", at = @At( value = "INVOKE", target = "Lnet/minecraft/util/registry/Registry;getId(Ljava/lang/Object;)Lnet/minecraft/util/Identifier;" ), require = 0 )
    private static Identifier onRegistryToTag( Registry<Object> registry, Object obj, boolean isClientSync )
    {
        {
            if ( isClientSync &&
                    (
                            obj instanceof GlowItem ||
                                    obj instanceof GlowEntity ||
                                    obj instanceof GlowBlockState ||
                                    obj instanceof GlowRecipe
                    )
            )
            {
                return null;
            }
            return registry.getId( obj );
        }
    }
}
