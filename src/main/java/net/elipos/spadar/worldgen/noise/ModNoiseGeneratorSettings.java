package net.elipos.spadar.worldgen.noise;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSpline;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class ModNoiseGeneratorSettings {
    public static final ResourceKey<NoiseGeneratorSettings> R_NOISE_SETTINGS = ResourceKey.create(
            Registries.NOISE_SETTINGS,
            new ResourceLocation(Spadar.MODID, "r_forest")
    );

    static NoiseSettings rNoiseSettings = NoiseSettings.create(-64, 384, 1, 2);

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        context.register(R_NOISE_SETTINGS, rNoise(context));
    }

    private static NoiseGeneratorSettings rNoise(BootstapContext<NoiseGeneratorSettings> context) {
        NoiseSettings noiseSettings = rNoiseSettings;

        BlockState defaultBlock = ModBlocks.R_STONE.get().defaultBlockState();
        BlockState defaultFluid = Blocks.WATER.defaultBlockState();

        SurfaceRules.RuleSource surfaceRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.state(defaultBlock)
                ),
                SurfaceRules.state(defaultBlock) // fallback in ogni altro caso
        );

        // Costruzione noise semplificato
        DensityFunction baseTerrain = DensityFunctions.blendDensity(DensityFunctions.yClampedGradient(-64, 320, 1.2, -0.8));
        DensityFunction caveNoise = DensityFunctions.noise(context.lookup(Registries.NOISE).getOrThrow(Noises.CAVE_LAYER));
        DensityFunction combinedDensity = DensityFunctions.add(baseTerrain, caveNoise);

        // Costruzione del router minimale
        NoiseRouter noiseRouter = new NoiseRouter(
                DensityFunctions.zero(),  // barrier
                DensityFunctions.constant(0.02),  // fluid level floodedness
                DensityFunctions.constant(0.02),  // fluid level spread
                DensityFunctions.constant(0.1),   // lava
                DensityFunctions.constant(0.5),   // temperature
                DensityFunctions.constant(0.6),   // vegetation
                DensityFunctions.constant(0.4),   // continents
                DensityFunctions.constant(0.3),   // erosion
                DensityFunctions.constant(0.2),   // depth
                baseTerrain,                      // ridges
                combinedDensity,                   // initialDensityWithoutJaggedness
                baseTerrain,                      // finalDensity
                DensityFunctions.constant(0.07),   // vein toggle
                DensityFunctions.constant(0.07),   // vein ridged
                DensityFunctions.constant(0.07)    // vein gap
        );

        List<Climate.ParameterPoint> spawnTarget = List.of(
                new Climate.ParameterPoint(
                        Climate.Parameter.span(0.25F, 0.75F),
                        Climate.Parameter.point(0.5F),
                        Climate.Parameter.point(0.5F),
                        Climate.Parameter.point(0.5F),
                        Climate.Parameter.point(0.5F),
                        Climate.Parameter.span(0.25F, 0.75F),
                        0L
                )
        );

        return new NoiseGeneratorSettings(
                noiseSettings,
                defaultBlock,
                defaultFluid,
                noiseRouter,
                surfaceRule,
                spawnTarget,
                32,      // sea level
                false,   // disableMobGeneration
                true,    // aquifersEnabled
                false,   // oreVeinsEnabled
                false    // useLegacyRandomSource
        );
    }
}