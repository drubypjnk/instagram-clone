package com.example.prm392_project.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class UtilImage {
    public static Bitmap getImageBase64(String base64String) {
        // Chuyển đổi chuỗi base64 thành mảng byte
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        // Chuyển đổi mảng byte thành đối tượng Bitmap
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
