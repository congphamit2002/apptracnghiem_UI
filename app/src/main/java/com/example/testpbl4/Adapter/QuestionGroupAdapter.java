package com.example.testpbl4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testpbl4.Payload.QuestionGroupRespone;
import com.example.testpbl4.R;

import java.util.ArrayList;

public class QuestionGroupAdapter extends RecyclerView.Adapter<QuestionGroupAdapter.ViewHolder>{

    private ArrayList<QuestionGroupRespone> listQGrs;
    private Context context;
    private static QuestionGroupAdapter.ClickListener clickListener;

    public QuestionGroupAdapter(ArrayList<QuestionGroupRespone> listQGrs, Context context) {
        this.listQGrs = listQGrs;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_group_item,parent, false);

        return new QuestionGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionGroupAdapter.ViewHolder holder, int position) {
        holder.txtContent.setText("" + listQGrs.get(position).getNameGroup());

    }

    @Override
    public int getItemCount() {
        return listQGrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView txtContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtContent = itemView.findViewById(R.id.txtContent);


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

    public void setOnItemClickListener(QuestionGroupAdapter.ClickListener clickListener) {
        QuestionGroupAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }


}
