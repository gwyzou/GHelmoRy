package com.example.ghelmory.utilities;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class ImagePicker {
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS_REQUIRED = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };




    public void runImagePicker(Context context, FragmentActivity requireActivity, ActivityResultLauncher<Intent> imagePickerLauncher) {
        if (context == null || requireActivity == null) {
            return;
        }


        if (checkPermissionsImageAndRequest(context, requireActivity)) {
            chooseCameraOrGalleryAndPickImage(requireActivity, imagePickerLauncher);
        } else {
            Toast.makeText(context, "All Permissions Are Required", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermissionsImageAndRequest(Context context , FragmentActivity requireActivity) {
        List<String> permissionsToRequest = new ArrayList<>();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            for (String permission : PERMISSIONS_REQUIRED) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.CAMERA);
            }
        }
        if (!permissionsToRequest.isEmpty()) {


            ActivityCompat.requestPermissions(requireActivity,
                    permissionsToRequest.toArray(new String[0]),
                    PERMISSIONS_REQUEST_CODE);

            return false;
        }

        return true;
    }
    private void chooseCameraOrGalleryAndPickImage(FragmentActivity requireActivity, ActivityResultLauncher<Intent> imagePickerLauncher){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity.getPackageManager()) != null) {
            Intent chooserIntent = Intent.createChooser(intent, "Choose an image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});
            imagePickerLauncher.launch(chooserIntent);
        } else {
            imagePickerLauncher.launch(intent);
        }
    }
    public String onImagePickedResult(ActivityResult result,Context context) {
        if(result == null || context == null){
            return null;
        }
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Intent data = result.getData();
            if (data.getData() != null) {
                Uri imageUri = data.getData();
                return imageUri.toString();
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    return MediaStore.Images.Media.insertImage(context.getContentResolver(), imageBitmap, "Title", null);
                }else{
                    Toast.makeText(context, "Failed to capture image", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(context, "Camera capture failed or canceled", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
