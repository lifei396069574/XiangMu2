package com.bwei.xiangmu2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bwei.xiangmu2.adapter.SimpleAdapter;
import com.bwei.xiangmu2.bean.JsonBean;
import com.bwei.xiangmu2.utils.MyUri;
import com.bwei.xiangmu2.utils.OKHttpUtils;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recylerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

        recylerview = (RecyclerView) findViewById(R.id.recylerview);

        recylerview.setHasFixedSize(true);
        recylerview.setLayoutManager(new LinearLayoutManager(this));


        OKHttpUtils okHttpUtils = new OKHttpUtils();

        okHttpUtils.getAsy(MyUri.url, new OKHttpUtils.HttpCallBack() {
            @Override
            public void onSusscess(String data) {

                Gson gson = new Gson();
                JsonBean jsonBean = gson.fromJson(data, JsonBean.class);
                List<JsonBean.StudentsBean.StudentBean> list = jsonBean.getStudents().getStudent();
                recylerview.setAdapter(new SimpleAdapter(MainActivity.this,list));
                Log.i("zzz", "data :" + data);
            }
        });


    }
}
