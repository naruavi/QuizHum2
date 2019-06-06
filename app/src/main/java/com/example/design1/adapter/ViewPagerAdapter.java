package com.example.design1.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.design1.Pojo.Question;
import com.example.design1.R;
import com.example.design1.models.QuestionDefinition;


import java.io.IOException;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    int contestId;

    List<QuestionDefinition> questions;
    LayoutInflater layoutInflater;
    private Context mContext;
    private Activity mActivity;

    private ConstraintLayout mRootLayout;
    private Button mButtonPlay;
    private Button mStop;


    public static MediaPlayer mPlayer;
    public static MediaController mediaController;
    private VideoView videoView;

    RadioGroup radioGroup;
    RadioButton radio1;
    RadioButton radio2;
    RadioButton radio3;

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    View itemView;

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
    }

    public RadioButton getRadio1() {
        return radio1;
    }

    public void setRadio1(RadioButton radio1) {
        this.radio1 = radio1;
    }

    public RadioButton getRadio2() {
        return radio2;
    }

    public void setRadio2(RadioButton radio2) {
        this.radio2 = radio2;
    }

    public RadioButton getRadio3() {
        return radio3;
    }

    public void setRadio3(RadioButton radio3) {
        this.radio3 = radio3;
    }

    public CheckBox getBox1() {
        return box1;
    }

    public void setBox1(CheckBox box1) {
        this.box1 = box1;
    }

    public CheckBox getBox2() {
        return box2;
    }

    public void setBox2(CheckBox box2) {
        this.box2 = box2;
    }

    public CheckBox getBox3() {
        return box3;
    }

    public void setBox3(CheckBox box3) {
        this.box3 = box3;
    }

    CheckBox   box1;
    CheckBox   box2;
    CheckBox   box3;



    public ViewPagerAdapter(Context context, List<QuestionDefinition> questions1) {
        this.context = context;
        this.questions = questions1;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("custompager", questions.toString());
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        itemView = layoutInflater.inflate(R.layout.text_layout, container, false);
        itemView.setTag("currentView" + questions.get(position).getQuestionId());
        Log.d("custompager", "worksinstantiate");

        videoView = itemView.findViewById(R.id.myVideo);
        TextView textView=itemView.findViewById(R.id.textView2);
        //textView.setId(contestId + questions.get(position).getQuestionId());
        TextView textView1=itemView.findViewById(R.id.textView3);
        //textView1.setId("a"+questions.get(position).getQuestionId());
        //textView1.setText("a"+questions.get(position).getQuestionId());
         radioGroup = itemView.findViewById(R.id.radioGroup);
         radio1 = itemView.findViewById(R.id.textView4);
         radio2 = itemView.findViewById(R.id.textView5);
         radio3 = itemView.findViewById(R.id.textView6);
           box1 = itemView.findViewById(R.id.textView4c);
           box2 = itemView.findViewById(R.id.textView5c);
           box3 = itemView.findViewById(R.id.textView6c);
        ImageView imageView = itemView.findViewById(R.id.questionImage);
        mRootLayout = itemView.findViewById(R.id.textlayout);
        mButtonPlay = itemView.findViewById(R.id.playbutton);
        mStop = itemView.findViewById(R.id.mStop);
        VideoView vi= imageView.findViewById(R.id.myVideo);

        Log.d("ejijaefdj", String.valueOf(questions.get(position)));
        textView.setText(String.valueOf(position + 1));
        textView1.setText(questions.get(position).getQuestionText());
      if(questions.get(position).getAnswerType().equals("Single-Correct")) {
          Log.e("insingle","type single");
          radio1.setVisibility(View.VISIBLE);
          radio2.setVisibility(View.VISIBLE);
          radio3.setVisibility(View.VISIBLE);
          radio1.setText(questions.get(position).getOptionA());
          radio2.setText(questions.get(position).getOptionB());
          radio3.setText(questions.get(position).getOptionC());
      }else if(questions.get(position).getAnswerType().equals("Multiple-Correct"))
      {
          box1.setVisibility(View.VISIBLE);
          box2.setVisibility(View.VISIBLE);
          box3.setVisibility(View.VISIBLE);
          box1.setText(questions.get(position).getOptionA());
          box2.setText(questions.get(position).getOptionB());
          box3.setText(questions.get(position).getOptionC());
      }
      else{
          radio1.setVisibility(View.VISIBLE);
          radio2.setVisibility(View.VISIBLE);
          radio3.setVisibility(View.VISIBLE);
          radio1.setText(questions.get(position).getOptionA());
          radio2.setText(questions.get(position).getOptionB());
          radio3.setText(questions.get(position).getOptionC());
      }
        if(questions.get(position).getQuestionType().equals("Video-Based")){
            videoView.setVisibility(View.VISIBLE);
            try{
            Uri vidUri = Uri.parse(questions.get(position).getBinaryFilePath());
            videoView.setVideoURI(vidUri);
            mediaController = new MediaController(context);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.start();
            Log.d("video", videoView.isPlaying()+"..");
            }catch (Exception e){
                e.printStackTrace();
            }

           // Log.d("video", vi.isPlaying()+"..");
        }
        else if(questions.get(position).getQuestionType().equals("Image-Based")){
            imageView.setVisibility(View.VISIBLE);
            try {
                Glide.with(itemView.getContext()).load(questions.get(position).getBinaryFilePath()).into(imageView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(questions.get(position).getQuestionType().equals("Audio-Based")){
            mButtonPlay.setVisibility(View.VISIBLE);
            mStop.setVisibility(View.VISIBLE);
            mStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ViewPagerAdapter.mPlayer!=null && ViewPagerAdapter.mPlayer.isPlaying()){
                        ViewPagerAdapter.mPlayer.stop();
                        ViewPagerAdapter.mPlayer.reset();
                        ViewPagerAdapter.mPlayer = null;
                    }
                }
            });

            mButtonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Disable the play button
                    mButtonPlay.setEnabled(false);

                    // The audio url to play
                    String audioUrl = questions.get(position).getBinaryFilePath();

                    // Initialize a new media player instance
                    mPlayer = new MediaPlayer();

                    // Set the media player audio stream type
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    //Try to play music/audio from url
                    try{

                        mPlayer.setDataSource(audioUrl);

                        mPlayer.prepare();

                        // Start playing audio from http url
                        mPlayer.start();

                        // Inform user for audio streaming
                        Toast.makeText(context,"Playing",Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        // Catch the exception
                        e.printStackTrace();
                    }catch (IllegalArgumentException e){
                        e.printStackTrace();
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }catch (IllegalStateException e){
                        e.printStackTrace();
                    }

                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mButtonPlay.setEnabled(true);
                        }
                    });
                }
            });
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