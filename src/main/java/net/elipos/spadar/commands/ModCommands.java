package net.elipos.spadar.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.elipos.spadar.Spadar;
import net.elipos.spadar.worldgen.ModConfiguredFeatures;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Spadar.MODID)
public class ModCommands {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("genrtree")
                .requires(cs -> cs.hasPermission(2))
                .executes(ctx -> {
                    ServerLevel level = ctx.getSource().getLevel();
                    BlockPos pos = BlockPos.containing(ctx.getSource().getPosition()).above();

                    // Verifica che la feature sia registrata
                    Optional<Holder<ConfiguredFeature<?, ?>>> optionalFeature = level.registryAccess()
                            .registryOrThrow(Registries.CONFIGURED_FEATURE)
                            .getHolder(ModConfiguredFeatures.RTREE_KEY)
                            .map(holderRef -> (Holder<ConfiguredFeature<?, ?>>) holderRef);

                    if (optionalFeature.isEmpty()) {
                        Spadar.LOGGER.error("La feature configurata non è registrata!");
                        ctx.getSource().sendFailure(Component.literal("Errore: La feature configurata non è registrata!"));
                        return 0;
                    }

                    Holder<ConfiguredFeature<?, ?>> feature = optionalFeature.get();

                    // Genera l'albero
                    boolean success = feature.value().place(
                            level,
                            level.getChunkSource().getGenerator(),
                            level.getRandom(),
                            pos
                    );

                    if (!success) {
                        Spadar.LOGGER.error("Impossibile generare l'albero alla posizione: " + pos);
                        ctx.getSource().sendFailure(Component.literal("Errore: Impossibile generare l'albero!"));
                        return 0;
                    }

                    ctx.getSource().sendSuccess(() -> Component.literal("Albero generato con successo!"), false);
                    return 1;
                }));
    }
}