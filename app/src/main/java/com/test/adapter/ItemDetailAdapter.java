package com.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.test.R;
import com.test.util.ItemTitleDAO;

import java.util.ArrayList;

public class ItemDetailAdapter extends ArrayAdapter<ItemTitleDAO> {
    private  Context mContext;
    private  ArrayList<ItemTitleDAO> titleDAOS = new ArrayList<>();
    private int layoutId;

    public ItemDetailAdapter(Context mContext, ArrayList<ItemTitleDAO> videos, int layoutId) {
        super(mContext, layoutId, videos);
        this.titleDAOS = videos;
        this.mContext = mContext;
        this.layoutId = layoutId;
    }

    public void clearListData() {titleDAOS.clear();}

    @Override
    public  int getCount() {
        return titleDAOS.size();
    }

    @Override
    public ItemTitleDAO getItem(int position) {
        return  titleDAOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  titleDAOS.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId, parent, false);
        }

        ViewHolder holder = new ViewHolder();
        holder.txtTitleName = convertView.findViewById(R.id.txtTitleName);
        holder.txtTitle = convertView.findViewById(R.id.txtTitle);
        holder.imageProfilePic = convertView.findViewById(R.id.imageProfilePic);

        ItemTitleDAO dao = titleDAOS.get(position);

        holder.txtTitleName.setText(dao.getStrTitleName());
        holder.txtTitle.setText(dao.getStrTitle());

        Picasso.get().load(dao.getStrSource()).into(holder.imageProfilePic);

        return  convertView;
    }
    private class ViewHolder {
        TextView txtTitleName, txtTitle;
        ImageView imageProfilePic;
    }
}
