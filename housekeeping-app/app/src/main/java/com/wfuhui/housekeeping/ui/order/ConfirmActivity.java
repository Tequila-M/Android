package com.wfuhui.housekeeping.ui.order;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.model.Order;
import com.wfuhui.housekeeping.model.OrderProject;
import com.wfuhui.housekeeping.model.OrderShipment;
import com.wfuhui.housekeeping.model.Teacher;
import com.wfuhui.housekeeping.model.Time;
import com.wfuhui.housekeeping.ui.address.AddressActivity;
import com.wfuhui.housekeeping.ui.mine.UserActivity;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.DateUtils;
import com.wfuhui.housekeeping.util.GlideImageLoader;
import com.wfuhui.housekeeping.util.PreferencesService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConfirmActivity extends AppCompatActivity {

    private LinearLayout projectLayout;

    private TextView amountTxt, addressTxt, appointDateTxt;

    private Button payBtn;

    private EditText remarkEdt;

    private JSONObject order = new JSONObject();

    private List<Teacher> teacherList = new ArrayList<>();

    private Integer teacherIndex;

    private List<String> arr = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    private Spinner spinner;

    TimeAdapter timeAdapter = new TimeAdapter();

    private List<Time> timeList = new ArrayList<>();

    private GridView timeGV;

    private Integer timeIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        projectLayout = findViewById(R.id.project);

        amountTxt = findViewById(R.id.amount);

        remarkEdt = findViewById(R.id.remark);

        addressTxt = findViewById(R.id.address);
        addressTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmActivity.this, AddressActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        String appointDate = DateUtils.format(new Date());
        appointDateTxt = findViewById(R.id.appointDate);
        appointDateTxt.setText(appointDate);
        appointDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog dialog = new DatePickerDialog(ConfirmActivity.this);
                    dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month + 1;
                            String n = String.valueOf(month);
                            if(month < 10){
                                n = "0" + month;
                            }
                            String d = String.valueOf(dayOfMonth);
                            if(dayOfMonth < 10){
                                d = "0" + dayOfMonth;
                            }
                            appointDateTxt.setText(year + "-" + n + "-" + d);
                            getTime();
                        }
                    });
                    dialog.show();
                }

            }
        });

        timeGV = findViewById(R.id.times);
        timeGV.setAdapter(timeAdapter);

        payBtn = findViewById(R.id.pay);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay();
            }
        });

        String payProject = getIntent().getStringExtra("payProject");
        Log.e("payProject", payProject);
        try {
            JSONArray jsonArray = new JSONArray(payProject);
            BigDecimal totalAmount = new BigDecimal(0);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                View project = LayoutInflater.from(this).inflate(R.layout.order_project_item, null);
                TextView name = project.findViewById(R.id.name);
                name.setText(jsonObject.getString("projectName"));
                TextView price = project.findViewById(R.id.price);
                price.setText(jsonObject.get("price").toString());
                new GlideImageLoader().displayImage(this, jsonObject.getString("picUrl"), (ImageView)project.findViewById(R.id.pic));
                totalAmount = totalAmount.add(new BigDecimal(jsonObject.getDouble("price")));
                order.put("projectId", jsonObject.getInt("projectId"));
                order.put("projectName", jsonObject.getString("projectName"));
                order.put("picUrl", jsonObject.getString("picUrl"));
                projectLayout.addView(project);
            }
            amountTxt.setText(String.valueOf(totalAmount));
            order.put("totalAmount", totalAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        spinner = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int arg, long l) {
                teacherIndex = arg;
                getTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.getTeacher();
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

    class TimeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return timeList.size();
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
            View v = LayoutInflater.from(ConfirmActivity.this).inflate(R.layout.time_item, null);
            final Time time = timeList.get(i);
            TextView timeTV = v.findViewById(R.id.time);
            TextView statusTV = v.findViewById(R.id.status);
            timeTV.setText(time.getStartTime() + "~" + time.getEndTime());

            final int index = i;
            if(i == timeIndex){
                timeTV.setTextColor(Color.rgb(216, 27, 96));
                statusTV.setTextColor(Color.rgb(216, 27, 96));
            }else{
                timeTV.setTextColor(Color.rgb(44, 44, 44));
                statusTV.setTextColor(Color.rgb(44, 44, 44));
            }

            if(time.getStatus() == 1){
                statusTV.setText("可预订");
            }else{
                statusTV.setText("已预订");
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    timeIndex = index;
                    handler.sendEmptyMessage(4);
                }
            });
            return v;
        }
    }

    /**
     * 预约
     */
    private void pay(){
        try {
            order.put("remark", remarkEdt.getText().toString());
            order.put("appointTime", appointDateTxt.getText().toString());
            order.put("time", timeList.get(timeIndex).getStartTime() + "~" +timeList.get(timeIndex).getEndTime());
            order.put("timeId", timeList.get(timeIndex).getId());
            order.put("teacherId", teacherList.get(teacherIndex).getId());
            order.put("teacherName", teacherList.get(teacherIndex).getRealName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = FormBody.create(order.toString(), MediaType.parse("application/json; charset=utf-8"));

        PreferencesService preferencesService = new PreferencesService(this);
        String token = preferencesService.get("token");

        Log.i("/api/order/save", order.toString());

        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/order/save")
                .addHeader("token", token)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/order/save", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/order/save", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", json.getInt("id"));
                        msg.setData(bundle);
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }else{
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", json.getString("msg"));
                        msg.setData(bundle);
                        msg.what = 2;
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    Intent intent = new Intent(ConfirmActivity.this, PayActivity.class);
                    intent.putExtra("id", msg.getData().getInt("id"));
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    String info = msg.getData().getString("msg");
                    Toast.makeText(ConfirmActivity.this, info, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    adapter.notifyDataSetChanged();
                    break;
                case 4:
                    timeAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String json = data.getStringExtra("address");
        try {
            JSONObject jsonObject = new JSONObject(json);
            String contacts = jsonObject.getString("contacts");
            String mobile = jsonObject.getString("mobile");
            String address = jsonObject.getString("address");

            order.put("orderAddress", jsonObject);

            addressTxt.setText(contacts + "("+mobile+")"+"\n"+address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得技工
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
                        JSONArray teacherArr = json.getJSONArray("teacherList");

                        for(int i = 0; i < teacherArr.length(); i++){
                            JSONObject teacherJson = teacherArr.getJSONObject(i);
                            Teacher teacher = new Teacher();
                            teacher.setId(teacherJson.getInt("id"));
                            teacher.setRealName(teacherJson.getString("realName"));
                            teacherList.add(teacher);
                            arr.add(teacherJson.getString("realName"));
                        }
                        handler.sendEmptyMessage(3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 获得时间
     */
    private void getTime(){
        timeList.clear();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/time/list?teacherId=" + teacherList.get(teacherIndex).getId() + "&appointDate=" + appointDateTxt.getText().toString())
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/time/list", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/time/list", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONArray timeArr = json.getJSONArray("timeList");

                        for(int i = 0; i < timeArr.length(); i++){
                            JSONObject timeJson = timeArr.getJSONObject(i);
                            Time time = new Time();
                            time.setId(timeJson.getInt("id"));
                            time.setStartTime(timeJson.getString("startTime"));
                            time.setEndTime(timeJson.getString("endTime"));
                            time.setStatus(timeJson.getInt("status"));
                            timeList.add(time);
                        }
                        handler.sendEmptyMessage(4);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
