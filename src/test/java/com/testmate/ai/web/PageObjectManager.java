package com.testmate.ai.web;

import com.testmate.ai.web.pages.SampleWebPage;

public final class PageObjectManager {

    private static final ThreadLocal<PageObjectManager> INSTANCE = ThreadLocal.withInitial(PageObjectManager::new);

    private SampleWebPage sampleWebPage;

    private PageObjectManager() {
    }

    public static PageObjectManager getInstance() {
        return INSTANCE.get();
    }

    public static void remove() {
        INSTANCE.remove();
    }

    public SampleWebPage sampleWebPage() {
        if (sampleWebPage == null) {
            sampleWebPage = new SampleWebPage();
        }
        return sampleWebPage;
    }
}
