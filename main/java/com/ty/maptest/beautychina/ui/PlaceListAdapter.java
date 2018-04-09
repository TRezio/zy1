package com.ty.maptest.beautychina.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ty.maptest.beautychina.R;
import com.ty.maptest.beautychina.db.PlaceInfoBean;

import java.util.ArrayList;
import java.util.List;


public class PlaceListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<PlaceInfoBean> data = new ArrayList<>();

    public PlaceListAdapter(Context context)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<PlaceInfoBean> data)
    {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PlaceInfoBean bean = (PlaceInfoBean) getItem(i);
        ViewHolder viewHolder;
        if(view == null)
        {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_place_list,null);
            viewHolder.tvPlaceName = view.findViewById(R.id.tv_place_name);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvPlaceName.setText(bean.getName());
        return view;
    }

    class ViewHolder
    {
        TextView tvPlaceName;
    }
}
