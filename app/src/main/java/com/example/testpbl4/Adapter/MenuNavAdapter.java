package com.example.testpbl4.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testpbl4.R;
import com.example.testpbl4.model.ItemMenu;

import java.util.List;

public class MenuNavAdapter extends BaseAdapter {

    Activity activity;
    List<ItemMenu> listItemMenu;

    public MenuNavAdapter(Activity activity, List<ItemMenu> listItemMenu){
        this.activity = activity;
        this.listItemMenu = listItemMenu;
    }

    @Override
    public int getCount() {
        return listItemMenu.size();
    }

    @Override
    public Object getItem(int i) {
        return listItemMenu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.row_item_nav,null);

        LinearLayout linearLayout = view.findViewById(R.id.containerItem);
        ImageView imageView = view.findViewById(R.id.iconItem);
        TextView textView = view.findViewById(R.id.nameIcon);

        imageView.setImageResource(listItemMenu.get(i).iconItem);
        textView.setText(listItemMenu.get(i).nameItem);
        return view;
    }
}
