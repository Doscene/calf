package com.github.doscene.calf.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <h1>com.github.doscene.calf.common.utils</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public class PingUtils {

    public static boolean ping(String ip) {
        try {
            Process p = Runtime.getRuntime().exec("ping " + ip);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (line.length() != 0) {
                    sb.append(line).append("\r\n");
                }
            }
            if (sb.indexOf("100%") > 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void pingAsync(final String ip, final PingCallback callback) {
        Thread thread = new Thread(() -> callback.call(PingUtils.ping(ip)),"ping-thread");
        thread.start();
    }

    @FunctionalInterface
    interface PingCallback {
        /**
         * 回调
         *
         * @param result 结果
         */
        void call(boolean result);
    }
}
