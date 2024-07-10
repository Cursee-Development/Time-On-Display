package com.cursee.time_on_display;

import com.cursee.monolib.core.sailing.Sailing;
import com.cursee.time_on_display.core.TimeOnDisplayConfig;
import com.cursee.time_on_display.core.TimeOnDisplayKeyBindNeoForge;
import com.cursee.time_on_display.core.TimeOnDisplayOverlayNeoForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import java.lang.reflect.Field;

@Mod(Constants.MOD_ID)
public class TimeOnDisplayNeoForge {
    
    public static boolean displayToggle = false;

    public TimeOnDisplayNeoForge(IEventBus bus) {

        TimeOnDisplay.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);
        TimeOnDisplayConfig.initialize();
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
//        @SubscribeEvent
//        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
//            // event.registerAboveAll("timeondisplay", NeoForgeTimeOverlay.HUD_TIME);
//            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "time_on_display"), NeoForgeTimeOverlay.HUD_TIME);
//        }

        private static final Field LAYERS = ObfuscationReflectionHelper.findField(Gui.class, "layers");

        @SubscribeEvent
        public static void clientInit(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Minecraft mc = Minecraft.getInstance();

                try {
                    LayeredDraw layers = (LayeredDraw) LAYERS.get(mc.gui);
                    layers.add(new TimeOnDisplayOverlayNeoForge());
                }
                catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            });
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(TimeOnDisplayKeyBindNeoForge.TIMEONDISPLAY_KEY);
        }

    }

    @EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static class ClientForgeBusEvents {



        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (TimeOnDisplayKeyBindNeoForge.TIMEONDISPLAY_KEY.consumeClick()) {
                if (Minecraft.getInstance().player != null) {
                    displayToggle = !displayToggle;
                }
            }
        }
    }
}
