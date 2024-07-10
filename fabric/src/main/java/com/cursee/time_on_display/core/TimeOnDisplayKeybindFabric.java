package com.cursee.time_on_display.core;

import com.cursee.time_on_display.TimeOnDisplayFabricClient;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class TimeOnDisplayKeybindFabric {

    public static final String KEY_CATEGORY_TUTORIAL = "key.time_on_display.toggle";
    public static final String KEY_DRINK_WATER = "key.category.time_on_display.overlay";

    public static KeyMapping displayKeybind;

    public static void register() {

        displayKeybind = KeyBindingHelper.registerKeyBinding(new KeyMapping(KEY_DRINK_WATER, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F8, KEY_CATEGORY_TUTORIAL));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (Minecraft.getInstance().player != null && displayKeybind.consumeClick()) {
                TimeOnDisplayFabricClient.displayToggle = !TimeOnDisplayFabricClient.displayToggle;
            }
        });
    }
}
