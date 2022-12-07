package com.example.testpbl4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testpbl4.Payload.HistoryTestRespone;
import com.example.testpbl4.Payload.SubjectRespone;
import com.example.testpbl4.R;

import java.util.ArrayList;

public class HistoryTestAdapter extends RecyclerView.Adapter<HistoryTestAdapter.ViewHolder> {

    private ArrayList<HistoryTestRespone> listHistoryTest;
    private Context context;
    private static HistoryTestAdapter.ClickListener clickListener;

    public HistoryTestAdapter(ArrayList<HistoryTestRespone> listHistoryTest, Context context) {
        this.listHistoryTest = listHistoryTest;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryTestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_test_item,parent, false);

        return new HistoryTestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryTestAdapter.ViewHolder holder, int position) {
        holder.txtHeaderName.setText(listHistoryTest.get(position).getName_gr_detail());
        holder.txtCorrectAns.setText("" +listHistoryTest.get(position).getCorrect_answer());
        holder.txtIncorrectAns.setText("" + listHistoryTest.get(position).getIncorrect_answer());
        holder.txtScore.setText("" + listHistoryTest.get(position).getScore());
    }


    @Override
    public int getItemCount() {
        return listHistoryTest.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView txtHeaderName, txtCorrectAns, txtScore, txtDeleteTest, txtIncorrectAns;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txtHeaderName = itemView.findViewById(R.id.txtHeaderName);
            txtCorrectAns = itemView.findViewById(R.id.txtCorrectAns);
            txtIncorrectAns = itemView.findViewById(R.id.txtIncorrectAns);
            txtScore = itemView.findViewById(R.id.txtScore);
            txtDeleteTest = itemView.findViewById(R.id.txtDeleteTest);

            txtDeleteTest.setOnClickListener(this);

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

    public void setOnItemClickListener(HistoryTestAdapter.ClickListener clickListener) {
        HistoryTestAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
