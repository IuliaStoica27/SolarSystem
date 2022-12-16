package com.link.solarsystem;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Canvas canvas;
    Paint paint;
    ImageView imageView;
    Timer timer;
    float centerX;
    float centerY;
    int[] screenDimensions;
    float[] centerYPlanets;
    int[] planetsColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenDimensions = getDisplayDimensions();
        Bitmap bitmap = Bitmap.createBitmap(screenDimensions[0], screenDimensions[1], Bitmap.Config.ARGB_8888);
        imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        canvas = new Canvas();
        canvas.setBitmap(bitmap);
        canvas.save();
        paint = new Paint();

        centerX = (float)screenDimensions[0]/2;
        centerY = (float)screenDimensions[1]/2;

        centerYPlanets = new float[]{centerY + 2 * sunRadius, centerY + 3 * sunRadius, centerY + 4 * sunRadius, centerY + 5 *sunRadius, centerY+6*sunRadius};
        planetsColor = new int[]{Color.parseColor("#e3ded8"),Color.parseColor("#d4c79b"),Color.parseColor("#7095b3"),Color.parseColor("#b84a37"),Color.parseColor("#c99885") };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rotatePlanets();
                    }
                });
            }
        }, 10, 50);

    }


    float rotateMercury = 10f;
    float rotateVenus = 10f;
    float rotateEarth = -10f;
    float rotateMars = -10f;
    float rotateJupiter = 10f;
    float sunRadius = 100;
    float mercuryRadius = 20;
    float venusRadius = 30;
    float earthRadius = 30;
    float marsRadius = 25;
    float jupiterRadius = 60;
    float[] planetsRadius = {mercuryRadius,venusRadius,earthRadius,marsRadius,jupiterRadius};

    private void rotatePlanets(){

        float[] rotate = {rotateMercury,rotateVenus,rotateEarth,rotateMars,rotateJupiter};

            canvas.drawColor(Color.parseColor("#16171c"));
            paint.setColor(Color.parseColor("#faa646"));
            canvas.drawCircle(centerX,centerY,sunRadius,paint);
            paint.reset();

        for (int i = 0; i<planetsColor.length; i++){
            paint.setColor(planetsColor[i]);
            canvas.rotate(rotate[i], centerX, centerY);
            canvas.drawCircle(centerX, centerYPlanets[i], planetsRadius[i],paint);
            canvas.restore();
            canvas.save();
            paint.reset();
        }
        rotateMercury = rotateMercury + 10f;
        rotateVenus = rotateVenus + 5f;
        rotateEarth = rotateEarth - 3f;
        rotateMars = rotateMars - 1f;
        rotateJupiter = rotateJupiter + 0.5f;

        imageView.invalidate();

    }


    private int[] getDisplayDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        return new int[]{width, height};
    }
}