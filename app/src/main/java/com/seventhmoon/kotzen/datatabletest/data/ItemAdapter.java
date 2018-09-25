package com.seventhmoon.kotzen.datatabletest.data;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seventhmoon.kotzen.datatabletest.R;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    public static final String TAG = "ItemAdapter";
    private LayoutInflater inflater;

    private Context mContext;

    private int layoutResourceId;
    private ArrayList<Item> items;

    public ItemAdapter(Context context, int textViewResourceId,
                                ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        this.layoutResourceId = textViewResourceId;
        this.items = objects;
        this.mContext = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return items.size();

    }

    public Item getItem(int position)
    {
        return items.get(position);
    }
    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {

        //Log.e(TAG, "getView = "+ position);
        View view;
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            //Log.e(TAG, "convertView = null");
            /*view = inflater.inflate(layoutResourceId, null);
            holder = new ViewHolder(view);
            view.setTag(holder);*/

            //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutResourceId, null);
            holder = new ViewHolder(view);
            //holder.checkbox.setVisibility(View.INVISIBLE);
            view.setTag(holder);
        }
        else {
            view = convertView ;
            holder = (ViewHolder) view.getTag();
        }

        //holder.fileicon = (ImageView) view.findViewById(R.id.fd_Icon1);
        //holder.filename = (TextView) view.findViewById(R.id.fileChooseFileName);
        //holder.checkbox = (CheckBox) view.findViewById(R.id.checkBoxInRow);

        Item item = items.get(position);
        if (item != null) {
            holder.itemName1.setText(item.getName());
            holder.itemName2.setText(item.getQuantity());
            holder.itemName3.setText(item.getQuality());
            holder.itemName4.setText(item.getCheck());
            //holder.itemDate.setText(allocationMsgItem.getDate());


            /*if (allocationMsgItem.isSelected()) {
                //Log.e(TAG, ""+position+" is selected.");
                //view.setSelected(true);
                view.setBackgroundColor(Color.rgb(0x4d, 0x90, 0xfe));
            } else {
                //Log.e(TAG, ""+position+" clear.");
                //view.setSelected(false);
                view.setBackgroundColor(Color.TRANSPARENT);
            }*/







        }
        return view;
    }



    private class ViewHolder {
        private TextView itemName1;
        private TextView itemName2;
        private TextView itemName3;
        private TextView itemName4;

        private ViewHolder(View view) {
            this.itemName1 = view.findViewById(R.id.name1);
            this.itemName2 = view.findViewById(R.id.name2);
            this.itemName3 = view.findViewById(R.id.name3);

            this.itemName4 = view.findViewById(R.id.name4);
        }
    }
}
