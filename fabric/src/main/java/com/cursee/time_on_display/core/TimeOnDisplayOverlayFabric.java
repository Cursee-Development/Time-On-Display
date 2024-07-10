package com.cursee.time_on_display.core;

import com.cursee.time_on_display.Constants;
import com.cursee.time_on_display.TimeOnDisplayFabricClient;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.time.LocalTime;

public class TimeOnDisplayOverlayFabric implements HudRenderCallback {

    public static final ResourceLocation DEFAULT_CLOCK = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/display/default_clock.png");
    public static final ResourceLocation ALTERNATE_CLOCK_1 = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/display/alternate_clock_1.png");
    public static final ResourceLocation ALTERNATE_CLOCK_2 = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/display/alternate_clock_2.png");

    // todo: re-write this mess, it's disgusting lol

    @Override
    public void onHudRender(GuiGraphics graphics, DeltaTracker delta) {

        // gather configuration values
        final boolean displayEnabled = TimeOnDisplayConfig.displayEnabled;
        final boolean displayOnlySystemTime = TimeOnDisplayConfig.displayOnlySystemTime;
        final boolean displayClockIcons = TimeOnDisplayConfig.displayClockIcons;
        final boolean display24Hour = TimeOnDisplayConfig.display24Hour;
        final String displayLocation = TimeOnDisplayConfig.displayLocation;
        final String displaySystemTimeIcon = TimeOnDisplayConfig.displaySystemTimeIcon;
        
        // define references for positioning display
        final int lowestX = 0;
        final int lowestY = 0;
        final int highestX = graphics.guiWidth();
        final int highestY = graphics.guiHeight();
        
        if (displayEnabled && TimeOnDisplayFabricClient.displayToggle) {
            
            // initialize default positions
            int gameClockIconX = 0, gameClockIconY = 0, gameClockTextX = 0, gameClockTextY = 0;
            int systemClockIconX = 0, systemClockIconY = 0, systemClockTextX = 0, systemClockTextY = 0;

            // gather the system time as a formatted string
            String time = String.valueOf(LocalTime.now()).substring(0, 5);
            if (!display24Hour) {
                Integer hoursFromTime = Integer.valueOf(time.substring(0, 2));
                if (hoursFromTime > 12) {
                    hoursFromTime -= 12;
                }
                time = time.substring(2, 5);
                time = String.valueOf(hoursFromTime) + time;
            }

            // gather the game time as a formatted string
            String minecraft_time = "";
            if (Minecraft.getInstance().level != null) {
                Level level = Minecraft.getInstance().level;
                minecraft_time = String.valueOf(level.getDayTime());
            } else {
                minecraft_time = "0";
            }


            // conform to location and not displaying game time based on config
            switch (displayLocation) {
                case "lower-left" -> {
                    systemClockIconX = 0; systemClockIconY = highestY-32; systemClockTextX = 18; systemClockTextY = (highestY-32)+5;
                    gameClockIconX = 0; gameClockIconY = highestY-16; gameClockTextX = 18; gameClockTextY = (highestY-16)+5;
                }
                case "upper-left" -> {
                    systemClockIconX = 0; systemClockIconY = 0; systemClockTextX = 18; systemClockTextY = 5;
                    gameClockIconX = 0; gameClockIconY = 16; gameClockTextX = 18; gameClockTextY = 16+5;
                }
                case "upper-middle" -> {
                    // begin everything centered on the x-axis and attempt to center afterward
                    systemClockIconX = (highestX/2); systemClockIconY = 0; systemClockTextX = (highestX/2); systemClockTextY = 0;
                    gameClockIconX = (highestX/2); gameClockIconY = 0; gameClockTextX = (highestX/2); gameClockTextY = 0;

                    // icons
                    systemClockIconX -= 32; systemClockIconY -= 0;
                    gameClockIconX -= 32; gameClockIconY += 16;

                    // text
                    systemClockTextX -= 14; systemClockTextY += 5;
                    gameClockTextX -= 14; gameClockTextY += 16+5;

                }
                case "upper-right" -> {
                    systemClockIconX = (highestX-16); systemClockIconY = 0; systemClockTextX = highestX-32-10; systemClockTextY = 5;
                    gameClockIconX = (highestX-16); gameClockIconY = 16; gameClockTextX = highestX-32-10; gameClockTextY = 16+5;
                }
                case "lower-right" -> {
                    systemClockIconX = (highestX-16); systemClockIconY = highestY-32; systemClockTextX = highestX-32-10; systemClockTextY = (highestY-32)+5;
                    gameClockIconX = (highestX-16); gameClockIconY = highestY-16; gameClockTextX = highestX-32-10; gameClockTextY = (highestY-16)+5;
                }
                default -> {
                    // invalid config value? needs testing
                    // should either display everything with initial 0 positions or not display at all
                    systemClockIconX -= 9999; systemClockIconY -= 9999; systemClockTextX -= 9999; systemClockTextY -= 9999;
                    gameClockIconX -= 9999; gameClockIconY -= 9999; gameClockTextX -= 9999; gameClockTextY -= 9999;
                }
            }

            // arbitrarily move game clock off-screen and adjust system clock icon and text position
            if (displayOnlySystemTime) {
                gameClockIconX -= 9999;
                gameClockTextX -= 9999;

                // SYSTEM_TIME_ONLY
                switch (displayLocation) {
                    case "lower-left" -> {
                        systemClockIconY += 16;
                        systemClockTextY += 16;
                    }
                    case "lower-right" -> {
                        systemClockIconY += 16;
                        systemClockTextY += 16;
                    }
                }
            }

            if (!displayClockIcons) {
                systemClockIconX -= 9999;
                gameClockIconX -= 9999;

                // !DISPLAY_CLOCK_ICONS
                switch (displayLocation) {
                    case "lower-left" -> {
                        systemClockTextX -= 16;
                        gameClockTextX -= 16;
                    }
                    case "upper-left" -> {
                        systemClockTextX -= 16;
                        gameClockTextX -= 16;
                    }
                    case "upper-middle" -> {
                        systemClockTextX -= 16;
                        gameClockTextX -= 16;

                        // attempt to center text after removing icons
                    }
                    case "upper-right" -> {

                        systemClockTextX += 16;
                        gameClockTextX += 16;
                    }
                    case "lower-right" -> {

                        systemClockTextX += 16;
                        gameClockTextX += 16;
                    }
                }
            }
            
            // final rendering to screen
            switch (displaySystemTimeIcon) {
                case "1" -> graphics.blit(ALTERNATE_CLOCK_1,    systemClockIconX, systemClockIconY, 0, 0.0f, 0.0f, 16, 16, 16, 16);
                case "2" -> graphics.blit(ALTERNATE_CLOCK_2,    systemClockIconX, systemClockIconY, 0, 0.0f, 0.0f, 16, 16, 16, 16);
                default -> graphics.blit(DEFAULT_CLOCK,         systemClockIconX, systemClockIconY, 0, 0.0f, 0.0f, 16, 16, 16, 16);
            }

            graphics.renderItem(Items.CLOCK.getDefaultInstance(), gameClockIconX, gameClockIconY);

            graphics.drawString(Minecraft.getInstance().font, time, systemClockTextX, systemClockTextY, 0xFFFFFFFF, false);
            graphics.drawString(Minecraft.getInstance().font, minecraft_time, gameClockTextX, gameClockTextY, 0xFFFFFFFF, false);
        }
    }
}
