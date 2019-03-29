package com.chrisyoung.huajiangapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CRecord;
import com.chrisyoung.huajiangapp.domain.RViewModel;
import com.chrisyoung.huajiangapp.uitils.DateFormatUtil;
import com.chrisyoung.huajiangapp.view.ModifyRecordActivity;
import com.chrisyoung.huajiangapp.view.MyListView;
import com.chrisyoung.huajiangapp.view.vinterface.OnViewGenerateListener;

import java.util.ArrayList;

public class ShowRecordModleListViewAdapter extends BaseAdapter {
    private ArrayList<RViewModel> models;
    private Context context;
    private OnViewGenerateListener listener;

    public ShowRecordModleListViewAdapter(ArrayList<RViewModel> models,Context context,OnViewGenerateListener listener) {
        this.models = models;
        this.context=context;
        this.listener=listener;
    }

    public ArrayList<RViewModel> getModels() {
        return models;
    }

    public void setModels(ArrayList<RViewModel> models) {
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(models!=null && !models.isEmpty()){
            ArrayList<CRecord> records=models.get(position).getRecords();
            if(convertView==null){
                convertView=LayoutInflater.from(context).inflate(R.layout.list_record_model_item_layout,null);
                holder=new ViewHolder();
                holder.txtRDate=convertView.findViewById(R.id.txtRecordDate);
                holder.recordItemListView=convertView.findViewById(R.id.recordItemListView);
                holder.txtTotalCost=convertView.findViewById(R.id.txtTotalCostInADay);
                holder.txtTotalIncome=convertView.findViewById(R.id.txtTotalIncomeInADay);
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder)convertView.getTag();
            }
            holder.txtRDate.setText(DateFormatUtil.dateToString(DateFormatUtil.longDateToshortDate(models.get(position).getDay())));
            holder.txtTotalIncome.setText(String.valueOf(models.get(position).getTotalIncome()));
            holder.txtTotalCost.setText(String.valueOf(models.get(position).getTotalCost()));
            ShowRecordListViewAdapter listViewAdapter=new ShowRecordListViewAdapter(models.get(position).getRecords(),context,listener);
            holder.recordItemListView.setAdapter(listViewAdapter);
            holder.recordItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String rId=records.get(position).getrId();
                    Intent intent=new Intent(context,ModifyRecordActivity.class);
                    intent.putExtra("rId",rId);
                    context.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    private class  ViewHolder{
        private TextView txtRDate;
        private MyListView recordItemListView;
        private TextView txtTotalCost;
        private TextView txtTotalIncome;
    }
}
