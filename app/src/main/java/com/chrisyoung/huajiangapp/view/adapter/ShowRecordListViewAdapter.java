package com.chrisyoung.huajiangapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.vinterface.OnViewGenerateListener;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowRecordListViewAdapter extends BaseSwipeAdapter {
    private ArrayList<CRecord> records;
    private Context context;
    private OnViewGenerateListener listener;


    public ShowRecordListViewAdapter(ArrayList<CRecord> records, Context context,OnViewGenerateListener listener) {
        this.records = records;
        this.context = context;
        this.listener=listener;

    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.list_record_item_layout;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v=LayoutInflater.from(context).inflate(R.layout.list_record_item_layout,null);
        listener.initView(v);
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView txtRKind=convertView.findViewById(R.id.txtRKind);
        TextView txtRMoney=convertView.findViewById(R.id.txtRMoney);
        TextView txtRWay=convertView.findViewById(R.id.txtRWay);
        TextView txtRTime=convertView.findViewById(R.id.txtRTime);
        Button btn=convertView.findViewById(R.id.btnDeleteRecord);
        btn.setTag(records.get(position).getrId());
        txtRKind.setText(records.get(position).getrKind());
        txtRWay.setText(records.get(position).getrWay());
        txtRMoney.setText(String.valueOf(records.get(position).getrMoney()));
        txtRTime.setText(DateFormatUtil.dateToString(records.get(position).getrTime()));

    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
