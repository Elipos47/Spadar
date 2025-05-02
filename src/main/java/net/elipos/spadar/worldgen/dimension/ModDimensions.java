package net.elipos.spadar.worldgen.dimension;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.worldgen.biome.ModBiomes;
import net.elipos.spadar.worldgen.noise.ModNoiseGeneratorSettings;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> R_DIMENSION_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(Spadar.MODID, "r_dimension"));
    public static final ResourceKey<Level> R_DIMENSION_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(Spadar.MODID, "r_dimension"));
    public static final ResourceKey<DimensionType> R_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(Spadar.MODID, "r_dimension_type"));

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(R_DIMENSION_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(ModBiomes.R_FOREST)),
                noiseGenSettings.getOrThrow(ModNoiseGeneratorSettings.R_NOISE_SETTINGS));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.R_DIMENSION_TYPE), wrappedChunkGenerator);

        context.register(R_DIMENSION_KEY, stem);
    }
}