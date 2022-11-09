package com.example.testpbl4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testpbl4.Payload.QuestionGrDetailRespone;
import com.example.testpbl4.Payload.ShareData;
import com.example.testpbl4.R;

import java.util.ArrayList;

public class QuestionGrDetailAdapter extends RecyclerView.Adapter<QuestionGrDetailAdapter.ViewHolder>{

    private ArrayList<QuestionGrDetailRespone> listQGrsDetail;
    private Context context;
    private static QuestionGrDetailAdapter.ClickListener clickListener;

    public QuestionGrDetailAdapter(ArrayList<QuestionGrDetailRespone> listQGrsDetail, Context context) {
        this.listQGrsDetail = listQGrsDetail;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionGrDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_gr_detail_item,parent, false);

        return new QuestionGrDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionGrDetailAdapter.ViewHolder holder, int position) {
        holder.txtHeaderName.setText("" + listQGrsDetail.get(position).getName_gr_detail());
        holder.txtDescription.setText(listQGrsDetail.get(position).getDescription());
        holder.txtUsrename.setText(ShareData.userLogin.getUsername());

    }

    @Override
    public int getItemCount() {
        return listQGrsDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView txtHeaderName, txtDescription, txtUsrename;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtHeaderName = itemView.findViewById(R.id.txtHeaderName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtUsrename = itemView.findViewById(R.id.txtUsername);


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

    public void setOnItemClickListener(QuestionGrDetailAdapter.ClickListener clickListener) {
        QuestionGrDetailAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }


}

