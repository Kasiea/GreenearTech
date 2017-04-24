package com.greenear.yeqinglu.greeneartech.service;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.SpecificAddress;

import java.util.List;

/**
 * Created by yeqing.lu on 2017/4/24.
 */

public class SpecificAddressAdapter extends ArrayAdapter<SpecificAddress> {

    private int resourceId;

    public SpecificAddressAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<SpecificAddress> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SpecificAddress specificAddress = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
             view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.specific_detail_address = (TextView)view.findViewById(R.id.specific_detial_address);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.specific_detail_address.setText(specificAddress.getAddress());
        return view;
    }

    class ViewHolder{

        TextView specific_detail_address;

    }
}
