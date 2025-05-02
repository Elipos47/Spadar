package net.elipos.spadar.block;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.block.custom.ModFlammableRotatedPillarBlock;
import net.elipos.spadar.block.custom.ModPortalBlock;
import net.elipos.spadar.item.ModItems;
import net.elipos.spadar.worldgen.tree.RTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Spadar.MODID);

    public static final RegistryObject<Block> R_ORE = registerBlock("r_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)
                    .strength(5f).requiresCorrectToolForDrops(),UniformInt.of(10, 20)));
    public static final RegistryObject<Block> DEEPSLATE_R_ORE = registerBlock("deepslate_r_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE)
                    .strength(6f).requiresCorrectToolForDrops(),UniformInt.of(10, 20)));
    public static final RegistryObject<Block> R_STONE_ORE = registerBlock("r_stone_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)
                    .strength(6f).requiresCorrectToolForDrops(),UniformInt.of(50, 60)));
    public static final RegistryObject<Block> R_BLOCK = registerBlock("r_block",
            () -> new Block(Block.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> R_STONE = registerBlock("r_stone",
            () -> new Block(Block.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> R_GRASS = registerBlock("r_grass",
            () -> new GrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> R_DIRT = registerBlock("r_dirt",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)));

    public static final RegistryObject<Block> R_LOG = registerBlock("r_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(5f)));
    public static final RegistryObject<Block> R_WOOD = registerBlock("r_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(5f)));
    public static final RegistryObject<Block> STRIPPED_R_LOG = registerBlock("stripped_r_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(4f)));
    public static final RegistryObject<Block> STRIPPED_R_WOOD = registerBlock("stripped_r_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(4f)));

    public static final RegistryObject<Block> R_PLANKS = registerBlock("r_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(4f)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> R_LEAVES = registerBlock("r_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LEAVES).strength(1f)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });

    public static final RegistryObject<Block> R_SAPLING = registerBlock("r_sapling",
            () -> new SaplingBlock(new RTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> R_PORTAL = registerBlock("r_portal",
            () -> new ModPortalBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
