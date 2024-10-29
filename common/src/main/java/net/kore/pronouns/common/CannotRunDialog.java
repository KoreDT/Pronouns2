package net.kore.pronouns.common;

import javax.swing.JOptionPane;

public class CannotRunDialog {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,
                """
                        You cannot run this JAR file! It is intented to be run as a library, plugin or mod for Minecraft!\

                        You can find which it is usually via the file name, API and Minestom are intended for library use.\
                        
                        Bungeecord and Velocity are intended for your proxy server. They go in the `plugins` folder.\
                        
                        Meep, Paper and Sponge go on your actual servers. They go in the `plugins` folder.\
                        
                        Fabric and NeoForge are intended for your modded servers or clients. They go in the `mods` folder.
                        """,
                "Cannot run this JAR file!",
                JOptionPane.ERROR_MESSAGE);
    }
}
