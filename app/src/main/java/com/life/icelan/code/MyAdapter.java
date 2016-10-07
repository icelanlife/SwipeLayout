package com.life.icelan.code;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/7 0007.
 */

public class MyAdapter extends BaseAdapter {
    private final ArrayList<Msg> list;

    public MyAdapter(ArrayList<Msg> list) {
        this.list=list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if(convertView==null){
            convertView=View.inflate(parent.getContext(),R.layout.lv_swipelayout_item,null);
            viewholder=new ViewHolder();
            viewholder.name=(TextView) convertView.findViewById(R.id.tv_name);
            viewholder.context= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewholder);
        }else{
            viewholder= (ViewHolder) convertView.getTag();
        }

        viewholder.name.setText(list.get(position).getName());
        viewholder.context.setText(list.get(position).getContent());
        return convertView;
    }
    class ViewHolder{
        TextView name;
        TextView context;

    }
}
