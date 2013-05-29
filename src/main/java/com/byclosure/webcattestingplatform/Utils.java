package com.byclosure.webcattestingplatform;

/**
 *
 */
public class Utils {

    public static String removeHeaderFromBase64File(String file) {
        return file.replaceAll("^(data):[a-z]+/[a-z]+-?[a-z]+;base64,", "");
    }
}
