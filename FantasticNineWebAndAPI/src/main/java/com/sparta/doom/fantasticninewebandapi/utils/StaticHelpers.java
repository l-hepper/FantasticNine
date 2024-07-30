package com.sparta.doom.fantasticninewebandapi.utils;

import jakarta.servlet.http.HttpServletRequest;

public class StaticHelpers {
    private static final String[] validAccessLevels = {"READ_ONLY", "FULL_ACCESS"};

    public static String getRequestBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            baseUrl.append(":").append(serverPort);
        }

        return baseUrl.toString();
    }
}
