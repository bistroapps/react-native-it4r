package com.nfcetectoyreact;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import java.util.HashMap;
import java.util.Map;

public class It4rUtils {
    public static HashMap<String, Object> convertReadableMapToHashMap(ReadableMap readableMap) {
        HashMap<String, Object> hashMap = new HashMap<>();

        if (readableMap == null) {
            return hashMap;
        }

        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            Object value = getValueFromReadableMap(readableMap, key);
            hashMap.put(key, value);
        }

        return hashMap;
    }

    private static Object getValueFromReadableMap(ReadableMap readableMap, String key) {
        switch (readableMap.getType(key)) {
            case Null:
                return null;
            case Boolean:
                return readableMap.getBoolean(key);
            case Number:
                return readableMap.getDouble(key);
            case String:
                return readableMap.getString(key);
            case Map:
                return convertReadableMapToHashMap(readableMap.getMap(key));
            case Array:
                // Implemente a lógica para converter um ReadableArray para um List, se necessário
                return null;
            default:
                return null;
        }
    }
}
