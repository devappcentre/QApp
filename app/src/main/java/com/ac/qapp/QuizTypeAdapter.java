package com.ac.qapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by raghav on 6/5/16.
 */
public class QuizTypeAdapter extends RecyclerView.Adapter<QuizTypeAdapter.QuizTypeViewHolder> {

    List<String> strings;
    String[] colors;
    Context context;
    int selectColorPosition = 0;

    public QuizTypeAdapter(List<String> strings, String[] colorsString) {

        this.strings = strings;
        colors = colorsString;

    }

    public void setStrings(List<String> strings) {

        if (strings == null) {
            strings = new ArrayList<>();
        }
        this.strings = strings;
    }

    @Override
    public QuizTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new QuizTypeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_quiz_type_question, parent, false));
    }

    @Override
    public void onBindViewHolder(QuizTypeViewHolder holder, int position) {
        holder.setDetails(holder.getAdapterPosition());

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public void setGallery(List<String> strings) {
        if (this.strings == null) {
            this.strings = new ArrayList<>();
        }
        this.strings = strings;
    }


    class QuizTypeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quizTypeNameTextView)
        TextView quizTypeNameTextView;


        public QuizTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        public void setDetails(int position) {
            quizTypeNameTextView.setText(String.valueOf(position));

            Timber.d(String.valueOf(position));

            if (selectColorPosition == colors.length) {
                selectColorPosition = 0;
            } else {

                String color = colors[selectColorPosition];
                quizTypeNameTextView.setBackgroundColor(Color.parseColor(color));
                selectColorPosition++;

            }

        }

        @OnClick(R.id.quizTypeNameTextView)
        public void onClick() {

            context.startActivity(new Intent(context, QuestionsActivity.class));
        }
    }


}
