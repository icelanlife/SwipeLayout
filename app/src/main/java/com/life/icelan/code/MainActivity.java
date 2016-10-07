package com.life.icelan.code;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initData() {
        ArrayList<Msg> list=new ArrayList<Msg>();
        for(int i=0;i<Cheeses.NAMES.length;i++){
            Msg msg=new Msg(Cheeses.NAMES[i],Cheeses.CHEESE_STRINGS[i]);
            list.add(msg);
        }
        listview.setAdapter(new MyAdapter(list));
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
    }
}
