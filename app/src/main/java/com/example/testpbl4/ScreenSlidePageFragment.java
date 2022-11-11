package com.example.testpbl4;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testpbl4.Constant.Constant;
import com.example.testpbl4.R;
import com.example.testpbl4.model.Question;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;

public class ScreenSlidePageFragment extends Fragment {

    private ArrayList<Question> listQuestion;
    private int currentPage; //vi tri trang hien tai
    private int checkState;

    TextView txtNum, txtQuestion;
    RadioGroup radGroup;
    RadioButton radA, radB, radC, radD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootview =  (ViewGroup) inflater.inflate(
                R.layout.activity_screen_slide_page_fragment, container, false);
        txtNum = rootview.findViewById(R.id.txtNum);
        txtQuestion = rootview.findViewById(R.id.txtQuestion);
        radGroup = rootview.findViewById(R.id.radGroup);
        radA = rootview.findViewById(R.id.radA);
        radB = rootview.findViewById(R.id.radB);
        radC = rootview.findViewById(R.id.radC);
        radD = rootview.findViewById(R.id.radD);
        return rootview;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listQuestion = new ArrayList<>();
        ScreenSlidePagerActivity screenSlidePagerActivity = (ScreenSlidePagerActivity) getActivity();
        listQuestion = screenSlidePagerActivity.getQuestionByGrDetailId();
        currentPage = getArguments().getInt(Constant.ARG_PAGE);
        checkState = getArguments().getInt(Constant.ARG_CHECK);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Current page ", ""+ currentPage);
        Log.e("Question ", listQuestion.get(currentPage).getQuestion());
        txtNum.setText("Câu " + (currentPage + 1));
        txtQuestion.setText(getItem(currentPage).getQuestion());
        radA.setText(getItem(currentPage).getOption1());
        radB.setText(getItem(currentPage).getOption2());
        radC.setText(getItem(currentPage).getOption3());
        radD.setText(getItem(currentPage).getOption4());

        if(checkState != 0 ) {
            Log.e("\t\tCorrect Answer ", ""+ getItem(currentPage).getCorrectAnswer().toString());
            getResultAnswer(getItem(currentPage).getCorrectAnswer().toString());
        }

        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("\t\tcurrenPage ", getItem(currentPage).getCorrectAnswer());
                getItem(currentPage).setCheckedID(checkedId);
                getItem(currentPage).setAnswer(getAnswerChoiceByID(checkedId));

            }
        });

    }

    //Đưa ra câu trả lời đúng và kh cho chọn đáp án
    private void getResultAnswer(String answer ) {
        radA.setClickable(false);
        radB.setClickable(false);
        radC.setClickable(false);
        radD.setClickable(false);
        if(answer.equals("A")) {
            radA.setBackgroundColor(Color.RED);
        } else if(answer.equals("B")) {
            radB.setBackgroundColor(Color.RED);
        }else if(answer.equals("C")) {
            radC.setBackgroundColor(Color.RED);
        }else if(answer.equals("D")) {
            radD.setBackgroundColor(Color.RED);
        }
    }

    private String getAnswerChoiceByID(int id ) {
        if(id == R.id.radA) {
            return  "A";
        }else if (id == R.id.radB) {
            return "B";
        }else if (id == R.id.radC) {
            return "C";
        } else if (id == R.id.radD) {
            return "D";
        }
        return "";
    }

    public Question getItem(int position) {
        return listQuestion.get(position);
    }

    public static ScreenSlidePageFragment create(int pageNum, int checkState) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.ARG_PAGE,pageNum);
        args.putInt(Constant.ARG_CHECK, checkState);
        fragment.setArguments(args);
        return fragment;
    }
}