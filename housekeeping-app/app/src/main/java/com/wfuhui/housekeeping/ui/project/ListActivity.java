package com.wfuhui.housekeeping.ui.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.model.Project;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.GlideImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 列表
 */
public class ListActivity extends AppCompatActivity implements View.OnClickListener{

    private GridView gridView;

    private ProjectAdapter projectAdapter = new ProjectAdapter();

    private List<Project> projectList = new ArrayList<>();

    private String projectName = "";

    private SearchView q;

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
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        gridView = findViewById(R.id.project);
        gridView.setAdapter(projectAdapter);

        q = findViewById(R.id.q);
        projectName = intent.getStringExtra("projectName");
        q.setQuery(projectName,false);
        q.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                projectName = newText;
                getProject();
                return false;
            }
        });

        this.getProject();
    }

    @Override
    public void onClick(View v) {

    }

    class ProjectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return projectList.size();
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
            View v = LayoutInflater.from(ListActivity.this).inflate(R.layout.project_item, null);
            final Project project = projectList.get(i);
            new GlideImageLoader().displayImage(ListActivity.this, project.getPicUrl(), (ImageView)v.findViewById(R.id.pic));
            TextView name = v.findViewById(R.id.name);
            name.setText(project.getProjectName());
            TextView price = v.findViewById(R.id.price);
            price.setText(project.getPrice().toString());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                    intent.putExtra("id", project.getId());
                    startActivity(intent);
                }
            });
            return v;
        }
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 2:
                    projectAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private void getProject(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/project/list?projectName=" + projectName)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/project/list", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/project/list", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONArray projectArr = json.getJSONArray("projectList");
                        List<String> picUrls = new ArrayList<String>();
                        for(int i = 0; i < projectArr.length(); i++){
                            JSONObject projectJson = projectArr.getJSONObject(i);
                            Project project = new Project();
                            project.setId(projectJson.getInt("id"));
                            project.setProjectName(projectJson.getString("projectName"));
                            project.setPicUrl(projectJson.getString("picUrl"));
                            project.setPrice(new BigDecimal(projectJson.getDouble("price")));
                            projectList.add(project);

                        }
                        handler.sendEmptyMessage(2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
