package net.elipos.spadar.item.custom;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.elipos.spadar.item.ModArmorMaterials;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ModArmorItem extends ArmorItem {

    private static final UUID STEP_UP_MODIFIER_UUID = UUID.fromString("a88f0679-12b6-490b-881c-21502d2741c5");
    private static final AttributeModifier STEP_UP_MODIFIER = new AttributeModifier(STEP_UP_MODIFIER_UUID, "Step Up Modifier", 10000.0D, AttributeModifier.Operation.ADDITION);

    private static final UUID FLYING_SPEED_MODIFIER_UUID = UUID.fromString("c88f0679-12b6-490b-881c-21502d2741c5");
    private static final AttributeModifier FLYING_SPEED_MODIFIER = new AttributeModifier(FLYING_SPEED_MODIFIER_UUID, "Flying Speed Modifier", 1.0D, AttributeModifier.Operation.ADDITION); // Aumenta la velocità di volo del 50%

    private static final Map<ArmorMaterial, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, List<MobEffectInstance>>())
                    .put(ModArmorMaterials.ARMORR, ImmutableList.of(
                            new MobEffectInstance(MobEffects.SATURATION, 1, 255, false, false, false),
                            new MobEffectInstance(MobEffects.REGENERATION, 1, 255, false, false, false),
                            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1, 5, false, false, false),
                            new MobEffectInstance(MobEffects.DIG_SPEED, 1, 255, false, false, false),
                            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 6, false, false, false)
                    )).build();

    // Set per tenere traccia dei giocatori con il modificatore applicato
    private static final Set<UUID> playersWithStepUp = new HashSet<>();
    private static final Set<UUID> playersWithFlying = new HashSet<>();
    private static final Set<UUID> playersWithFlyingSpeed = new HashSet<>();

    public ModArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffects(player);
            }
        }
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, List<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            List<MobEffectInstance> mapStatusEffects = entry.getValue();

            if (hasCorrectArmorOn(mapArmorMaterial, player)) {
                for (MobEffectInstance effect : mapStatusEffects) {
                    addStatusEffectForMaterial(player, mapArmorMaterial, effect);
                }
            }
        }
    }

    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial, MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

        if (hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(mapStatusEffect));
        }
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack helmet = player.getInventory().getArmor(0);
        ItemStack breastplate = player.getInventory().getArmor(1);
        ItemStack leggings = player.getInventory().getArmor(2);
        ItemStack boots = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !breastplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        ArmorItem breastplate = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material &&
                breastplate.getMaterial() == material &&
                leggings.getMaterial() == material &&
                boots.getMaterial() == material;
    }

    @Mod.EventBusSubscriber(modid = "spadar", bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class EquipmentChangeHandler {

        @SubscribeEvent
        public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player player) {
                Level level = player.level();
                if (!level.isClientSide) {
                    updateStepUpAttribute(player);
                    updateFlyingAbility(player);
                    updateFlyingSpeedAttribute(player);
                }
            }
        }

        private static void updateStepUpAttribute(Player player) {
            boolean hasFullSuit = hasFullSuitOfArmorOn(player);
            boolean isWearingStepUpArmor = isWearingStepUpArmor(player);

            if (hasFullSuit && isWearingStepUpArmor) {
                applyStepUpAttribute(player);
            } else {
                removeStepUpAttribute(player);
            }
        }

        private static void updateFlyingAbility(Player player) {
            boolean hasFullSuit = hasFullSuitOfArmorOn(player);
            boolean isWearingFlyingArmor = isWearingFlyingArmor(player);

            if (hasFullSuit && isWearingFlyingArmor) {
                enableFlyingAbility(player);
            } else {
                disableFlyingAbility(player);
            }
        }

        private static void updateFlyingSpeedAttribute(Player player) {
            boolean hasFullSuit = hasFullSuitOfArmorOn(player);
            boolean isWearingFlyingArmor = isWearingFlyingArmor(player);

            if (hasFullSuit && isWearingFlyingArmor) {
                applyFlyingSpeedAttribute(player);
            } else {
                removeFlyingSpeedAttribute(player);
            }
        }

        private static boolean hasFullSuitOfArmorOn(Player player) {
            ItemStack helmet = player.getInventory().getArmor(0);
            ItemStack breastplate = player.getInventory().getArmor(1);
            ItemStack leggings = player.getInventory().getArmor(2);
            ItemStack boots = player.getInventory().getArmor(3);

            return !helmet.isEmpty() && !breastplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
        }

        private static boolean isWearingStepUpArmor(Player player) {
            for (ItemStack armorStack : player.getInventory().armor) {
                if (armorStack.getItem() instanceof ModArmorItem) {
                    return true;
                }
            }
            return false;
        }

        private static boolean isWearingFlyingArmor(Player player) {
            for (ItemStack armorStack : player.getInventory().armor) {
                if (armorStack.getItem() instanceof ModArmorItem) {
                    return true;
                }
            }
            return false;
        }

        private static void applyStepUpAttribute(Player player) {
            UUID playerUUID = player.getUUID();
            if (!playersWithStepUp.contains(playerUUID)) {
                AttributeInstance stepUpAttribute = player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
                if (stepUpAttribute != null) {
                    stepUpAttribute.addTransientModifier(STEP_UP_MODIFIER);
                    playersWithStepUp.add(playerUUID);
                }
            }
        }

        private static void removeStepUpAttribute(Player player) {
            UUID playerUUID = player.getUUID();
            if (playersWithStepUp.contains(playerUUID)) {
                AttributeInstance stepUpAttribute = player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
                if (stepUpAttribute != null) {
                    stepUpAttribute.removeModifier(STEP_UP_MODIFIER_UUID);
                    playersWithStepUp.remove(playerUUID);
                }
            }
        }

        private static void enableFlyingAbility(Player player) {
            UUID playerUUID = player.getUUID();
            if (!playersWithFlying.contains(playerUUID)) {
                player.getAbilities().mayfly = true;
                player.getAbilities().flying = true;
                player.onUpdateAbilities();
                playersWithFlying.add(playerUUID);
            }
        }

        private static void disableFlyingAbility(Player player) {
            UUID playerUUID = player.getUUID();
            if (playersWithFlying.contains(playerUUID)) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
                playersWithFlying.remove(playerUUID);
            }
        }

        private static void applyFlyingSpeedAttribute(Player player) {
            UUID playerUUID = player.getUUID();
            if (!playersWithFlyingSpeed.contains(playerUUID)) {
                AttributeInstance flyingSpeedAttribute = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED);
                if (flyingSpeedAttribute != null) {
                    flyingSpeedAttribute.addTransientModifier(FLYING_SPEED_MODIFIER);
                    playersWithFlyingSpeed.add(playerUUID);
                }
            }
        }

        private static void removeFlyingSpeedAttribute(Player player) {
            UUID playerUUID = player.getUUID();
            if (playersWithFlyingSpeed.contains(playerUUID)) {
                AttributeInstance flyingSpeedAttribute = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED);
                if (flyingSpeedAttribute != null) {
                    flyingSpeedAttribute.removeModifier(FLYING_SPEED_MODIFIER_UUID);
                    playersWithFlyingSpeed.remove(playerUUID);
                }
            }
        }
    }
}