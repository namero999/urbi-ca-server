package co.urbi.http;

import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
public class Headers extends HashMap<String, String> {

    public Headers(String key, String value) {
        this.put(key, value);
    }

}