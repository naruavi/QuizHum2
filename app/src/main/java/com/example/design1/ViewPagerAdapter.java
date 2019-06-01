package com.example.design1;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


import com.bumptech.glide.Glide;
import com.example.design1.Pojo.Question;

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

        VideoView vidView = itemView.findViewById(R.id.myVideo);
        TextView textView=itemView.findViewById(R.id.textView2);
        TextView textView1=itemView.findViewById(R.id.textView3);
        TextView textView2=itemView.findViewById(R.id.textView4);
        TextView textView3=itemView.findViewById(R.id.textView5);
        TextView textView4=itemView.findViewById(R.id.textView6);
        ImageView imageView = itemView.findViewById(R.id.questionImage);


        Log.d("ejijaefdj", String.valueOf(questions.get(position).getOrder()));
        textView.setText(String.valueOf(questions.get(position).getOrder()));
        textView1.setText(questions.get(position).getQuestion());
        textView2.setText(questions.get(position).getOptionA());
        textView3.setText(questions.get(position).getOptionB());
        textView4.setText(questions.get(position).getOptionC());

        if(questions.get(position).getQuestionType().equals("video")){
            vidView.setVisibility(View.VISIBLE);
            Uri vidUri = Uri.parse(questions.get(position).getBinaryFilePath());
            vidView.setVideoURI(vidUri);
            MediaController vidControl = new MediaController(context);
            vidControl.setAnchorView(vidView);
            vidView.setMediaController(vidControl);
            vidView.start();
        }
        else if(questions.get(position).getQuestionType().equals("video")){
            imageView.setVisibility(View.VISIBLE);
            Glide.with(itemView.getContext()).load(questions.get(position).getBinaryFilePath()).into(imageView);
        }


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