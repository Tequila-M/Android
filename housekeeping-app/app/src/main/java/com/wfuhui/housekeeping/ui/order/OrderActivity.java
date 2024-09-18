package com.wfuhui.housekeeping.ui.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.model.Order;
import com.wfuhui.housekeeping.model.OrderProject;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.GlideImageLoader;
import com.wfuhui.housekeeping.util.PreferencesService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView order;

    private List<Order> orderList = new ArrayList<>();

    private OrderAdapter orderAdapter = new OrderAdapter();

    private TextView statusAll, statusUnPay, statusUnSend, statusComplete;

    private Integer status = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        order = findViewById(R.id.order);
        order.setAdapter(orderAdapter);
        statusAll = findViewById(R.id.all);
        statusAll.setOnClickListener(this);
        statusUnPay = findViewById(R.id.pay);
        statusUnPay.setOnClickListener(this);
        statusUnSend = findViewById(R.id.send);
        statusUnSend.setOnClickListener(this);

        statusComplete = findViewById(R.id.complete);
        statusComplete.setOnClickListener(this);
        getOrder();
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    orderAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all:
                setSelected(statusAll);
                status = -1;
                getOrder();
                break;
            case R.id.pay:
                setSelected(statusUnPay);
                status = 1;
                getOrder();
                break;
            case R.id.send:
                setSelected(statusUnSend);
                status = 2;
                getOrder();
                break;
            case R.id.complete:
                setSelected(statusComplete);
                status = 3;
                getOrder();
                break;
        }
    }

    private void setSelected(TextView tv){
        statusAll.setTextColor(getResources().getColor(R.color.black));
        statusUnPay.setTextColor(getResources().getColor(R.color.black));
        statusUnSend.setTextColor(getResources().getColor(R.color.black));
        statusComplete.setTextColor(getResources().getColor(R.color.black));
        tv.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    class OrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderList.size();
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
            View v = LayoutInflater.from(OrderActivity.this).inflate(R.layout.order_item, null);
            final Order order = orderList.get(i);

            TextView num = v.findViewById(R.id.num);
            num.setText(order.getOrderNumber());
            TextView amount = v.findViewById(R.id.amount);
            amount.setText(order.getTotalAmount().toString());

            final Button cancel = v.findViewById(R.id.cancel);
            Button pay = v.findViewById(R.id.pay);
            final Button evaluate = v.findViewById(R.id.evaluate);
            if(order.getStatus() == 1){
                cancel.setVisibility(View.VISIBLE);
                pay.setVisibility(View.VISIBLE);
            }else if(order.getStatus() == 3){
                evaluate.setVisibility(View.VISIBLE);
            }

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel(order.getId());
                }
            });

            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OrderActivity.this, PayActivity.class);
                    intent.putExtra("id", order.getId());
                    startActivity(intent);
                }
            });

            evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(OrderActivity.this, EvaluationActivity.class);
                    intent.putExtra("id", order.getId());
                    startActivity(intent);
                }
            });

            LinearLayout projectLayout = v.findViewById(R.id.project);

            OrderProject orderProject = order.getOrderProject();


            View project = LayoutInflater.from(OrderActivity.this).inflate(R.layout.order_project_item, null);
            TextView name = project.findViewById(R.id.name);
            name.setText(orderProject.getProjectName());
            new GlideImageLoader().displayImage(OrderActivity.this, orderProject.getPicUrl(), (ImageView)project.findViewById(R.id.pic));

            projectLayout.addView(project);

            return v;
        }
    }

    private void getOrder(){
        orderList.clear();
        PreferencesService preferencesService = new PreferencesService(this);
        String token = preferencesService.get("token");
        if(token == null || "".equals(token)){
            return;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/order/list?status="+status)
                .addHeader("token", token)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/order/list", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/order/list", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONArray orderArr = json.getJSONArray("orderList");
                        List<String> picUrls = new ArrayList<String>();
                        for(int i = 0; i < orderArr.length(); i++){
                            JSONObject orderJson = orderArr.getJSONObject(i);
                            Order order = new Order();
                            order.setId(orderJson.getInt("id"));
                            order.setOrderNumber(orderJson.getString("orderNumber"));
                            order.setTotalAmount(new BigDecimal(orderJson.getDouble("totalAmount")));
                            order.setStatus(orderJson.getInt("status"));
                            JSONObject projectObject = orderJson.getJSONObject("project");
                            OrderProject orderProject = new OrderProject();
                            orderProject.setPicUrl(projectObject.getString("picUrl"));
                            orderProject.setProjectName(projectObject.getString("projectName"));
                            order.setOrderProject(orderProject);
                            orderList.add(order);
                        }
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void cancel(int id){
        PreferencesService preferencesService = new PreferencesService(this);
        String token = preferencesService.get("token");
        if(token == null || "".equals(token)){
            return;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/order/cancel?id="+id)
                .addHeader("token", token)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/order/cancel", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/order/cancel", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        getOrder();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
}
