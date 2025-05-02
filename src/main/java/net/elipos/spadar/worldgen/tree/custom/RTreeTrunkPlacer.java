package net.elipos.spadar.worldgen.tree.custom;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.elipos.spadar.worldgen.tree.ModTrunkPlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RTreeTrunkPlacer extends TrunkPlacer {
    public static final Codec<RTreeTrunkPlacer> CODEC = RecordCodecBuilder.create(rTreeTrunkPlacerInstance ->
            trunkPlacerParts(rTreeTrunkPlacerInstance).apply(rTreeTrunkPlacerInstance, RTreeTrunkPlacer::new));

    public RTreeTrunkPlacer(int p_70268_, int p_70269_, int p_70270_) {
        super(p_70268_, p_70269_, p_70270_);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.R_TREE_TRUNK_PLACER.get();
    }



    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter,
                                                            RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {

        setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        int height = pFreeTreeHeight + pRandom.nextInt(heightRandA, heightRandB + 3);

        for (int i = 0; i < height; i++) {
            // Posiziona il tronco principale
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);

            // Aggiungi rami laterali in modo casuale
            if (i > 2 && pRandom.nextFloat() < 10) { // Inizia a generare rami solo dopo un certo punto
                int branchLength = pRandom.nextInt(3, 5); // Lunghezza casuale del ramo
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom); // Direzione casuale

                for (int x = 1; x <= branchLength; x++) {
                    BlockPos branchPos = pPos.above(i).relative(direction, x);

                    // Probabilità che il ramo si interrompa prima della fine
                    if (pRandom.nextFloat() > 10f) {
                        break;
                    }

                    // Posiziona il blocco del ramo
                    placeLog(pLevel, pBlockSetter, pRandom, branchPos, pConfig);

                    // Aggiungi ulteriori rami secondari in modo casuale
                    if (pRandom.nextFloat() < 10f) {
                        Direction secondaryDirection = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
                        if (secondaryDirection != direction.getOpposite()) {
                            BlockPos secondaryBranchPos = branchPos.relative(secondaryDirection);
                            placeLog(pLevel, pBlockSetter, pRandom, secondaryBranchPos, pConfig);
                        }
                    }
                }
            }
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(height), 0, false));
    }
}