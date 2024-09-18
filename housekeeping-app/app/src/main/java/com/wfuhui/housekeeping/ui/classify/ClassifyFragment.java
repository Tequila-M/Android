package com.wfuhui.housekeeping.ui.classify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.model.Classify;
import com.wfuhui.housekeeping.model.Project;
import com.wfuhui.housekeeping.ui.project.DetailActivity;
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
 * 分类
 */
public class ClassifyFragment extends Fragment {

    private List<Classify> classifyList = new ArrayList<>();
    private List<Project> projectList = new ArrayList<>();

    private ListView listView;

    private GridView gridView;

    private ProjectAdapter projectAdapter = new ProjectAdapter();

    private ClassifyAdapter classifyAdapter = new ClassifyAdapter();

    private int categoryIndex = 0;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_classify, container, false);

        listView = root.findViewById(R.id.category);
        gridView = root.findViewById(R.id.project);

        listView.setAdapter(classifyAdapter);
        gridView.setAdapter(projectAdapter);
        Classify all = new Classify();
        all.setCategoryName("全部");
        all.setId(-1);
        classifyList.add(all);
        this.getProject();
        this.getCategory();
        return root;
    }

    class ClassifyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return classifyList.size();
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
        public View getView(int i, View view, final ViewGroup viewGroup) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.classify_item, null);
            Classify classify = classifyList.get(i);
            final TextView name = v.findViewById(R.id.name);
            name.setText(classify.getCategoryName());
            final int index = i;
            if(i == categoryIndex){
                name.setTextColor(Color.rgb(216, 27, 96));
            }else{
                name.setTextColor(Color.rgb(44, 44, 44));
            }
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryIndex = index;
                    getProject();
                    handler.sendEmptyMessage(1);
                }
            });
            return v;
        }
    }

    class ProjectAdapter extends BaseAdapter{

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
            View v = LayoutInflater.from(getContext()).inflate(R.layout.project_item, null);
            final Project project = projectList.get(i);
            new GlideImageLoader().displayImage(getContext(), project.getPicUrl(), (ImageView)v.findViewById(R.id.pic));
            TextView name = v.findViewById(R.id.name);
            name.setText(project.getProjectName());
            TextView price = v.findViewById(R.id.price);
            price.setText(project.getPrice().toString());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("id", project.getId());
                    getActivity().startActivity(intent);
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
                case 1:
                    classifyAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    projectAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private void getCategory(){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/category/list")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/category/list", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/category/list", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONArray categoryArr = json.getJSONArray("categoryList");

                        for(int i = 0; i < categoryArr.length(); i++){
                            JSONObject categoryJson = categoryArr.getJSONObject(i);
                            Classify category = new Classify();
                            category.setId(categoryJson.getInt("id"));
                            category.setCategoryName(categoryJson.getString("categoryName"));
                            classifyList.add(category);
                        }
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getProject(){
        Integer categoryId = classifyList.get(this.categoryIndex).getId();
        projectList.clear();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/project/list?categoryId="+categoryId)
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
