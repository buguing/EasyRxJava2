package com.wellee.rxjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = findViewById(R.id.image);
//        loadImage();
        loadImageByRx();
    }

    private void loadImageByRx() {
        Observable.just("https://pics1.baidu.com/feed/2934349b033b5bb559f6640e50ca3a3fb600bc39.jpeg?token=86d5e448602837a3b3b5511bc6451965")
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        URL url = new URL(s);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        return BitmapFactory.decodeStream(inputStream);
                    }
                })
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        bitmap = Utils.addTextWatermark(bitmap, "wellee", 30,
                                Color.parseColor("#ff0000"), 20, 20, false, true);
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("MainActivity", "onSubscribe");
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mImage.setImageBitmap(bitmap);
                        Log.e("MainActivity", "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("MainActivity", "onComplete");
                    }
                });
    }

    private void loadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://pics1.baidu.com/feed/2934349b033b5bb559f6640e50ca3a3fb600bc39.jpeg?token=86d5e448602837a3b3b5511bc6451965");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = Utils.addTextWatermark(bitmap, "wellee", 30,
                            Color.parseColor("#ff0000"), 20, 20, false, true);
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            mImage.setImageBitmap(bitmap);
            return false;
        }
    });
}
