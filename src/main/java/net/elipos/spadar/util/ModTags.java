package net.elipos.spadar.util;

import net.elipos.spadar.Spadar;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_R_TOOL = tag("needs_r_tool");
        public static final TagKey<Block> R_STONE_REPLACEABLE = tag("r_stone_replaceable");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Spadar.MODID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> R_FOREST =
                TagKey.create(ForgeRegistries.Keys.BIOMES, new ResourceLocation(Spadar.MODID, "r_forest"));

    }

    public static class Items {
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Spadar.MODID, name));
        }
    }
}