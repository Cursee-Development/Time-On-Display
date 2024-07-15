package com.cursee.time_on_display;

import com.cursee.monolib.core.sailing.Sailing;
import com.cursee.time_on_display.core.TimeOnDisplayConfig;
import com.cursee.time_on_display.core.TimeOnDisplayKeyBindForge;
import com.cursee.time_on_display.core.TimeOnDisplayOverlayForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

@Mod(Constants.MOD_ID)
public class TimeOnDisplayForge {

    public static boolean displayToggle = false;
    
    public TimeOnDisplayForge() {
    
        TimeOnDisplay.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);
        TimeOnDisplayConfig.initialize();
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
//        @SubscribeEvent
//        public static void registerGuiOverlays(ScreenEvent event) {
//
//
//            event.registerAboveAll("timeondisplay", ForgeTimeOverlay.HUD_TIME);
//        }

        private static final Field LAYERS = ObfuscationReflectionHelper.findField(Gui.class, "layers");

        @SubscribeEvent
        public static void clientInit(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();

                try {
                    LayeredDraw layers = (LayeredDraw) LAYERS.get(mc.gui);
                    layers.add(new TimeOnDisplayOverlayForge());
                }
                catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientForgeBusEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (TimeOnDisplayKeyBindForge.TIMEONDISPLAY_KEY.consumeClick()) {
                if (Minecraft.getInstance().player != null) {
                    displayToggle = !displayToggle;
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(TimeOnDisplayKeyBindForge.TIMEONDISPLAY_KEY);
        }
    }
}
