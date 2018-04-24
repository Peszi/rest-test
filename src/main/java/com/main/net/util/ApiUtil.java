package com.main.net.util;

import java.util.Base64;
import java.util.Map;

public class ApiUtil {

    public static String getStringChain(Map<String, String> valuesMap) {
        String valuesChain = "";
        for (Map.Entry<String, String> valueEntry : valuesMap.entrySet())
            valuesChain += valueEntry.getKey() + "=" + valueEntry.getValue() + "&";
        return valuesChain.substring(0, valuesChain.length()-1);
    }

    public static String getBasicAuth(String clientId, String secret) {
        final byte[] base64 = new String(clientId + ":" + secret).getBytes();
        return new String(Base64.getEncoder().encode(base64));
    }
}
