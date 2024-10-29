package net.kore.pronouns.paper;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.api.data.DecorationData;
import net.kore.pronouns.api.data.PronounData;
import net.kore.pronouns.common.PronounNumber;
import net.kore.pronouns.common.command.CommandSender;
import net.kore.pronouns.common.command.DataType;
import net.kore.pronouns.common.command.Profile;
import net.kore.pronouns.common.command.PronounCommand;
import net.kore.pronouns.paper.command.EnumArgumentType;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class PaperPronouns extends JavaPlugin {
    @Override
    public void onLoad() {
        InstanceHolder.setPlatformProvider(new PaperPlatformProvider());

        LifecycleEventManager<Plugin> manager = getLifecycleManager();

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            Commands commands = event.registrar();

            commands.register(
                    Commands.literal("pronouns")
                            .requires(stack -> stack.getSender().hasPermission("pronouns.command"))
                            .executes(ctx -> {
                                ctx.getSource().getSender().sendMessage(MiniMessage.miniMessage().deserialize("<red>Usage: /pronouns <info|get|set|reload>"));
                                return Command.SINGLE_SUCCESS;
                            })
                            .then(
                                    Commands.literal("info")
                                            .requires(stack -> stack.getSender().hasPermission("pronouns.command.info"))
                                            .executes(ctx -> {
                                                PronounCommand.info(getSender(ctx.getSource()));
                                                return Command.SINGLE_SUCCESS;
                                            })
                            )
                            .then(
                                    Commands.literal("get")
                                            .requires(stack -> stack.getSender().hasPermission("pronouns.command.get"))
                                            .executes(ctx -> {
                                                PronounCommand.get(getSender(ctx.getSource()));
                                                return Command.SINGLE_SUCCESS;
                                            })
                                            .then(
                                                    Commands.argument("player", ArgumentTypes.player())
                                                            .requires(stack -> stack.getSender().hasPermission("pronouns.command.get.other"))
                                                            .executes(ctx -> {
                                                                Player player = ctx.getArgument("player", Player.class);
                                                                PronounCommand.get(getSender(ctx.getSource()), new Profile(player.getUniqueId(), PlainTextComponentSerializer.plainText().serialize(player.displayName())));
                                                                return Command.SINGLE_SUCCESS;
                                                            })
                                            )
                            )
                            .then(
                                    Commands.literal("set")
                                            .requires(stack -> stack.getSender().hasPermission("pronouns.command.set"))
                                            .executes(ctx -> {
                                                ctx.getSource().getSender().sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide the type and value you would like to set!"));
                                                return Command.SINGLE_SUCCESS;
                                            })
                                            .then(
                                                    Commands.argument("pronoun_type", new EnumArgumentType<>(PronounNumber.class))
                                                            .executes(ctx -> {
                                                                ctx.getSource().getSender().sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide a value you would like to set!"));
                                                                return Command.SINGLE_SUCCESS;
                                                            })
                                                            .then(
                                                                    Commands.argument("pronoun_value", new EnumArgumentType<>(PronounData.class))
                                                                            .executes(ctx -> {
                                                                                PronounCommand.set(getSender(ctx.getSource()), ctx.getArgument("pronoun_type", PronounNumber.class).getDataType(), ctx.getArgument("pronoun_value", PronounData.class), null);
                                                                                return Command.SINGLE_SUCCESS;
                                                                            })
                                                                            .then(
                                                                                    Commands.argument("player", ArgumentTypes.player())
                                                                                            .requires(stack -> stack.getSender().hasPermission("pronouns.command.set.other"))
                                                                                            .executes(ctx -> {
                                                                                                PronounCommand.set(getSender(ctx.getSource()), ctx.getArgument("pronoun_type", PronounNumber.class).getDataType(), ctx.getArgument("pronoun_value", PronounData.class), null, ctx.getArgument("player", Player.class).getUniqueId());
                                                                                                return Command.SINGLE_SUCCESS;
                                                                                            })
                                                                            )
                                                            )
                                            )
                                            .then(
                                                    Commands.literal("decoration")
                                                            .executes(ctx -> {
                                                                ctx.getSource().getSender().sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide a value you would like to set!"));
                                                                return Command.SINGLE_SUCCESS;
                                                            })
                                                            .then(
                                                                    Commands.argument("decoration_value", new EnumArgumentType<>(DecorationData.class))
                                                                            .executes(ctx -> {
                                                                                PronounCommand.set(getSender(ctx.getSource()), DataType.DECORATION, null, ctx.getArgument("decoration_value", DecorationData.class));
                                                                                return Command.SINGLE_SUCCESS;
                                                                            })
                                                                            .then(
                                                                                    Commands.argument("player", ArgumentTypes.player())
                                                                                            .requires(stack -> stack.getSender().hasPermission("pronouns.command.set.other"))
                                                                                            .executes(ctx -> {
                                                                                                PronounCommand.set(getSender(ctx.getSource()), DataType.DECORATION, null, ctx.getArgument("decoration_value", DecorationData.class), ctx.getArgument("player", Player.class).getUniqueId());
                                                                                                return Command.SINGLE_SUCCESS;
                                                                                            })
                                                                            )
                                                            )
                                            )
                            )
                            .then(
                                    Commands.literal("reload")
                                            .requires(stack -> stack.getSender().hasPermission("pronouns.command.reload"))
                                            .executes(ctx -> {
                                                PronounCommand.reload(getSender(ctx.getSource()));
                                                return Command.SINGLE_SUCCESS;
                                            })
                            )
                            .build()
            );
        });
    }

    private CommandSender getSender(CommandSourceStack commandSourceStack) {
        return new CommandSender() {
            @Override
            public void sendMessage(String message) {
                commandSourceStack.getSender().sendMessage(MiniMessage.miniMessage().deserialize(message));
            }

            @Nullable
            @Override
            public UUID getUUID() {
                return commandSourceStack.getSender() instanceof Entity entity ? entity.getUniqueId() : null;
            }
        };
    }
}