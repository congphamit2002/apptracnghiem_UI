package com.example.testpbl4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testpbl4.R;
import com.example.testpbl4.model.Question;

import java.util.ArrayList;

public class CheckAnswerAdapter extends BaseAdapter {

    ArrayList<Question> listData;
    LayoutInflater inflater;

    public CheckAnswerAdapter(ArrayList<Question> listData, Context context) {
        this.listData = listData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question data = (Question) getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.check_answer_item, null);
            holder.txtAns = convertView.findViewById(R.id.txtAns);
            holder.txtNumAns = convertView.findViewById(R.id.txtNumAns);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        int i = position + 1;

        holder.txtAns.setText(data.getAnswer());
        holder.txtNumAns.setText("Câu " + i + " : ");


        return convertView;
    }

    public static class ViewHolder {
        TextView txtNumAns, txtAns;
    }
}
