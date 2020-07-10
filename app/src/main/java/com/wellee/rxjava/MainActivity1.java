package com.wellee.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wellee.rxjava.rx.functions.Function;
import com.wellee.rxjava.rx.Observable;
import com.wellee.rxjava.rx.Observer;
import com.wellee.rxjava.rx.schedulers.Schedulers;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity1 extends AppCompatActivity {

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = findViewById(R.id.image);
        loadImageBySelfRx();
    }

    private void loadImageBySelfRx() {
        Observable.just("https://pics1.baidu.com/feed/2934349b033b5bb559f6640e50ca3a3fb600bc39.jpeg?token=86d5e448602837a3b3b5511bc6451965")
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull String s) throws Exception {
                        URL url = new URL(s);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        return BitmapFactory.decodeStream(inputStream);
                    }
                })
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull Bitmap bitmap) {
                        bitmap = Utils.addTextWatermark(bitmap, "wellee", 30,
                                Color.parseColor("#ff0000"), 20, 20, false, true);
                        Log.e("MainActivity1", "thread name = " + Thread.currentThread().getName());
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe() {
                        Log.e("MainActivity1", "onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull final Bitmap bitmap) {
                        Log.e("MainActivity1", "onNext");
                        Log.e("MainActivity1", "thread name = " + Thread.currentThread().getName());
                        mImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("MainActivity1", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("MainActivity1", "onComplete");
                    }
                });
    }
}
