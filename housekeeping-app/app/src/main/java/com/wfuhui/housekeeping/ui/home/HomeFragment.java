package com.wfuhui.housekeeping.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.components.ProjectGridView;
import com.wfuhui.housekeeping.model.Project;
import com.wfuhui.housekeeping.model.Teacher;
import com.wfuhui.housekeeping.ui.project.DetailActivity;
import com.wfuhui.housekeeping.ui.project.ListActivity;
import com.wfuhui.housekeeping.ui.teacher.TeacherDetailActivity;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.GlideImageLoader;
import com.youth.banner.Banner;

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
 * 首页
 */
public class HomeFragment extends Fragment {

    private Banner banner;

    private GridView gridView;

    private ProjectAdapter projectAdapter = new ProjectAdapter();

    private List<Project> projectList = new ArrayList<>();

    private SearchView q;

    private String projectName = "";


    private List<Teacher> teacherList = new ArrayList<>();

    private TeacherAdapter teacherAdapter = new TeacherAdapter();

    private ProjectGridView teacherGrid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        banner = root.findViewById(R.id.banner);

        q = root.findViewById(R.id.q);
        q.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("projectName", projectName);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                projectName = newText;

                return false;
            }
        });

        this.getBanner();

        gridView = root.findViewById(R.id.project);
        gridView.setAdapter(projectAdapter);
        this.getProject();

        teacherGrid = root.findViewById(R.id.teacher);
        teacherGrid.setAdapter(teacherAdapter);
        this.getTeacher();

        return root;
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
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
                    break;
                case 2:
                    projectAdapter.notifyDataSetChanged();
                    break;

                case 4:
                    teacherAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    /**
     * 获取banner
     */
    private void getBanner(){
        Log.e("请求", "开始---/api/advert/list---");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/advert/list")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/advert/list", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/advert/list", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONArray advertList = json.getJSONArray("advertList");
                        List<String> picUrls = new ArrayList<String>();
                        for(int i = 0; i < advertList.length(); i++){
                            JSONObject advert = advertList.getJSONObject(i);
                            picUrls.add(advert.getString("picUrl"));
                        }
                        //设置图片加载器
                        banner.setImageLoader(new GlideImageLoader());
                        //设置图片集合
                        banner.setImages(picUrls);
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 获取项目
     */
    private void getProject(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/project/list")
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

    /**
     * 查询公司
     */
    private void getTeacher(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/teacher/list")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/teacher/list", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/teacher/list", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONArray projectArr = json.getJSONArray("teacherList");
                        List<String> picUrls = new ArrayList<String>();
                        for(int i = 0; i < projectArr.length(); i++) {
                            JSONObject teacherJson = projectArr.getJSONObject(i);
                            Teacher teacher = new Teacher();
                            teacher.setId(teacherJson.getInt("id"));
                            teacher.setRealName(teacherJson.getString("realName"));
                            teacher.setPicUrl(teacherJson.getString("picUrl"));
                            teacher.setRemark(teacherJson.getString("remark"));
                            teacherList.add(teacher);
                        }

                        handler.sendEmptyMessage(4);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    class TeacherAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return teacherList.size();
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
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.teacher_item, null);
            final Teacher teacher = teacherList.get(i);
            new GlideImageLoader().displayImage(getActivity(), teacher.getPicUrl(), (ImageView)v.findViewById(R.id.pic));
            TextView name = v.findViewById(R.id.name);
            name.setText(teacher.getRealName());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), TeacherDetailActivity.class);
                    intent.putExtra("id", teacher.getId());
                    startActivity(intent);
                }
            });
            return v;
        }
    }

}