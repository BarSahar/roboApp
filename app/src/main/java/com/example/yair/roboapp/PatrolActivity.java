package com.example.yair.roboapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class PatrolActivity extends AppCompatActivity {
    DrawView drawView;
    public LinearLayout myBox;
    public String strMap;
    public String serverIp;
    public int[][] matrixMap;
    public int rows;
    public int cols;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol);




        RequestQueue queue = Volley.newRequestQueue(PatrolActivity.this);
        serverIp = Login.ip;
        serverIp = "79.178.101.120";

        String url = "http://" + serverIp + ":8080/Map";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        strMap = response.toString();
                        parseMap();
                        myBox = (LinearLayout) findViewById(R.id.my);
                        drawView = new DrawView(PatrolActivity.this, myBox,matrixMap,rows,cols);
                        drawView.setBackgroundColor(Color.WHITE);
                        ViewGroup myLayout = (ViewGroup) findViewById(R.id.my);

                        myLayout.addView(drawView);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PatrolActivity.this, "Error!" + error, Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

        findViewById(R.id.testbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.mapbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PatrolActivity.this, strMap, Toast.LENGTH_LONG).show();
            }
        });






    }

    public void parseMap() {

        int rows = Integer.parseInt(strMap.substring(0, 2));
        int cols = Integer.parseInt(strMap.substring(3, 5));
        this.rows=rows;
        this.cols=cols;

        matrixMap = new int[rows][cols];
        String strrow;

        strrow = strMap.substring(strMap.indexOf("?") + 1, strMap.length() - 1);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (strrow.substring(i * cols + j, i * cols + j + 1).equals(":"))
                    continue;
                int value = Integer.parseInt(strrow.substring(i * cols + j, i * cols + j + 1));
                matrixMap[i][j] = value;
            }
        }
    }
}

class DrawView extends View {
    Paint paint = new Paint();
    public LinearLayout myBox;
    public int x;
    public int y;
    public int width;
    public int height;

    public int[][]map;
    public int rows;
    public int cols;

    public DrawView(Context context, LinearLayout box,int[][] matrix,int rowss,int colss) {
        super(context);
        paint.setColor(Color.BLACK);
        myBox = box;
        x = (int) (myBox.getX()/2);
        y = (int) (myBox.getY()/2);
        width = myBox.getWidth();
        height = myBox.getHeight();
        map=matrix;
        rows=rowss;
        cols=colss;

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int value;


        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++)
            {
                value=map[i][j];
                if (value==0)
                    paint.setColor(Color.BLACK);
                if(value==1)
                    paint.setColor(Color.BLUE);
                else paint.setColor(Color.RED);


                float Xratio=width/rows;
                float Yratio=height/cols;

                paint.setColor(Color.RED);

                canvas.drawPoint(x+i*100,y+j*100,paint);

                canvas.drawOval((float)(x+i*Xratio),(float)(y+j*Yratio),(float)(10+x+i*Xratio),(float)(10+y+j*Yratio),paint);


            }


    }
}