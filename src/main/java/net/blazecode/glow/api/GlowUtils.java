package net.blazecode.glow.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.UUID;

public class GlowUtils
{
    public static MutableText getText( String text, Formatting... formattings)
    {
        return new LiteralText(text).setStyle( Style.EMPTY.withItalic(false).withFormatting(formattings) );
    }

    public static void playSoundToPlayer( ServerPlayerEntity player, SoundEvent event, float vol, float pitch)
    {
        player.networkHandler.sendPacket( new PlaySoundS2CPacket(event, SoundCategory.PLAYERS, player.getPos().x, player.getPos().y, player.getPos().z, vol, pitch));
    }

    public static ItemStack getHeadFromRawId( String rawId )
    {
        ItemStack buffStack = new ItemStack( Items.PLAYER_HEAD );
        NbtCompound skullTag = buffStack.getOrCreateSubNbt( "SkullOwner" );

        NbtCompound propertiesTag = new NbtCompound();
        NbtList texturesTag = new NbtList();
        NbtCompound textureValue = new NbtCompound();

        textureValue.putString("Value", rawId);

        texturesTag.add(textureValue);
        propertiesTag.put("textures", texturesTag);
        skullTag.put("Properties", propertiesTag);
        skullTag.putUuid( "Id", UUID.randomUUID() );

        return buffStack;
    }

    public static void setStackModelData(ItemStack stack, int id)
    {
        NbtCompound stackTag = stack.getOrCreateNbt(  );

        stackTag.putInt("CustomModelData", id);
    }

    public static void setStackLore(ItemStack stack, List<MutableText> lores)
    {
        NbtCompound dispCompound = stack.getOrCreateSubNbt( "display" );

        NbtList loreListTag = new NbtList();

        for( Text t : lores)
        {
            loreListTag.add( NbtString.of(Text.Serializer.toJson(t)));
        }

        dispCompound.put( "Lore", loreListTag );
    }
}
