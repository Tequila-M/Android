package com.wfuhui.housekeeping.ui.address;

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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.model.Address;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.PreferencesService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 地址列表
 */
public class AddressActivity extends AppCompatActivity {

    private ListView address;

    private Button addBtn;

    private List<Address> addressList = new ArrayList<>();

    private AddressAdapter addressAdapter = new AddressAdapter();

    private String fromType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setSupportActionBar(toolbar);

        fromType = getIntent().getStringExtra("fromType");

        addBtn = findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddressAddActivity.class);
                startActivity(intent);
            }
        });

        address = findViewById(R.id.address);
        address.setAdapter(addressAdapter);
        this.getAddress();


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
                    addressAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    class AddressAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return addressList.size();
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
            View v = LayoutInflater.from(AddressActivity.this).inflate(R.layout.address_item, null);
            final Address address = addressList.get(i);
            TextView name = v.findViewById(R.id.name);
            name.setText(address.getContacts() + " " + address.getMobile());
            TextView addressText = v.findViewById(R.id.address);
            addressText.setText(address.getAddress());
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent();
                    if("address".equals(fromType)){
                        i.setClass(AddressActivity.this, AddressAddActivity.class);
                        i.putExtra("fromType", "edit");
                        i.putExtra("id", address.getId());
                        startActivity(i);
                        return;
                    }


                    JSONObject json = new JSONObject();
                    try {
                        json.put("contacts", address.getContacts());
                        json.put("mobile", address.getMobile());
                        json.put("address", address.getAddress());
                        json.put("provinceName", address.getProvinceName());
                        json.put("cityName", address.getCityName());
                        json.put("districtName", address.getDistrictName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i.putExtra("address", json.toString());
                    setResult(1, i);
                    finish();
                }
            });
            return v;
        }
    }

    /**
     * 查询地址列表
     */
    private void getAddress(){
        PreferencesService preferencesService = new PreferencesService(this);
        String token = preferencesService.get("token");
        if(token == null || "".equals(token)){
            return;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/address/list")
                .addHeader("token", token)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/address/list", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/address/list", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONArray addressArr = json.getJSONArray("memberAddressList");
                        List<String> picUrls = new ArrayList<String>();
                        for(int i = 0; i < addressArr.length(); i++){
                            JSONObject addressJson = addressArr.getJSONObject(i);
                            Address address = new Address();
                            address.setId(addressJson.getInt("id"));
                            address.setContacts(addressJson.getString("contacts"));
                            address.setMobile(addressJson.getString("mobile"));
                            address.setProvinceName(addressJson.getString("provinceName"));
                            address.setAddress(addressJson.getString("address"));
                            addressList.add(address);
                        }
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
