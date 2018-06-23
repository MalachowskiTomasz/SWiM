package com.company.swim7a;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.lang.reflect.Field;

public class ScannerActivity extends AppCompatActivity {
    private final static String focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Toast.makeText(this, "Zeskanuj kod kreskowy produktu", Toast.LENGTH_LONG).show();

        SurfaceView cameraView = findViewById(R.id.camera_view);

        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.EAN_13)
                        .build();
        CameraSource cameraSource =
                new CameraSource.Builder(this, barcodeDetector)
                .build();
        cameraView.setOnClickListener(v -> cameraFocus(cameraSource));


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try{
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e){
                    Log.e("Camera source", e.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if(barcodes.size() > 0){
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(barcodes.valueAt(0).displayValue));
                    setResult(RESULT_OK, intent);
//                    cameraSource.release();
                    finish();
                }
            }
        });
    }

    private void cameraFocus(CameraSource cameraSource) {
        for(Field field : CameraSource.class.getDeclaredFields()){
            if(field.getType() == Camera.class){
                field.setAccessible(true);
                try{
                    Camera camera = (Camera) field.get(cameraSource);
                    if(camera != null){
                        Camera.Parameters params = camera.getParameters();
                        params.setFocusMode(focusMode);
                        camera.setParameters(params);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
