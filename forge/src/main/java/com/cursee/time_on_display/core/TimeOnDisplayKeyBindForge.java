package com.cursee.time_on_display.core;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class TimeOnDisplayKeyBindForge {
  
  public static final String KEY_NAME = "key.time_on_display.toggle";
  public static final String KEY_CATEGORY = "key.category.time_on_display.overlay";
  
  public static final KeyMapping TIMEONDISPLAY_KEY = new KeyMapping(
    KEY_NAME,
    KeyConflictContext.IN_GAME,
    InputConstants.Type.KEYSYM,
    GLFW.GLFW_KEY_F8,
    KEY_CATEGORY
  );

}
