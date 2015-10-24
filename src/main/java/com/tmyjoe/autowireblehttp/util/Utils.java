package com.tmyjoe.autowireblehttp.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Tomoya Honjo
 */
public class Utils {

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
