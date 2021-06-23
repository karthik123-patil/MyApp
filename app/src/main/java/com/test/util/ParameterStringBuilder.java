package com.test.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * @author Anant Technologies Pvt Ltd on 21/04/2020
 */
public class ParameterStringBuilder {

    public static String getParamsString(Map<String, String> params, boolean decodePara) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                if( entry.getValue() == null ) {
                    result.append(URLEncoder.encode(" ", "UTF-8"));
                } else {
                    if(decodePara) {
                        result.append(URI.create(entry.getValue()));
                    } else {
                        result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    }
                }
                result.append("&");
            } catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

}
