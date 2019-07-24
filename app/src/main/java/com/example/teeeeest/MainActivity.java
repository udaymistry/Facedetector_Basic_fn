package com.example.teeeeest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnProcess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= findViewById(R.id.bob);
        btnProcess= findViewById(R.id.but);

        final Bitmap myBitmap= BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.wtf);
        imageView.setImageBitmap(myBitmap);


        final Paint rectPaint=new Paint();
        rectPaint.setStrokeWidth(5);
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);


        final Bitmap tempbit = Bitmap.createBitmap(myBitmap.getWidth(),myBitmap.getHeight(), Bitmap.Config.RGB_565);
        final Canvas can= new Canvas(tempbit);
        can.drawBitmap(myBitmap,0,0,null);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FaceDetector faceDetector= new FaceDetector.Builder(getApplicationContext())
                        .setTrackingEnabled(false)
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .setMode(FaceDetector.FAST_MODE)
                        .build();
                if(!faceDetector.isOperational())
                {
                    Toast.makeText(MainActivity.this,"what is going on i dont know ",Toast.LENGTH_SHORT ).show();
                    return;

                }
                Frame frame=new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> sparseArray = faceDetector.detect(frame);


                for(int i=0;i<sparseArray.size();i++)
                {
                    Face face = sparseArray.valueAt(i);
                    float x1 =  face.getPosition().x;
                    float y1 = face.getPosition().y;
                    float x2 = x1+face.getWidth();
                    float y2 = y1+face.getHeight();
                    RectF rectF = new RectF(x1,y1,x2,y2);
                    can.drawRoundRect(rectF,2,2,rectPaint);
                }
                imageView.setImageDrawable(new BitmapDrawable(getResources(),tempbit));




            }
        });


    }
}
