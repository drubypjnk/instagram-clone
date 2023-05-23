package com.example.prm392_project.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_project.Adapter.GalleryAdapter;
import com.example.prm392_project.Adapter.ImagesGallery;
import com.example.prm392_project.Constant.CommonConstant;
import com.example.prm392_project.R;

import java.io.File;
import java.util.List;

public class AddPostStepOneActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;
    List<String> images;

    String imageUrl = "";
    private static final int MY_READ_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_step_one_activity);

        recyclerView = findViewById(R.id.recyclerview_gallery_images);

        /** Check permission to read image from gallery*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(AddPostStepOneActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddPostStepOneActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        } else {
            loadImages();
        }

        /** Next step event click */
        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Redirect to {@link AddPostStepTwoActivity}*/
                Intent nextIntent = new Intent(AddPostStepOneActivity.this, AddPostStepTwoActivity.class);
                nextIntent.putExtra(CommonConstant.IMAGE, imageUrl);
                startActivity(nextIntent);
            }
        });

        /** Cancel event click */
        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Redirect to {@link MainActivity}*/
                Intent intentCancel = new Intent(AddPostStepOneActivity.this, MainActivity.class);
                startActivity(intentCancel);
            }
        });
    }

    /** Load image to recyclerView */
    private void loadImages() {
        recyclerView.setHasFixedSize(true);

        /** Create new GridLayout with 4 column */
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        images = ImagesGallery.listOfImages(this);

        ImageView imageView = findViewById(R.id.image_selected);

        /** Always choose first image*/
        if (images.size() > 0) {
            imageUrl = images.get(0);
            imageView.setImageBitmap(BitmapFactory.decodeFile(new File(images.get(0)).getAbsolutePath()));
        }
        galleryAdapter = new GalleryAdapter(this, images, new GalleryAdapter.PhotoListener() {
            @Override
            public void onPhotoClick(String path) {
                /** Set image has selected to image view */
                imageUrl = path;
                File imgFile = new File(path);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }
        });

        /** Set adapter to recycle view */
        recyclerView.setAdapter(galleryAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, CommonConstant.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();
                loadImages();
            } else {
                Toast.makeText(this, CommonConstant.PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
