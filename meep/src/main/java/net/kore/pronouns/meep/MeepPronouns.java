package net.kore.pronouns.meep;

import com.mojang.brigadier.Command;
import net.kore.meep.api.command.arguments.ArgumentProvider;
import net.kore.meep.api.command.arguments.EnumArgumentType;
import net.kore.meep.api.entity.Entity;
import net.kore.meep.api.entity.Player;
import net.kore.meep.api.event.EventListener;
import net.kore.meep.api.event.EventManager;
import net.kore.meep.api.event.command.CommandRegisterEvent;
import net.kore.meep.api.event.lifecycle.EnableEvent;
import net.kore.meep.api.meepling.Meepling;
import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.api.data.DecorationData;
import net.kore.pronouns.api.data.PronounData;
import net.kore.pronouns.common.PronounNumber;
import net.kore.pronouns.common.command.CommandSender;
import net.kore.pronouns.common.command.DataType;
import net.kore.pronouns.common.command.Profile;
import net.kore.pronouns.common.command.PronounCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import javax.annotation.Nullable;
import java.util.UUID;

public class MeepPronouns extends Meepling {
    protected static MeepPronouns INSTANCE;

    @Override
    public void init() {
        INSTANCE = this;
        EventManager.get().registerListener(this);
        InstanceHolder.setPlatformProvider(new MeepPlatformProvider());
    }

    @EventListener
    public void onCommands(CommandRegisterEvent event) {
        event.getCommandDispatcher().register(
                ArgumentProvider.literal("pronouns", "pronouns.command")
                        .executes(ctx -> {
                            ctx.getSource().sendMessage(MiniMessage.miniMessage().deserialize("<red>Usage: /pronouns <info|get|set|reload>"));
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(
                                ArgumentProvider.literal("info", "pronouns.command.info")
                                        .executes(ctx -> {
                                            PronounCommand.info(getSender(ctx.getSource()));
                                            return Command.SINGLE_SUCCESS;
                                        })
                        )
                        .then(
                                ArgumentProvider.literal("get", "pronouns.command.get")
                                        .executes(ctx -> {
                                            PronounCommand.get(getSender(ctx.getSource()));
                                            return Command.SINGLE_SUCCESS;
                                        })
                                        .then(
                                                ArgumentProvider.argument("player", ArgumentProvider.player(), "pronouns.command.get.other")
                                                        .executes(ctx -> {
                                                            Player player = ctx.getArgument("player", Player.class);
                                                            PronounCommand.get(getSender(ctx.getSource()), new Profile(player.getUUID(), PlainTextComponentSerializer.plainText().serialize(player.displayName())));
                                                            return Command.SINGLE_SUCCESS;
                                                        })
                                        )
                        )
                        .then(
                                ArgumentProvider.literal("set", "pronouns.command.set")
                                        .executes(ctx -> {
                                            ctx.getSource().sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide the type and value you would like to set!"));
                                            return Command.SINGLE_SUCCESS;
                                        })
                                        .then(
                                                ArgumentProvider.argument("pronoun_type", new EnumArgumentType<>(PronounNumber.class))
                                                        .executes(ctx -> {
                                                            ctx.getSource().sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide a value you would like to set!"));
                                                            return Command.SINGLE_SUCCESS;
                                                        })
                                                        .then(
                                                                ArgumentProvider.argument("pronoun_value", new EnumArgumentType<>(PronounData.class))
                                                                        .executes(ctx -> {
                                                                            PronounCommand.set(getSender(ctx.getSource()), ctx.getArgument("pronoun_type", PronounNumber.class).getDataType(), ctx.getArgument("pronoun_value", PronounData.class), null);
                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                                        .then(
                                                                                ArgumentProvider.argument("player", ArgumentProvider.player(), "pronouns.command.set.other")
                                                                                        .executes(ctx -> {
                                                                                            PronounCommand.set(getSender(ctx.getSource()), ctx.getArgument("pronoun_type", PronounNumber.class).getDataType(), ctx.getArgument("pronoun_value", PronounData.class), null, ctx.getArgument("player", Player.class).getUUID());
                                                                                            return Command.SINGLE_SUCCESS;
                                                                                        })
                                                                        )
                                                        )
                                        )
                                        .then(
                                                ArgumentProvider.literal("decoration")
                                                        .executes(ctx -> {
                                                            ctx.getSource().sendMessage(MiniMessage.miniMessage().deserialize("<red>You must provide a value you would like to set!"));
                                                            return Command.SINGLE_SUCCESS;
                                                        })
                                                        .then(
                                                                ArgumentProvider.argument("decoration_value", new EnumArgumentType<>(DecorationData.class))
                                                                        .executes(ctx -> {
                                                                            PronounCommand.set(getSender(ctx.getSource()), DataType.DECORATION, null, ctx.getArgument("decoration_value", DecorationData.class));
                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                                        .then(
                                                                                ArgumentProvider.argument("player", ArgumentProvider.player(), "pronouns.command.set.other")
                                                                                        .executes(ctx -> {
                                                                                            PronounCommand.set(getSender(ctx.getSource()), DataType.DECORATION, null, ctx.getArgument("decoration_value", DecorationData.class), ctx.getArgument("player", Player.class).getUUID());
                                                                                            return Command.SINGLE_SUCCESS;
                                                                                        })
                                                                        )
                                                        )
                                        )
                        )
                        .then(
                                ArgumentProvider.literal("reload", "pronouns.command.reload")
                                        .executes(ctx -> {
                                            PronounCommand.reload(getSender(ctx.getSource()));
                                            return Command.SINGLE_SUCCESS;
                                        })
                        )
        );
    }

    private CommandSender getSender(net.kore.meep.api.command.CommandSender sender) {
        return new CommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(message));
            }

            @Nullable
            @Override
            public UUID getUUID() {
                return sender instanceof Entity entity ? entity.getUUID() : null;
            }
        };
    }
}
