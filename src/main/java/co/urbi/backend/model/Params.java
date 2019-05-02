package co.urbi.backend.model;

import lombok.NoArgsConstructor;

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

    public boolean has(String key) {
        return key != null && super.containsKey(key);
    }

}