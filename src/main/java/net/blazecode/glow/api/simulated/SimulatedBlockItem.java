package net.blazecode.glow.api.simulated;

import net.blazecode.glow.api.simulated.proxies.GlowItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SimulatedBlockItem extends BlockItem implements GlowItem
{
    public SimulatedBlockItem( Block block, ItemStack simulatedStack, Identifier identifier, Settings settings, Text displayName )
    {
        super( block, settings );
        this.simulatedStack = simulatedStack;
        this.realDisplayName = displayName;
    }


    @Override
    public ItemStack getSimulatedItemStack( ItemStack original )
    {
        ItemStack changed = simulatedStack.copy();
        changed.setCount(original.getCount());
        changed.setCustomName( getRealDisplayName() );
        return changed;
    }

    @Override
    public boolean hasGlint ( ItemStack stack )
    {
        return true;
    }

    @Override
    public Text getName ( )
    {
        if( realDisplayName == null )
        {
            return super.getName();
        }
        else
        {
            return getRealDisplayName();
        }
    }

    public ItemStack getRepresentationItem()
    {
        return simulatedStack.copy();
    }

    public Text getRealDisplayName()
    {
        return realDisplayName;
    }

    private ItemStack simulatedStack;
    private Text realDisplayName;
}