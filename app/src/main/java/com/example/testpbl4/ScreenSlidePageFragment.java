package com.example.testpbl4;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
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

public class ScreenSlidePageFragment extends Fragment {

    private ArrayList<Question> listQuestion;
    private int currentPage; //vi tri trang hien tai

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Current page ", ""+ currentPage);
        Log.e("Question ", listQuestion.get(currentPage).getQuestion());
        txtNum.setText("Câu " + (currentPage + 1));
        txtQuestion.setText(listQuestion.get(currentPage).getQuestion());
        radA.setText(listQuestion.get(currentPage).getOption1());
        radB.setText(listQuestion.get(currentPage).getOption2());
        radC.setText(listQuestion.get(currentPage).getOption3());
        radD.setText(listQuestion.get(currentPage).getOption4());
    }

    public static ScreenSlidePageFragment create(int pageNum) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.ARG_PAGE,pageNum);
        fragment.setArguments(args);
        return fragment;
    }
}