package net.blazecode.glow.api.simulated.proxies;

import net.minecraft.entity.EntityType;

public interface GlowEntity
{
    /**
     * Returns what entity to 'simulate' for client given
     * the real entity as 'original'
     *
     * @return Simulated EntityType to send to client
     */
    public EntityType getSimulatedEntity();
}
