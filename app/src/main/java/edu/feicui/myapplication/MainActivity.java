package edu.feicui.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
Button button;
    final OkHttpClient client=new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                postRequest();
                break;
        }
    }
    public static final MediaType JSON=MediaType.parse("application/json:charset=utf-8");

    private  void  postRequest() {
        EditText edit = (EditText) findViewById(R.id.editText);
        EditText edit1 = (EditText) findViewById(R.id.editText2);
        String username = edit.getText().toString();
        String pwd = edit1.getText().toString();
        JSONObject object = new JSONObject();
        try {
            object.put("name", username);
            object.put("pwd", pwd);
            Log.d("测试", object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, object.toString());
        String url = HttpUrlUtil.url + "UserAddServlet";
        Log.v(url.toString(), url.toString());
        final Request request = new Request.Builder().url(url).post(body).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        Log.v("响应成功", str);
                    } else {
                        Log.v("响应失败", "响应失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    }
