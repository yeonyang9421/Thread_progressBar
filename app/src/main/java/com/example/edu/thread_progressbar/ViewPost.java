package com.example.edu.thread_progressbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewPost extends AppCompatActivity implements View.OnClickListener {
    Button mbutton, mbutton2;
    ProgressBar mprogressbar;
    ImageView mImageView;
    private int mProgressBarStatus = 0;
    Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);


        mbutton = findViewById(R.id.button);
        mbutton2 = findViewById(R.id.button2);
        mprogressbar = findViewById(R.id.progressBar);
        mImageView = findViewById(R.id.imageView);
        mbutton.setOnClickListener(this);
        mbutton2.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                mImageView.setImageResource(R.drawable.google);
               thread= new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (mProgressBarStatus < 100) {
                            try {
                                Thread.sleep(100);
                                mProgressBarStatus++;
                                mprogressbar.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mprogressbar.setProgress(mProgressBarStatus);
                                    }
                                });
                            } catch (InterruptedException e) {
                                break;
                             //   e.printStackTrace();
                            }

                        }
                    }
                });
                   thread.start();

                break;

            case R.id.button2:
                if(thread !=null) {
                    thread.interrupt();
                    thread=null;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String strUri = "https://png.pngtree.com/thumb_back/fh260/back_pic/03/71/68/3257b821ae7a560.jpg";
                        try {
                            URL url = new URL(strUri);
                            HttpURLConnection httpURLConnection = null;
                            httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.connect();
                            InputStream inputStream = null;
                            inputStream = httpURLConnection.getInputStream();
                            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            mImageView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mImageView.setImageBitmap(bitmap);
                                }
                            });
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
        }


    }


}
