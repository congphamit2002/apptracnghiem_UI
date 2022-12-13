package com.example.testpbl4;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testpbl4.Adapter.CheckAnswerAdapter;
import com.example.testpbl4.Constant.Constant;
import com.example.testpbl4.R;
import com.example.testpbl4.ScreenSlidePageFragment;
import com.example.testpbl4.model.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */

    ArrayList<Question> listQuestion;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard stepaps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;
    private int checkState = 0, questionGrDetailId;
    private  CounterClass counterClass;
    TextView txtKiemTra, txtTime, txtViewScore;
    private int time, currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide_pager);

        listQuestion = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        listQuestion = (ArrayList<Question>) b.getSerializable("listQuestion");
        questionGrDetailId = b.getInt(Constant.ARG_QUESTION_GR_DETAIL_ID);
        Log.e("question ", "" + listQuestion.size());

        txtKiemTra = findViewById(R.id.txtKiemTra);
        txtViewScore = findViewById(R.id.txtViewScore);
        txtTime = findViewById(R.id.txtTime);

        time = b.getInt("time");
        Log.e("\t\tTIME: " , "" + time);
         counterClass = new CounterClass(time*60*1000, 1000);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        txtKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        txtViewScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ScreenSlidePagerActivity.this, TestDoneActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listQuestion",(Serializable) listQuestion);
                bundle.putInt(Constant.ARG_QUESTION_GR_DETAIL_ID, questionGrDetailId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        counterClass.start();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            final AlertDialog.Builder builder = new AlertDialog.Builder(ScreenSlidePagerActivity.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có muốn thoát hay không");
            builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    counterClass.cancel();
                    finish();
                }
            });
            builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public ArrayList<Question> getQuestionByGrDetailId() {
        return listQuestion;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Log.e("Current page activity " , ""+ position);
            currentPage = position;
            return ScreenSlidePageFragment.create(position, checkState);
        }

        @Override
        public int getCount() {
            return listQuestion.size();
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    private void checkAnswer() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.check_answer_dialog);
        dialog.setTitle("Danh sách câu trả lời");

        Button btnCancel, btnSubmit;
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnSubmit = dialog.findViewById(R.id.btnSubmit);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////
                getResult();
                counterClass.cancel();
                txtKiemTra.setVisibility(View.GONE);
                if(currentPage >= 4) {
                    mPager.setCurrentItem(0);
                }else {
                    mPager.setCurrentItem(currentPage + 3);
                }
                txtViewScore.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        CheckAnswerAdapter checkAnswerAdapter = new CheckAnswerAdapter(listQuestion, this);
        GridView view = dialog.findViewById(R.id.rclListCheckQ);
        view.setAdapter(checkAnswerAdapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPager.setCurrentItem(position);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getResult() {
        checkState = 1;
    }


    //class countdown time
    public class CounterClass extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String countTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            txtTime.setText(countTime); //SetText cho textview hiện thị thời gian.
        }

        @Override
        public void onFinish() {
            txtTime.setText("00:00");  //SetText cho textview hiện thị thời gian.
        }
    }



}