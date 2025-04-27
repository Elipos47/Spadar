package net.elipos.spadar.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class SpadarItem extends SwordItem {

    public SpadarItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 120; // 5 secondi
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void onUseTick(Level level, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!(user instanceof Player player)) return;

        int useTime = getUseDuration(stack) - remainingUseTicks;

        if (useTime == 100) {

            Vec3 start = player.getEyePosition(1.0F);
            Vec3 look = player.getLookAngle();
            Vec3 end = start.add(look.scale(300.0));

            // Particelle lato client
            if (level.isClientSide) {
                for (int i = 0; i < 3000; i++) {
                    Vec3 p = start.add(look.scale(i));
                    level.addParticle(ParticleTypes.SONIC_BOOM, p.x, p.y, p.z, 0, 0, 0);
                    level.addParticle(ParticleTypes.GLOW, p.x, p.y, p.z, 0, 0, 0);
                    level.addParticle(ParticleTypes.ELECTRIC_SPARK, p.x, p.y, p.z, 0, 0, 0);
                }
            }

            // Lato server: danno
            if (!level.isClientSide) {
                EntityHitResult entityHit = getEntityHit(level, player, start, end);
                if (entityHit != null && entityHit.getEntity() instanceof LivingEntity target) {
                    target.hurt(level.damageSources().magic(), Float.MAX_VALUE);
                }

                level.playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.getCooldowns().addCooldown(this, 100);
                player.stopUsingItem();
            }
        }
    }

    private EntityHitResult getEntityHit(Level level, Player player, Vec3 start, Vec3 end) {
        AABB bounds = new AABB(start, end).inflate(1.0D);
        return ProjectileUtil.getEntityHitResult(level, player, start, end, bounds,
                entity -> entity instanceof LivingEntity && !entity.isSpectator() && entity.isPickable());
    }
}
