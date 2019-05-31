package com.example.quizhum;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quizhum.Pojo.Question;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    List<Question> questions;
    LayoutInflater layoutInflater;



    public ViewPagerAdapter(Context context, List<Question> questions1) {
        Log.d("custompager", "workscustructor");
        this.context = context;
        this.questions = questions1;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.text_layout, container, false);

        Log.d("custompager", "worksinstantiate");

        TextView textView=itemView.findViewById(R.id.textView2);
        TextView textView1=itemView.findViewById(R.id.textView3);
        TextView textView2=itemView.findViewById(R.id.textView4);
        TextView textView3=itemView.findViewById(R.id.textView5);
        TextView textView4=itemView.findViewById(R.id.textView6);




        Log.d("ejijaefdj", String.valueOf(questions.get(position).getOrder()));
        textView.setText(String.valueOf(questions.get(position).getOrder()));
        textView1.setText(questions.get(position).getQuestion());
        textView2.setText(questions.get(position).getOptions().get(1));
        textView3.setText(questions.get(position).getOptions().get(2));
        textView4.setText(questions.get(position).getOptions().get(3));


        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}