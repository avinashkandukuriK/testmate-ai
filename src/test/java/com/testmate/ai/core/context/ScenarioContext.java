package com.testmate.ai.core.context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static final ThreadLocal<Map<String, Object>> CURRENT_DATA = ThreadLocal.withInitial(HashMap::new);

    public static ScenarioContext current() {
        return new ScenarioContext();
    }

    public static void remove() {
        CURRENT_DATA.remove();
    }

    public void set(String key, Object value) {
        CURRENT_DATA.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = CURRENT_DATA.get().get(key);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

    public void clear() {
        CURRENT_DATA.get().clear();
    }
}
