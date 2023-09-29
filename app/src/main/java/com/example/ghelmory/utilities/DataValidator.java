package com.example.ghelmory.utilities;

import android.graphics.BitmapFactory;

public class DataValidator {
    public static boolean isValidPositiveIntString(String intStr){
        return intStr.matches("\\d+");
    }
    public static boolean isWithinRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean isStringValid(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isWithinRange(String intStr, int min, int max) {
        if (isValidPositiveIntString(intStr)) {
            int value = Integer.parseInt(intStr);
            return isWithinRange(value, min, max);
        }
        return false;
    }
    public static boolean isValidUrl(String url){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url, options);
        return options.outWidth != -1 && options.outHeight != -1;
    }
}
