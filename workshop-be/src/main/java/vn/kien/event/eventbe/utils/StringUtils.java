package vn.kien.event.eventbe.utils;


public class StringUtils {
    public static boolean isBlankOrNull(String st) {
        return st == null || st.trim().length() == 0;
    }
}
