package net.kore.pronouns.paper.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import org.jetbrains.annotations.NotNull;

public class EnumArgumentType<T extends Enum<T>> implements CustomArgumentType<T, String> {
    private final Class<T> theEnum;

    public EnumArgumentType(Class<T> theEnum) {
        this.theEnum = theEnum;
    }

    @Override
    public @NotNull T parse(@NotNull StringReader stringReader) throws CommandSyntaxException {
        String input = stringReader.readQuotedString();
        for (T t : theEnum.getEnumConstants()) {
            if (t.name().equals(input)) {
                return t;
            }
        }

        throw new CommandSyntaxException(new CommandExceptionType() {}, () -> input + " is not part of that enum.");
    }

    @Override
    public @NotNull ArgumentType<String> getNativeType() {
        return StringArgumentType.string();
    }
}
