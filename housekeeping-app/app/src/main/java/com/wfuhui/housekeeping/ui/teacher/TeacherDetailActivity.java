package com.wfuhui.housekeeping.ui.teacher;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.model.Teacher;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.GlideImageLoader;
import com.youth.banner.Banner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 详情
 */
public class TeacherDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Banner banner;

    private Teacher teacher = new Teacher();

    private TextView name, specialty, remark;

    private Button buy;

    private Integer total;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        banner = findViewById(R.id.banner);

        name = findViewById(R.id.name);
        specialty = findViewById(R.id.specialty);
        remark = findViewById(R.id.remark);
        int id = getIntent().getIntExtra("id", 0);

        this.getTeacher(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    name.setText(teacher.getRealName());
                    specialty.setText(teacher.getSpecialty());
                    remark.setText(teacher.getRemark());
                    new GlideImageLoader().displayImage(TeacherDetailActivity.this, teacher.getPicUrl(), (ImageView)findViewById(R.id.avatar));
                    //describe.setText(Html.fromHtml(teacher.getDescribe()));
                    break;

                case 3:{
                    String info = msg.getData().getString("msg");
                    Toast.makeText(TeacherDetailActivity.this, info, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };

    /**
     * 查询公司
     * @param id
     */
    private void getTeacher(int id){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/teacher/detail?id="+id)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/teacher/detail", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/teacher/detail", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONObject teacherJson = json.getJSONObject("teacher");
                        teacher.setId(teacherJson.getInt("id"));
                        teacher.setRealName(teacherJson.getString("realName"));
                        teacher.setPicUrl(teacherJson.getString("picUrl"));
                        teacher.setSpecialty(teacherJson.getString("specialty"));
                        teacher.setRemark(teacherJson.getString("remark"));

                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
