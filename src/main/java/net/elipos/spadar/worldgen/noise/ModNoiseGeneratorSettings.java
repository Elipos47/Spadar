package net.elipos.spadar.worldgen.noise;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.lang.reflect.Method;
import java.util.List;

public class ModNoiseGeneratorSettings {
    public static final ResourceKey<NoiseGeneratorSettings> R_NOISE_SETTINGS = ResourceKey.create(
            Registries.NOISE_SETTINGS,
            new ResourceLocation(Spadar.MODID, "r_forest")
    );

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        context.register(R_NOISE_SETTINGS, createRForestSettings(context));
    }

    private static NoiseGeneratorSettings createRForestSettings(BootstapContext<NoiseGeneratorSettings> context) {
        NoiseSettings noiseSettings = NoiseSettings.create(-64, 384, 1, 2);

        BlockState defaultBlock = ModBlocks.R_STONE.get().defaultBlockState();
        BlockState defaultFluid = Blocks.WATER.defaultBlockState();

        SurfaceRules.RuleSource surfaceRule = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("minecraft:bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                        SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.waterBlockCheck(-1, 0),
                                                        SurfaceRules.sequence(
                                                                SurfaceRules.ifTrue(
                                                                        SurfaceRules.waterBlockCheck(0, 0),
                                                                        SurfaceRules.state(ModBlocks.R_GRASS.get().defaultBlockState())
                                                                ),
                                                                SurfaceRules.state(Blocks.DIRT.defaultBlockState())
                                                        )
                                                )
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                                        SurfaceRules.sequence(
                                                SurfaceRules.state(defaultBlock),
                                                SurfaceRules.state(defaultBlock)
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.CEILING),
                                                        SurfaceRules.state(defaultBlock)
                                                ),
                                                SurfaceRules.state(Blocks.GRAVEL.defaultBlockState())
                                        )
                                )
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("spadar:r_stone", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)),
                        applyAxisProperty(defaultBlock, Direction.Axis.Y)
                )
        );

        // Utilizzo della reflection per accedere al metodo protetto "overworld" di NoiseRouterData
        NoiseRouter noiseRouter = getOverworldNoiseRouter(context);

        List<Climate.ParameterPoint> spawnTarget = List.of(
                new Climate.ParameterPoint(
                        Climate.Parameter.span(-0.11F, 1F),
                        Climate.Parameter.point(0F),
                        Climate.Parameter.span(-1F, 1F),
                        Climate.Parameter.span(-1F, 1F),
                        Climate.Parameter.span(-1F, 1F),
                        Climate.Parameter.span(-1F, -0.16F),
                        0L
                ),
                new Climate.ParameterPoint(
                        Climate.Parameter.span(-0.11F, 1F),
                        Climate.Parameter.point(0F),
                        Climate.Parameter.span(-1F, 1F),
                        Climate.Parameter.span(-1F, 1F),
                        Climate.Parameter.span(-1F, 1F),
                        Climate.Parameter.span(0.16F, 1F),
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
                63,
                false,  // disableMobGeneration
                true,   // aquifersEnabled
                false,  // oreVeinsEnabled
                false   // legacyRandomSource
        );
    }

    private static SurfaceRules.RuleSource applyAxisProperty(BlockState baseState, Direction.Axis axis) {
        EnumProperty<Direction.Axis> axisProperty = (EnumProperty<Direction.Axis>) baseState.getBlock()
                .getStateDefinition()
                .getProperty("axis");

        if (axisProperty != null) {
            return SurfaceRules.state(baseState.setValue(axisProperty, axis));
        }

        return SurfaceRules.state(baseState);
    }

    private static NoiseRouter getOverworldNoiseRouter(BootstapContext<?> context) {
        try {
            Method overworldMethod = NoiseRouterData.class.getDeclaredMethod(
                    "overworld", HolderGetter.class, HolderGetter.class, boolean.class, boolean.class);
            overworldMethod.setAccessible(true);
            // Passa due volte lo stesso lookup, poiché il metodo richiede HolderGetter per due tipologie differenti
            return (NoiseRouter) overworldMethod.invoke(
                    null,
                    context.lookup(Registries.NOISE),
                    context.lookup(Registries.NOISE),
                    false,
                    false);
        } catch (Exception e) {
            throw new RuntimeException("Impossibile ottenere il NoiseRouter per l'Overworld", e);
        }
    }
}
