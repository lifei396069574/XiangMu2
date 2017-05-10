package com.bwei.xiangmu2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bwei.xiangmu2.R;
import com.bwei.xiangmu2.bean.JsonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {


    private final Context mContext;
    private List<JsonBean.StudentsBean.StudentBean> mData;


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final ImageView image;

        public SimpleViewHolder(View view) {
            super(view);
            title  = (TextView) view.findViewById(R.id.text);
            image  = (ImageView) view.findViewById(R.id.image);
        }
    }

    public SimpleAdapter(Context context, List<JsonBean.StudentsBean.StudentBean> data) {
        mContext = context;
        if (data != null)
            mData = data;
        else mData = new ArrayList<JsonBean.StudentsBean.StudentBean>();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.recylerview_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.title.setText(mData.get(position).getName());
        Glide.with(mContext).load("http://img1.ph.126.net/uGXCiObZfCXqWxelYpQI-g==/6597955973028472771.jpg"). diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher).crossFade().into(holder.image);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Position =" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
