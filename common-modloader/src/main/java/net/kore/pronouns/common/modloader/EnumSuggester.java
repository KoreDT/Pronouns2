package net.kore.pronouns.common.modloader;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class EnumSuggester<T extends Enum<T>> implements SuggestionProvider<CommandSourceStack> {
    private final Class<T> enumClass;

    public EnumSuggester(Class<T> theEnum) {
        enumClass = theEnum;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        String[] strings = (String[]) Arrays.stream(enumClass.getEnumConstants()).map(anEnum -> anEnum.name().toLowerCase(Locale.ROOT)).toArray();
        return SharedSuggestionProvider.suggest(strings, builder);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidEnum(Class<? extends Enum<?>> enumClass, String input) {
        for (Enum<?> anEnum : enumClass.getEnumConstants()) {
            if (input.toLowerCase(Locale.ROOT).equals(anEnum.name().toLowerCase(Locale.ROOT))) return true;
        }
        return false;
    }
}
