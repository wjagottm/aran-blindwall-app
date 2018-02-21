package com.blacetec.blindwalls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;



public class BlindWallAdapter extends BaseAdapter {

    private static final String TAG = BlindWallAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList mBlindWallArrayList;

    public BlindWallAdapter(Context context, LayoutInflater layoutInflater, ArrayList<BlindWall> blindWalls) {
        mContext = context;
        mInflator = layoutInflater;
        mBlindWallArrayList = blindWalls;
    }

    @Override
    public int getCount() {
        int size = mBlindWallArrayList.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG, "getItem " + position);
        return mBlindWallArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mInflator.inflate(R.layout.listview_row, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BlindWall blindWall = (BlindWall) mBlindWallArrayList.get(position);

        viewHolder.title.setText(blindWall.getTitle());

        if(blindWall.getImage()!=null){
            Bitmap imageMap = BitmapFactory.decodeByteArray(blindWall.getImage(), 0, blindWall.getImage().length);
            viewHolder.imageView.setImageBitmap(imageMap);
        }

        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageView;
        public TextView title;
    }


}
