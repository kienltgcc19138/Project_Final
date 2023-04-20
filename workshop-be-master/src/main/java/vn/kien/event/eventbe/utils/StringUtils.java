package vn.kien.event.eventbe.utils;

/**
 * Le-Hong-Quan
 * Date: 20/02/2023
 * Time: 21:08
 */
public class StringUtils {
    public static boolean isBlankOrNull(String st) {
        return st == null || st.trim().length() == 0;
    }
}
