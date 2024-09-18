package com.wfuhui.housekeeping.ui.mine;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wfuhui.housekeeping.R;
import com.wfuhui.housekeeping.model.Member;
import com.wfuhui.housekeeping.ui.address.AddressActivity;
import com.wfuhui.housekeeping.ui.login.LoginActivity;
import com.wfuhui.housekeeping.ui.order.OrderActivity;
import com.wfuhui.housekeeping.util.Constant;
import com.wfuhui.housekeeping.util.GlideImageLoader;
import com.wfuhui.housekeeping.util.PreferencesService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MineFragment extends Fragment implements View.OnClickListener{

    private ImageView avatar;
    private TextView nickname;

    private Member user;

    private LinearLayout mine_info, mine_order, mine_address;

    private Button logout;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_mine, container, false);
        avatar = root.findViewById(R.id.avatar);
        nickname = root.findViewById(R.id.nickname);
        nickname.setOnClickListener(this);
        avatar.setOnClickListener(this);

        mine_info = root.findViewById(R.id.mine_info);
        mine_order = root.findViewById(R.id.mine_order);
        mine_address = root.findViewById(R.id.mine_address);
        logout = root.findViewById(R.id.logout);

        mine_info.setOnClickListener(this);
        mine_order.setOnClickListener(this);
        mine_address.setOnClickListener(this);
        logout.setOnClickListener(this);

        return root;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nickname:
                if(user != null){
                    break;
                }
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.mine_info:
                Intent info = new Intent(getActivity(), UserActivity.class);
                startActivity(info);
                break;

            case R.id.mine_order:
                Intent order = new Intent(getActivity(), OrderActivity.class);
                startActivity(order);
                break;

            case R.id.mine_address:
                Intent address = new Intent(getActivity(), AddressActivity.class);
                address.putExtra("fromType", "address");
                startActivity(address);
                break;
            case R.id.logout:
                PreferencesService preferencesService = new PreferencesService(getActivity());
                preferencesService.remove("token");
                user = null;
                nickname.setText("未登录，点击去登录");
                avatar.setImageResource(R.mipmap.avatar);
                logout.setVisibility(View.GONE);
                break;
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
                    nickname.setText(user.getNickname());
                    new GlideImageLoader().displayImage(getContext(), user.getAvatarUrl(), (ImageView)root.findViewById(R.id.avatar));
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        PreferencesService preferencesService = new PreferencesService(getActivity());
        String token = preferencesService.get("token");
        if(token != null && !"".equals(token)){
            logout.setVisibility(View.VISIBLE);
        }
        this.getMember();
    }

    private void getMember(){
        PreferencesService preferencesService = new PreferencesService(this.getActivity());
        String token = preferencesService.get("token");
        if(token == null || "".equals(token)){
            return;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + "/api/member/info")
                .addHeader("token", token)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("/api/member/info", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("/api/member/info", "onResponse: " + result);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt(Constant.RESP_CODE);
                    if(code == 0){

                        JSONObject memberJson = json.getJSONObject("member");
                        user = new Member();
                        user.setId(memberJson.getInt("id"));
                        user.setNickname(memberJson.getString("nickname"));
                        user.setAvatarUrl(memberJson.getString("avatarUrl"));
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
