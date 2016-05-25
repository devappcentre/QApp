package com.ac.qapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Raghavthakkar on 25-05-2016.
 */

public class QuestionsAdapter extends BaseAdapter {
    @BindView(R.id.button)
    Button button;
    static QuestionsAnswerClickListerner questionsAnswerClickListerner;


    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public String getItem(int position) {
        return String.valueOf(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setDetails(position);


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.quizTypeNameTextView)
        TextView quizTypeNameTextView;
        @BindView(R.id.button)
        Button button;

        int positions = 0;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setDetails(int position) {
            quizTypeNameTextView.setText(String.valueOf(position));
            positions = position;
        }

        @OnClick(R.id.button)
        public void onClick() {
            questionsAnswerClickListerner.onAnswerClick(positions);
        }
    }

    public interface QuestionsAnswerClickListerner {
        void onAnswerClick(int position);
    }

    public void setQuestionsAnswerClickListerner(QuestionsAnswerClickListerner questionsAnswerClickListerner) {
        QuestionsAdapter.questionsAnswerClickListerner = questionsAnswerClickListerner;
    }

}
