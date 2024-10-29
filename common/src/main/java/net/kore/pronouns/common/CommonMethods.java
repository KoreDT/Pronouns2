package net.kore.pronouns.common;

import net.kore.pronouns.api.config.PronounConfig;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.objectmapping.meta.NodeResolver;

import java.nio.file.Files;
import java.nio.file.Path;

public class CommonMethods {
    public static PronounConfig getConfig(Path path) throws Exception {
        ObjectMapper.Factory customFactory = ObjectMapper.factoryBuilder()
                .addNodeResolver(NodeResolver.onlyWithSetting())
                .build();
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
                .path(path)
                .defaultOptions(opts -> opts.serializers(build -> build.registerAnnotatedObjects(customFactory)))
                .prettyPrinting(true)
                .build();

        if (!Files.exists(path)) {
            CommentedConfigurationNode root = loader.load(ConfigurationOptions
                    .defaults()
                    .header("Pronouns Configuration File!"));

            PronounConfig pronounConfig = new PronounConfig();
            root.set(pronounConfig);
            loader.save(root);
            return pronounConfig;
        } else {
            return loader.load(ConfigurationOptions
                    .defaults()
                    .header("Pronouns2 Configuration File!")).get(PronounConfig.class);
        }
    }
}
