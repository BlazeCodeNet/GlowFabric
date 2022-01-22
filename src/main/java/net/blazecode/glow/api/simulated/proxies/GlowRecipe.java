package net.blazecode.glow.api.simulated.proxies;

import net.minecraft.recipe.Recipe;

public interface GlowRecipe<T extends Recipe<?>>
{
    public T getRecipeProxy();
}
