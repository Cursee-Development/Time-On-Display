package com.cursee.time_on_display;

import com.cursee.monolib.core.sailing.Sailing;
import com.cursee.time_on_display.core.TimeOnDisplayConfig;
import net.fabricmc.api.ModInitializer;

public class TimeOnDisplayFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        TimeOnDisplay.init();
        Sailing.register(Constants.MOD_NAME, Constants.MOD_ID, Constants.MOD_VERSION, Constants.MC_VERSION_RAW, Constants.PUBLISHER_AUTHOR, Constants.PRIMARY_CURSEFORGE_MODRINTH);
        TimeOnDisplayConfig.initialize();
    }
}