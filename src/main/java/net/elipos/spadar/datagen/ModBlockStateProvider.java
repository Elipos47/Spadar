package net.elipos.spadar.datagen;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Spadar.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.R_BLOCK);
        blockWithItem(ModBlocks.R_STONE);

        blockWithItem(ModBlocks.R_DIRT);

        grassBlock(ModBlocks.R_GRASS);

        blockWithItem(ModBlocks.R_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_R_ORE);
        blockWithItem(ModBlocks.R_STONE_ORE);

        logBlock(((RotatedPillarBlock) ModBlocks.R_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.R_WOOD.get()), blockTexture(ModBlocks.R_LOG.get()), blockTexture(ModBlocks.R_LOG.get()));

        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_R_LOG.get()), blockTexture(ModBlocks.STRIPPED_R_LOG.get()),
                new ResourceLocation(Spadar.MODID, "block/stripped_r_log_top"));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_R_WOOD.get()), blockTexture(ModBlocks.STRIPPED_R_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_R_LOG.get()));

        blockItem(ModBlocks.R_LOG);
        blockItem(ModBlocks.R_WOOD);
        blockItem(ModBlocks.STRIPPED_R_LOG);
        blockItem(ModBlocks.STRIPPED_R_WOOD);

        blockWithItem(ModBlocks.R_PLANKS);

        leavesBlock(ModBlocks.R_LEAVES);

        saplingBlock(ModBlocks.R_SAPLING);

        blockWithItem(ModBlocks.R_PORTAL);
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Spadar.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
        }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void grassBlock(RegistryObject<Block> blockRegistryObject) {
        Block block = blockRegistryObject.get();
        ResourceLocation blockId = blockRegistryObject.getId();

        ResourceLocation topTexture = new ResourceLocation("minecraft", "block/grass_block_top");
        ResourceLocation sideTexture = new ResourceLocation("spadar", "block/r_grass_block_side");
        ResourceLocation bottomTexture = new ResourceLocation("spadar", "block/r_dirt");

        ModelFile model = models().withExistingParent(blockId.getPath(), mcLoc("block/cube_bottom_top"))
                .texture("top", topTexture)
                .texture("side", sideTexture)
                .texture("bottom", bottomTexture);

        simpleBlockWithItem(block, model);
    }


}
