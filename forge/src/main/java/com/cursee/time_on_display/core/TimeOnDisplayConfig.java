package com.cursee.time_on_display.core;

import com.cursee.monolib.platform.Services;
import com.cursee.monolib.util.toml.Toml;
import com.cursee.monolib.util.toml.TomlWriter;
import com.cursee.time_on_display.Constants;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TimeOnDisplayConfig {

    public static final File CONFIG_DIRECTORY = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config");
    public static final String CONFIG_FILEPATH = CONFIG_DIRECTORY + File.separator + Constants.MOD_ID + ".toml";

    public static boolean displayEnabled = false;
    public static boolean displayOnlySystemTime = false;
    public static boolean displayClockIcons = true;
    public static boolean display24Hour = false;

    public static String displayLocation = "upper-left";
    public static String displaySystemTimeIcon = "";

    public static final Map<String, Object> defaults = new HashMap<String, Object>();

    public static void initialize() {

        defaults.put("displayEnabled", displayEnabled);
        defaults.put("displayOnlySystemTime", displayOnlySystemTime);
        defaults.put("displayClockIcons", displayClockIcons);
        defaults.put("display24Hour", display24Hour);
        defaults.put("displayLocation", displayLocation);
        defaults.put("displaySystemTimeIcon", displaySystemTimeIcon);

        if (!CONFIG_DIRECTORY.isDirectory()) {
            CONFIG_DIRECTORY.mkdir();
        }

        final File CONFIG_FILE = new File(CONFIG_FILEPATH);

        TimeOnDisplayConfig.handle(CONFIG_FILE);
    }

    public static void handle(File file) {

        final boolean FILE_NOT_FOUND = !file.isFile();

        if (FILE_NOT_FOUND) {
            try {
                TomlWriter writer = new TomlWriter();
                writer.write(defaults, file);
            }
            catch (IOException exception) {
                Constants.LOG.error("Fatal error occurred while attempting to write " + Constants.MOD_ID + ".toml");
                Constants.LOG.error("Did another process delete the config directory during writing?");
                Constants.LOG.error(exception.getMessage());
            }
        }
        else {
            try {

                Toml toml = new Toml().read(file);

                displayEnabled = toml.getBoolean("displayEnabled");

                displayOnlySystemTime = toml.getBoolean("displayOnlySystemTime");
                displayClockIcons = toml.getBoolean("displayClockIcons");
                display24Hour = toml.getBoolean("display24Hour");
                displayLocation = toml.getString("displayLocation");
                displaySystemTimeIcon = toml.getString("displaySystemTimeIcon");

            }
            catch (IllegalStateException exception) {
                Constants.LOG.error("Fatal error occurred while attempting to read " + Constants.MOD_ID + ".toml");
                Constants.LOG.error("Did another process delete the file during reading?");
                Constants.LOG.error(exception.getMessage());
            }
        }
    }
}
