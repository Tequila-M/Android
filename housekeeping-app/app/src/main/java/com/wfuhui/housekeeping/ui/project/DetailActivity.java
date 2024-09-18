package com.wfuhui.housekeeping.ui.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wfuhui.housekeeping.MainActivity;
import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.components.ProjectGridView;
import com.wfuhui.housekeeping.model.Evaluation;
import com.wfuhui.housekeeping.model.Member;
import com.wfuhui.housekeeping.model.Project;
import com.wfuhui.housekeeping.model.Teacher;
import com.wfuhui.housekeeping.ui.order.ConfirmActivity;
import com.wfuhui.housekeeping.ui.teacher.TeacherDetailActivity;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.GlideImageLoader;
import com.wfuhui.housekeeping.util.PreferencesService;
import com.youth.banner.Banner;
import com.zzhoujay.richtext.RichText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 详情
 */
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Banner banner;

    private Project project = new Project();

    private TextView name, price, describe;

    private Button buy;
    private ListView evaluationLv;

    private EvaluationAdapter evaluationAdapter = new EvaluationAdapter();

    private List<Evaluation> evaluationList = new ArrayList<>();

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
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        banner = findViewById(R.id.banner);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        int id = getIntent().getIntExtra("id", 0);

        buy = findViewById(R.id.buy);
        buy.setOnClickListener(this);
        describe = findViewById(R.id.describe);

        evaluationLv = findViewById(R.id.evaluation);
        evaluationLv.setAdapter(evaluationAdapter);

        this.getProject(id);
        this.getEvaluation(id);
    }

    /**
     * 评价
     */
    class EvaluationAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return evaluationList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = LayoutInflater.from(DetailActivity.this).inflate(R.layout.evaluation_item, null);
            final Evaluation evaluation = evaluationList.get(i);
            new GlideImageLoader().displayImage(DetailActivity.this, evaluation.getMember().getAvatarUrl(), (ImageView)v.findViewById(R.id.avatar));
            TextView nickname = v.findViewById(R.id.nickname);
            nickname.setText(evaluation.getMember().getNickname());
            TextView time = v.findViewById(R.id.time);
            time.setText(evaluation.getCreateTime());
            TextView content = v.findViewById(R.id.content);
            content.setText(evaluation.getContent());

            return v;
        }
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
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
                    name.setText(project.getProjectName());
                    price.setText(project.getPrice().toString());
                    //describe.setText(Html.fromHtml(project.getDescribe()));
                    RichText.fromHtml(project.getDescribe()).into(describe);
                    break;
                case 2:
                    break;
                case 3:
                    String info = msg.getData().getString("msg");
                    Toast.makeText(DetailActivity.this, info, Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    /**
     * 查询项目
     * @param id
     */
    private void getProject(int id){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/project/detail?id="+id)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/project/detail", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/project/detail", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONObject projectJson = json.getJSONObject("project");
                        project.setId(projectJson.getInt("id"));
                        project.setProjectName(projectJson.getString("projectName"));
                        project.setPicUrl(projectJson.getString("picUrl"));
                        project.setPrice(new BigDecimal(projectJson.getDouble("price")));
                        project.setDescribe(projectJson.getString("remark"));
                        List<String> picList = new ArrayList<>();
                        picList.add(project.getPicUrl());
                        //设置图片加载器
                        banner.setImageLoader(new GlideImageLoader());
                        //设置图片集合
                        banner.setImages(picList);
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
            case R.id.buy:
                Intent intent = new Intent(DetailActivity.this, ConfirmActivity.class);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("projectId", project.getId());
                    jsonObject.put("projectName", project.getProjectName());
                    jsonObject.put("price", project.getPrice());
                    jsonObject.put("picUrl", project.getPicUrl());
                    jsonObject.put("num", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject);
                intent.putExtra("payProject", jsonArray.toString());
                startActivity(intent);

                break;
        }
    }

    /**
     * 评价
     * @param projectId
     */
    private void getEvaluation(Integer projectId){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/order/evaluation/list?projectId="+projectId)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/evaluation/list", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/evaluation/list", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONArray evaluationArr = json.getJSONArray("evaluations");
                        for(int i = 0; i < evaluationArr.length(); i++){
                            JSONObject evaluationJson = evaluationArr.getJSONObject(i);
                            Evaluation evaluation = new Evaluation();
                            evaluation.setId(evaluationJson.getInt("id"));
                            evaluation.setContent(evaluationJson.getString("content"));
                            evaluation.setCreateTime(evaluationJson.getString("createTime"));
                            JSONObject memberObj = evaluationJson.getJSONObject("member");
                            Member member = new Member();
                            member.setAvatarUrl(memberObj.getString("avatarUrl"));
                            member.setNickname(memberObj.getString("nickname"));
                            evaluation.setMember(member);
                            evaluationList.add(evaluation);
                        }
                        handler.sendEmptyMessage(5);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
