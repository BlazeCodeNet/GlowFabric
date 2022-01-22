package net.blazecode.glow.api.simulated.proxies;

import net.minecraft.block.BlockState;

public interface GlowBlockState
{
    /**
     * Returns what BlockState to 'simulate' for client given
     * the real BlockState as 'original'
     *
     * @param original
     * @return Simulated BlockState to send to the client
     */
    public BlockState getSimulatedBlockState( BlockState original);
}
