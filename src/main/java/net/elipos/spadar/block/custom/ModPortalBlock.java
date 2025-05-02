package net.elipos.spadar.block.custom;

import net.elipos.spadar.Spadar;
import net.elipos.spadar.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.UUID;

import static net.elipos.spadar.worldgen.dimension.ModDimensions.R_DIMENSION_LEVEL_KEY;

public class ModPortalBlock extends Block {
    protected static final VoxelShape PORTAL_BLOCK_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected Integer lastTeleport = 0;
    private static final ResourceKey<Level> START_DIMENSION = Level.OVERWORLD;
    private static final ResourceKey<Level> DESTINATION_DIMENSION = R_DIMENSION_LEVEL_KEY;

    // Mappa per salvare la posizione del portale nel mondo di partenza (es. Overworld)
    private static final HashMap<UUID, BlockPos> returnPositions = new HashMap<>();

    public ModPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
                                 InteractionHand handIn, BlockHitResult hit) {
        if ((player instanceof ServerPlayer) && player.isCrouching()) {
            transferPlayer((ServerPlayer) player, pos);
            worldIn.addAlwaysVisibleParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX(), pos.getY() + 1, pos.getZ(), 0, 1, 0);
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    public void transferPlayer(ServerPlayer player, BlockPos pos) {
        int tickCount = player.server.getTickCount();
        if (lastTeleport == 0) {
            lastTeleport = tickCount - 25;
        }
        if (tickCount - lastTeleport < 20) {
            return;
        }

        ResourceKey<Level> currentDimension = player.level().dimension();
        ResourceKey<Level> targetDimension = currentDimension.equals(START_DIMENSION)
                ? DESTINATION_DIMENSION : START_DIMENSION;
        ServerLevel targetWorld = player.server.getLevel(targetDimension);

        BlockPos targetPos = new BlockPos(0, targetWorld.getMinBuildHeight() + 2, 0); // fallback iniziale

        if (targetDimension.equals(DESTINATION_DIMENSION)) {
            // ✅ SALVA la posizione attuale (nell'Overworld)
            if (currentDimension.equals(START_DIMENSION)) {
                returnPositions.put(player.getUUID(), pos);
            }

            // ✅ CERCA il primo blocco solido dall'alto verso il basso
            int y = targetWorld.getMaxBuildHeight() - 1;
            while (y > targetWorld.getMinBuildHeight()) {
                BlockPos checkPos = new BlockPos(0, y, 0);
                BlockState state = targetWorld.getBlockState(checkPos);
                if (!state.isAir() && state.isSolidRender(targetWorld, checkPos)) {
                    targetPos = checkPos.above();
                    break;
                }
                y--;
            }

            // ✅ PIAZZA il portale se non c'è
            BlockState below = targetWorld.getBlockState(targetPos.below());
            if (!below.getBlock().getName().toString().contains("mod_portal_block")) {
                targetWorld.setBlockAndUpdate(targetPos.below(), ModBlocks.R_PORTAL.get().defaultBlockState());
            }

        } else {
            // ✅ RITORNO: usa la posizione salvata dall'andata
            BlockPos savedPos = returnPositions.get(player.getUUID());
            if (savedPos != null) {
                BlockState state = targetWorld.getBlockState(savedPos);
                if (state.getBlock().getName().toString().contains("mod_portal_block")) {
                    targetPos = savedPos.above();
                } else {
                    targetPos = savedPos; // fallback se il blocco è stato rimosso
                }
            } else {
                targetPos = pos.above(); // fallback finale se nulla è stato salvato
            }
        }

        // ✅ Teletrasporto finale
        player.teleportTo(
                targetWorld,
                targetPos.getX() + 0.5D,
                targetPos.getY() + 0.25D,
                targetPos.getZ() + 0.5D,
                player.getYRot(),
                player.getXRot()
        );

        lastTeleport = tickCount;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return PORTAL_BLOCK_AABB;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return PORTAL_BLOCK_AABB;
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        return !player.level().dimension().registry().getNamespace().contains(Spadar.MODID);
    }
}
