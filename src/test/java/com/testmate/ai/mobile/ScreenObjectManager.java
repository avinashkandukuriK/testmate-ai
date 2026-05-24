package com.testmate.ai.mobile;

import com.testmate.ai.mobile.screens.SampleMobileScreen;

public final class ScreenObjectManager {

    private static final ThreadLocal<ScreenObjectManager> INSTANCE = ThreadLocal.withInitial(ScreenObjectManager::new);

    private SampleMobileScreen sampleMobileScreen;

    private ScreenObjectManager() {
    }

    public static ScreenObjectManager getInstance() {
        return INSTANCE.get();
    }

    public static void remove() {
        INSTANCE.remove();
    }

    public SampleMobileScreen sampleMobileScreen() {
        if (sampleMobileScreen == null) {
            sampleMobileScreen = new SampleMobileScreen();
        }
        return sampleMobileScreen;
    }
}
