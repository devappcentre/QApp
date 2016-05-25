package com.ac.qapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import link.fls.swipestack.SwipeStack;
import timber.log.Timber;

public class QuestionsActivity extends AppCompatActivity {

    @BindView(R.id.swipeStack)
    SwipeStack swipeStack;

    Unbinder unbinder;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        unbinder = ButterKnife.bind(this);

        progressBar.setVisibility(View.GONE);
        final QuestionsAdapter questionsAdapter = new QuestionsAdapter();
        questionsAdapter.setQuestionsAnswerClickListerner(new QuestionsAdapter.QuestionsAnswerClickListerner() {
            @Override
            public void onAnswerClick(int position) {
                Toast.makeText(QuestionsActivity.this, "Wow!Nice job.", Toast.LENGTH_SHORT).show();
                swipeStack.swipeTopViewToLeft();
            }
        });
        swipeStack.setAdapter(questionsAdapter);
        swipeStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {

            }

            @Override
            public void onViewSwipedToRight(int position) {

            }

            @Override
            public void onStackEmpty() {
                Timber.d(String.format("end"));
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(QuestionsActivity.this, "Wow!Nice job.", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        swipeStack.setSwipeProgressListener(new SwipeStack.SwipeProgressListener() {
            @Override
            public void onSwipeStart(int position) {
                Timber.d(String.format("start %d ", position));
            }

            @Override
            public void onSwipeProgress(int position, float progress) {

                Timber.d(String.format("%d %f", position, progress));

            }

            @Override
            public void onSwipeEnd(int position) {

                if ((questionsAdapter.getCount() - 1) == position) {

                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
