package com.wfuhui.housekeeping.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.PreferencesService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 登录
 *
 */
public class LoginActivity extends AppCompatActivity {

    private EditText loginNameEdit, passwordEdit;

    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginNameEdit = findViewById(R.id.loginName);
        passwordEdit = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    finish();
                    break;
                case 2:
                    String info = msg.getData().getString("msg");
                    Toast.makeText(LoginActivity.this, info, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * 登录
     */
    private void login(){
        String loginName = loginNameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/login?loginName=" + loginName +"&password=" + password)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/login", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/login", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONObject userJson = json.getJSONObject("member");
                        String token = json.getString("token");
                        PreferencesService preferencesService = new PreferencesService(LoginActivity.this);
                        preferencesService.save("token", token);
                        handler.sendEmptyMessage(1);
                    }else{
                        Message msg = new Message();
                        msg.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", json.getString("msg"));
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
