package net.blazecode.glow.mixins.packets;

import net.blazecode.glow.api.simulated.proxies.GlowItem;
import net.blazecode.glow.api.simulated.proxies.GlowRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin( ShapedRecipe.class )
public abstract class SimulatedRecipeMixin implements CraftingRecipe, GlowRecipe<ShapedRecipe>
{
    @Shadow
    @Final
    int width;

    @Shadow
    @Final
    int height;

    @Shadow
    @Final
    DefaultedList<Ingredient> input;

    @Shadow
    @Final
    ItemStack output;

    @Shadow
    @Final
    private Identifier id;

    @Shadow
    @Final
    String group;


    @Override
    public ShapedRecipe getRecipeProxy( )
    {
        ItemStack out = this.output;
        Item outputItem = out.getItem();

        if( outputItem instanceof GlowRecipe )
        {
            out = (( GlowItem )outputItem).getSimulatedItemStack( out );
        }

        return new ShapedRecipe( id, group, width, height, input, out );
    }
}