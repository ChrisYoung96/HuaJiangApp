package com.chrisyoung.huajiangapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.view.vinterface.OnViewGenerateListener;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

public class ShowBillGridViewAdapter extends BaseSwipeAdapter {
    private List<CBill> bills;
    private Context context;
    private OnViewGenerateListener billViewGenerateListener;


    public ShowBillGridViewAdapter(List<CBill> bs,Context c,OnViewGenerateListener listener) {
        bills = bs;
        context = c;
        billViewGenerateListener=listener;
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.gridItemLayout;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v=LayoutInflater.from(context).inflate(R.layout.grid_item,null);
        billViewGenerateListener.initView(v);

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView t=(TextView)convertView.findViewById(R.id.txtShowBillName);
        t.setText(bills.get(position).getbName());
        ImageButton btn=convertView.findViewById(R.id.btnDeleteBill);
        btn.setTag(bills.get(position).getbId());

    }

    @Override
    public int getCount() {
        return bills.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
