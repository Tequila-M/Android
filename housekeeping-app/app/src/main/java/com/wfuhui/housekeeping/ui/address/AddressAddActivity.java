package com.wfuhui.housekeeping.ui.address;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.model.Address;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.PreferencesService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 地址新增
 */
public class AddressAddActivity extends AppCompatActivity {

    private EditText contactsEdit, mobileEdit, addressEdit;

    private TextView cityEdit;

    CityPickerView mPicker=new CityPickerView();

    private Button save, del;

    private String provinceName, cityName, districtName;

    private String fromType = "";

    private Address address = new Address();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contactsEdit = findViewById(R.id.contacts);
        mobileEdit = findViewById(R.id.mobile);
        addressEdit = findViewById(R.id.address);
        cityEdit = findViewById(R.id.city);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAddress();
            }
        });

        del = findViewById(R.id.del);
        del.setVisibility(View.VISIBLE);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delAddress();
            }
        });

        mPicker.init(this);
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);

        fromType = getIntent().getStringExtra("fromType");
        if("edit".equals(fromType)){
            int id = getIntent().getIntExtra("id", 0);
            this.getAddress(id);
        }

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                //省份province
                //城市city
                //地区district

                provinceName = province.getName();
                cityName = city.getName();
                districtName = district.getName();

                cityEdit.setText(provinceName + cityName + districtName);
            }

            @Override
            public void onCancel() {

            }
        });
        cityEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPicker.showCityPicker();
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
                    String info = msg.getData().getString("msg");
                    Toast.makeText(AddressAddActivity.this, info, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    contactsEdit.setText(address.getContacts());
                    mobileEdit.setText(address.getMobile());
                    addressEdit.setText(address.getAddress());
                    cityEdit.setText(address.getProvinceName() + address.getCityName() + address.getDistrictName());
            }
        }
    };

    /**
     * 保存地址
     */
    private void saveAddress(){
        String contacts = contactsEdit.getText().toString();
        String mobile = mobileEdit.getText().toString();
        String address = addressEdit.getText().toString();
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("contacts", contacts);
            json.put("mobile", mobile);
            json.put("address", address);
            json.put("provinceName", provinceName);
            json.put("cityName", cityName);
            json.put("districtName", districtName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = FormBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));

        PreferencesService preferencesService = new PreferencesService(this);
        String token = preferencesService.get("token");

        String url = "/api/address/add";
        if("edit".equals(fromType)){
            url = "/api/address/update";
        }

        Request request = new Request.Builder()
                .url(Constant.BASE_URL + url)
                .addHeader("token", token)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/address/add", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/address/add", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        Message msg = new Message();
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", "保存成功");
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getAddress(int id){
        PreferencesService preferencesService = new PreferencesService(this);
        String token = preferencesService.get("token");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/address/detail?id="+id)
                .addHeader("token", token)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/address/detail", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/address/detail", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        JSONObject addressJson = json.getJSONObject("memberAddress");
                        address.setId(addressJson.getInt("id"));
                        address.setContacts(addressJson.getString("contacts"));
                        address.setMobile(addressJson.getString("mobile"));
                        address.setProvinceName(addressJson.getString("provinceName"));
                        address.setCityName(addressJson.getString("cityName"));
                        address.setDistrictName(addressJson.getString("districtName"));
                        address.setAddress(addressJson.getString("address"));
                        handler.sendEmptyMessage(2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void delAddress(){
        PreferencesService preferencesService = new PreferencesService(this);
        String token = preferencesService.get("token");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/address/delete?id="+address.getId())
                .addHeader("token", token)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/address/delete", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/address/delete", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
