package net.blazecode.glow.api.simulated.proxies;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public interface GlowItem
{
    /**
     * Returns what ItemStack to 'simulate' for client given
     * the real ItemStack as 'original'
     *
     * @param original
     * @return Simulated ItemStack to send to the client
     */
    public ItemStack getSimulatedItemStack( ItemStack original );
}
