package co.urbi.http;

import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor
public class Params extends TreeMap<String, Object> {

    public Params(String key, Object value) {
        this.put(key, value);
    }

    public Params put(String key, Object value) { // Chaining
        super.put(key, value);
        return this;
    }

    public static Params of(Map<String, ?> map) {
        Params params = new Params();
        params.putAll(map);
        return params;
    }

    public boolean has(String key) {
        return key != null && super.containsKey(key);
    }

}