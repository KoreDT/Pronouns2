package net.kore.pronouns.common.modloader;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.kore.pronouns.api.InstanceHolder;
import net.kore.pronouns.api.data.DecorationData;
import net.kore.pronouns.api.data.PronounData;
import net.kore.pronouns.common.PronounNumber;
import net.kore.pronouns.common.command.CommandSender;
import net.kore.pronouns.common.command.DataType;
import net.kore.pronouns.common.command.Profile;
import net.kore.pronouns.common.command.PronounCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.function.Predicate;

public class ModPronounsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("pronouns")
                        .requires(hasPermission("pronouns.command"))
                        .executes(ctx -> {
                            sendMessage(ctx.getSource(), "<red>Usage: /pronouns <info|get|set|reload>");
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(
                                Commands.literal("info")
                                        .requires(hasPermission("pronouns.command.info"))
                                        .executes(ctx -> {
                                            PronounCommand.info(getSender(ctx.getSource()));
                                            return Command.SINGLE_SUCCESS;
                                        })
                        )
                        .then(
                                Commands.literal("get")
                                        .requires(hasPermission("pronouns.command.get"))
                                        .executes(ctx -> {
                                            PronounCommand.get(getSender(ctx.getSource()));
                                            return Command.SINGLE_SUCCESS;
                                        })
                                        .then(
                                                Commands.argument("player", EntityArgument.player())
                                                        .requires(hasPermission("pronouns.command.get.other"))
                                                        .executes(ctx -> {
                                                            Player player = ctx.getArgument("player", Player.class);
                                                            String name;
                                                            Component cname = player.getDisplayName();
                                                            if (cname == null) cname = player.getName();
                                                            name = cname.getString();
                                                            PronounCommand.get(getSender(ctx.getSource()), new Profile(player.getUUID(), name));
                                                            return Command.SINGLE_SUCCESS;
                                                        })
                                        )
                        )
                        .then(
                                Commands.literal("set")
                                        .requires(hasPermission("pronouns.command.set"))
                                        .executes(ctx -> {
                                            sendMessage(ctx.getSource(), "<red>You must provide the type and value you would like to set!");
                                            return Command.SINGLE_SUCCESS;
                                        })
                                        .then(
                                                Commands.argument("pronoun_type", StringArgumentType.word())
                                                        .suggests(new EnumSuggester<>(PronounNumber.class))
                                                        .executes(ctx -> {
                                                            if (!EnumSuggester.isValidEnum(PronounNumber.class, StringArgumentType.getString(ctx, "pronoun_type")))
                                                                sendMessage(ctx.getSource(), "<red>Invalid argument for Pronoun Number");
                                                            else sendMessage(ctx.getSource(), "<red>You must provide a value you would like to set!");
                                                            return Command.SINGLE_SUCCESS;
                                                        })
                                                        .then(
                                                                Commands.argument("pronoun_value", StringArgumentType.word())
                                                                        .suggests(new EnumSuggester<>(PronounData.class))
                                                                        .executes(ctx -> {
                                                                            if (!EnumSuggester.isValidEnum(PronounNumber.class, StringArgumentType.getString(ctx, "pronoun_type")))
                                                                                sendMessage(ctx.getSource(), "<red>Invalid argument for Pronoun Number");
                                                                            else if (!EnumSuggester.isValidEnum(PronounNumber.class, StringArgumentType.getString(ctx, "pronoun_value")))
                                                                                sendMessage(ctx.getSource(), "<red>Invalid argument for Pronoun Data");
                                                                            else PronounCommand.set(getSender(ctx.getSource()), ctx.getArgument("pronoun_type", PronounNumber.class).getDataType(), ctx.getArgument("pronoun_value", PronounData.class), null);
                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                                        .then(
                                                                                Commands.argument("player", EntityArgument.player())
                                                                                        .requires(hasPermission("pronouns.command.set.other"))
                                                                                        .executes(ctx -> {
                                                                                            if (!EnumSuggester.isValidEnum(PronounNumber.class, StringArgumentType.getString(ctx, "pronoun_type")))
                                                                                                sendMessage(ctx.getSource(), "<red>Invalid argument for Pronoun Number");
                                                                                            else if (!EnumSuggester.isValidEnum(PronounNumber.class, StringArgumentType.getString(ctx, "pronoun_value")))
                                                                                                sendMessage(ctx.getSource(), "<red>Invalid argument for Pronoun Data");
                                                                                            else PronounCommand.set(getSender(ctx.getSource()), ctx.getArgument("pronoun_type", PronounNumber.class).getDataType(), ctx.getArgument("pronoun_value", PronounData.class), null, ctx.getArgument("player", Player.class).getUUID());
                                                                                            return Command.SINGLE_SUCCESS;
                                                                                        })
                                                                        )
                                                        )
                                        )
                                        .then(
                                                Commands.literal("decoration")
                                                        .executes(ctx -> {
                                                            sendMessage(ctx.getSource(), "<red>You must provide a value you would like to set!");
                                                            return Command.SINGLE_SUCCESS;
                                                        })
                                                        .then(
                                                                Commands.argument("decoration_value", StringArgumentType.word())
                                                                        .suggests(new EnumSuggester<>(DecorationData.class))
                                                                        .executes(ctx -> {
                                                                            if (!EnumSuggester.isValidEnum(PronounNumber.class, StringArgumentType.getString(ctx, "decoration_value")))
                                                                                sendMessage(ctx.getSource(), "<red>Invalid argument for Decoration Data");
                                                                            PronounCommand.set(getSender(ctx.getSource()), DataType.DECORATION, null, ctx.getArgument("decoration_value", DecorationData.class));
                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                                        .then(
                                                                                Commands.argument("player", EntityArgument.player())
                                                                                        .requires(hasPermission("pronouns.command.set.other"))
                                                                                        .executes(ctx -> {
                                                                                            if (!EnumSuggester.isValidEnum(PronounNumber.class, StringArgumentType.getString(ctx, "decoration_value")))
                                                                                                sendMessage(ctx.getSource(), "<red>Invalid argument for Decoration Data");
                                                                                            PronounCommand.set(getSender(ctx.getSource()), DataType.DECORATION, null, ctx.getArgument("decoration_value", DecorationData.class), ctx.getArgument("player", Player.class).getUUID());
                                                                                            return Command.SINGLE_SUCCESS;
                                                                                        })
                                                                        )
                                                        )
                                        )
                        )
                        .then(
                                Commands.literal("reload")
                                        .requires(hasPermission("pronouns.command.reload"))
                                        .executes(ctx -> {
                                            PronounCommand.reload(getSender(ctx.getSource()));
                                            return Command.SINGLE_SUCCESS;
                                        })
                        )
        );
    }

    private static CommandSender getSender(CommandSourceStack commandSourceStack) {
        return new CommandSender() {
            @Override
            public void sendMessage(String message) {
                ((ModPlatformProvider) InstanceHolder.getPlatformProvider()).sendMessage(commandSourceStack, message);
            }

            @Override
            public UUID getUUID() {
                Entity entity = commandSourceStack.getEntity();
                return entity != null ? entity.getUUID() : null;
            }
        };
    }

    private static Predicate<CommandSourceStack> hasPermission(String permission) {
        return ((ModPlatformProvider) InstanceHolder.getPlatformProvider()).hasPermission(permission);
    }

    private static void sendMessage(CommandSourceStack commandSourceStack, String message) {
        ((ModPlatformProvider) InstanceHolder.getPlatformProvider()).sendMessage(commandSourceStack, message);
    }
}
