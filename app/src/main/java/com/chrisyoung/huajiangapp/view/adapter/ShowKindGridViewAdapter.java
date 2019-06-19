package com.chrisyoung.huajiangapp.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrisyoung.huajiangapp.R;
import com.chrisyoung.huajiangapp.domain.CBill;
import com.chrisyoung.huajiangapp.domain.CUserDiyKind;
import com.chrisyoung.huajiangapp.view.vinterface.OnViewGenerateListener;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

public class ShowKindGridViewAdapter extends BaseSwipeAdapter {
    private List<CUserDiyKind> kinds;
    private Context context;
    private OnViewGenerateListener viewGenerateListener;


    public ShowKindGridViewAdapter(List<CUserDiyKind> kinds,Context c,OnViewGenerateListener listener) {
        this.kinds = kinds;
        context = c;
        viewGenerateListener=listener;
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.gridKindItemLayout;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v=LayoutInflater.from(context).inflate(R.layout.grid_kind_item,null);
        viewGenerateListener.initView(v);

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView t=(TextView)convertView.findViewById(R.id.txtShowKindName);
        ImageView img=convertView.findViewById(R.id.icon_kind);
        t.setText(kinds.get(position).getdKind());
        img.setImageDrawable(context.getDrawable(R.mipmap.qitashouzhi2));
        ImageButton btn=convertView.findViewById(R.id.btnDeleteKind);
        btn.setTag(kinds.get(position).getdId());

    }

    @Override
    public int getCount() {
        return kinds.size();
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
