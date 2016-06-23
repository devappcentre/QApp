package com.ac.qapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ac.qapp.category.CategoryPojo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by raghav on 6/5/16.
 */
public class QuizTypeAdapter extends RecyclerView.Adapter<QuizTypeAdapter.QuizTypeViewHolder> {

    List<CategoryPojo> categories;
    String[] colors;
    String[] colorsDark;
    Context context;
    int selectColorPosition = 0;
    int[] imgs = {R.drawable.sport, R.drawable.a, R.drawable.b,R.drawable.c};
    int posimg = 0;

    public QuizTypeAdapter(List<CategoryPojo> categories, String[] colorsString, String[] colorsDark) {

        this.categories = categories;
        colors = colorsString;
        this.colorsDark = colorsDark;
    }

    public void setStrings(List<CategoryPojo> strings) {

        if (strings == null) {
            strings = new ArrayList<CategoryPojo>();
        }
        this.categories = strings;
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
        return categories.size();
    }


    class QuizTypeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quizTypeNameTextView)
        TextView quizTypeNameTextView;

//        @BindView(R.id.imgCategory)
//        ImageView imgCategory;
//
//        @BindView(R.id.viewPatti)
//        View viewPatti;

        private View item;

        public QuizTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.item = itemView;

        }

        public void setDetails(int position) {

            //imgCategory.setImageResource(imgs[0]);
            quizTypeNameTextView.setText(categories.get(position).catgName);

            Timber.d(String.valueOf(position));

            String color = colors[selectColorPosition];
            String colorDark = colorsDark[selectColorPosition];
            quizTypeNameTextView.setBackgroundColor(Color.parseColor(colorDark));
            //viewPatti.setBackgroundColor(Color.parseColor(color));

            if (selectColorPosition == colors.length - 1) {
                selectColorPosition = 0;
            } else {
                selectColorPosition = selectColorPosition + 1;
            }


        }

        @OnClick(R.id.quizTypeNameTextView)
        public void onClick() {

            context.startActivity(new Intent(context, QuestionsActivity.class));
        }
    }


}
