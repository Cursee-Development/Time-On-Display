package com.cursee.time_on_display;

import com.cursee.time_on_display.core.TimeOnDisplayKeybindFabric;
import com.cursee.time_on_display.core.TimeOnDisplayOverlayFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class TimeOnDisplayFabricClient implements ClientModInitializer {

    public static boolean displayToggle = false;

    @Override
    public void onInitializeClient() {
        TimeOnDisplayKeybindFabric.register();
        HudRenderCallback.EVENT.register(new TimeOnDisplayOverlayFabric());
    }
}
