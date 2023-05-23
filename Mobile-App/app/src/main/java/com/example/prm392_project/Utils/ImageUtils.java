package com.example.prm392_project.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

public class ImageUtils {
    public static Bitmap getImageBase64(String base64String) {
        // Chuyển đổi chuỗi base64 thành mảng byte
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        // Chuyển đổi mảng byte thành đối tượng Bitmap
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    public static byte[] getResizedImage(Bitmap bitmap, String imagePath, Context context) {
        int width = bitmap.getWidth() / 2;
        int height = bitmap.getHeight() / 2;

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

        return stream.toByteArray();
    }
}
