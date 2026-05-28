package com.simats.airwayanesthesia;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class ip {

    static String ipn ="http://172.23.52.220/AnesthEaseNew/";

    public static boolean checkMediaPermission(FragmentActivity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(!(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_MEDIA_IMAGES)== PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(activity, "Media Permission Denied", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if(!(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(activity, "Media Permission Denied", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public static boolean checkCameraPermission(FragmentActivity activity) {
        if(!(ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(activity, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static void getCamera(@NonNull FragmentActivity activity) {
        ActivityResultLauncher<String> cameraPermission = activity.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                Toast.makeText(activity,(o)? "Camera Permission Granted" : "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void requestCameraPermission(ActivityResultLauncher<String> launcher) {
        launcher.launch(Manifest.permission.CAMERA);
    }

    public static void requestMediaPermission(ActivityResultLauncher<String> imagesPermission) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            imagesPermission.launch(android.Manifest.permission.READ_MEDIA_IMAGES);
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            imagesPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void image(FragmentActivity context) {
        ActivityResultLauncher<String> imagesPermission = context.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                Toast.makeText(context,(o)? "Media Permission Granted" : "Media Permission Denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickImageFromGallery(FragmentActivity context) {
        ActivityResultLauncher<String> imagesPermission = context.registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                Toast.makeText(context,(o)? "Media Permission Granted" : "Media Permission Denied", Toast.LENGTH_SHORT).show();
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(!(ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_MEDIA_IMAGES)== PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(context, "Media Permission Denied", Toast.LENGTH_SHORT).show();
                imagesPermission.launch(android.Manifest.permission.READ_MEDIA_IMAGES);
                return;
            }
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if(!(ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(context, "Media Permission Denied", Toast.LENGTH_SHORT).show();
//                imagesPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                return;
            }
        }
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        imageGalleryLauncher.launch(pickIntent);
    }
}

