package net.elipos.spadar.datagen;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.block.ModBlocks;
import net.elipos.spadar.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Spadar.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.R_BLOCK.get(),
                        ModBlocks.R_ORE.get(),
                        ModBlocks.R_STONE.get(),
                        ModBlocks.DEEPSLATE_R_ORE.get(),
                        ModBlocks.R_STONE_ORE.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.R_GRASS.get(),
                        ModBlocks.R_DIRT.get());

        this.tag(ModTags.Blocks.NEEDS_R_TOOL)
                .add(ModBlocks.R_STONE.get(),
                        ModBlocks.STRIPPED_R_LOG.get(),
                        ModBlocks.STRIPPED_R_WOOD.get(),
                        ModBlocks.R_LOG.get(),
                        ModBlocks.R_WOOD.get(),
                        ModBlocks.R_LEAVES.get(),
                        ModBlocks.R_SAPLING.get(),
                        ModBlocks.R_PORTAL.get(),
                        ModBlocks.R_PLANKS.get(),
                        ModBlocks.R_GRASS.get(),
                        ModBlocks.R_DIRT.get(),
                        ModBlocks.R_ORE.get(),
                        ModBlocks.DEEPSLATE_R_ORE.get(),
                        ModBlocks.R_BLOCK.get(),
                        ModBlocks.R_STONE_ORE.get());

        this.tag(ModTags.Blocks.R_STONE_REPLACEABLE)
                .add(ModBlocks.R_STONE.get());

        this.tag(BlockTags.DIRT)
                .add(ModBlocks.R_GRASS.get(),
                        ModBlocks.R_DIRT.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.R_LOG.get(),
                        ModBlocks.R_WOOD.get(),
                        ModBlocks.STRIPPED_R_LOG.get(),
                        ModBlocks.STRIPPED_R_WOOD.get());

        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.R_PLANKS.get());


    }
}