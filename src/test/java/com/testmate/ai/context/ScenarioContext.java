package com.testmate.ai.context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static final ThreadLocal<ScenarioContext> CURRENT = ThreadLocal.withInitial(ScenarioContext::new);
    private final Map<String, Object> data = new HashMap<>();

    public static ScenarioContext current() {
        return CURRENT.get();
    }

    public static void remove() {
        CURRENT.remove();
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = data.get(key);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

    public void clear() {
        data.clear();
    }
}
