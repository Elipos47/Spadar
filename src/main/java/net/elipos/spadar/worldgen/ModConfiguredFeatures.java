package net.elipos.spadar.worldgen;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.block.ModBlocks;
import net.elipos.spadar.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> R_ORE_KEY = registerKey("r_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RTREE_KEY = registerKey("rtree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> R_STONE_ORE_KEY = registerKey("r_stone_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest rStoneReplaceable = new TagMatchTest(ModTags.Blocks.R_STONE_REPLACEABLE);

        List<OreConfiguration.TargetBlockState> overworldRores = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.R_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.DEEPSLATE_R_ORE.get().defaultBlockState())
        );

        register(context, R_STONE_ORE_KEY, Feature.ORE, new OreConfiguration(rStoneReplaceable, ModBlocks.R_STONE_ORE.get().defaultBlockState(), 32));

        register(context, R_ORE_KEY, Feature.ORE, new OreConfiguration(overworldRores, 4));

        register(context, RTREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.R_LOG.get()),
                new FancyTrunkPlacer(8, 12, 8),
                BlockStateProvider.simple(ModBlocks.R_LEAVES.get()),
                new FancyFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
                new TwoLayersFeatureSize(3, 2, 3)).build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Spadar.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}