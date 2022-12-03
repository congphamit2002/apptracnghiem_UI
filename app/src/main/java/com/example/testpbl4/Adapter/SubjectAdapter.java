package com.example.testpbl4.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testpbl4.Payload.SubjectRespone;
import com.example.testpbl4.R;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private ArrayList<SubjectRespone> listSubject;
    private Context context;
    private static ClickListener clickListener;

    public SubjectAdapter(ArrayList<SubjectRespone> listSubject, Context context) {
        this.listSubject = listSubject;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_item,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(listSubject.get(position).getSubjectName());
        String src = listSubject.get(position).getImage();
//        int drawableId = context.getResources().getIdentifier(src, "drawable", context.getPackageName());
        int drawableId = context.getResources().getIdentifier(src, "drawable", context.getPackageName());
//        holder.icon.setImageResource(drawableId);
        holder.icon.setImageResource(drawableId);
    }


    @Override
    public int getItemCount() {
        return listSubject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView txtName;
        public ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtName = itemView.findViewById(R.id.txtName);
            icon = itemView.findViewById(R.id.icon);

            icon.setOnClickListener(this);
            icon.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        SubjectAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
