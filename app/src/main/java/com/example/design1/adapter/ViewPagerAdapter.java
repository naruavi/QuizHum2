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


import java.io.IOException;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    List<Question> questions;
    LayoutInflater layoutInflater;
    private Context mContext;
    private Activity mActivity;

    private ConstraintLayout mRootLayout;
    private Button mButtonPlay;

    public static MediaPlayer mPlayer;
    public static MediaController mediaController;
    public static VideoView videoView;

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

        videoView = itemView.findViewById(R.id.myVideo);
        TextView textView=itemView.findViewById(R.id.textView2);
        TextView textView1=itemView.findViewById(R.id.textView3);
        RadioGroup radioGroup = itemView.findViewById(R.id.radioGroup);
        RadioButton radio1 = itemView.findViewById(R.id.textView4);
        RadioButton radio2 = itemView.findViewById(R.id.textView5);
        RadioButton radio3 = itemView.findViewById(R.id.textView6);
        CheckBox   box1 = itemView.findViewById(R.id.textView4c);
        CheckBox   box2 = itemView.findViewById(R.id.textView5c);
        CheckBox   box3 = itemView.findViewById(R.id.textView6c);
        ImageView imageView = itemView.findViewById(R.id.questionImage);
        mRootLayout = itemView.findViewById(R.id.textlayout);
        mButtonPlay = itemView.findViewById(R.id.playbutton);
        VideoView vi= imageView.findViewById(R.id.myVideo);

        Log.d("ejijaefdj", String.valueOf(questions.get(position).getOrder()));
        textView.setText(String.valueOf(questions.get(position).getOrder()));
        textView1.setText(questions.get(position).getQuestion());
      if(questions.get(position).getAnswerType().equals("single")||questions.get(position)
              .getQuestionType().equals("video")) {
          Log.e("insingle","type single");
          radio1.setVisibility(View.VISIBLE);
          radio2.setVisibility(View.VISIBLE);
          radio3.setVisibility(View.VISIBLE);
          radio1.setText("op1");//questions.get(position).getOptionA());
          radio2.setText("op2");//questions.get(position).getOptionB());
          radio3.setText("op3");//questions.get(position).getOptionC());
      }else if(questions.get(position).getAnswerType().equals("multiple"))
      {
          box1.setVisibility(View.VISIBLE);
          box2.setVisibility(View.VISIBLE);
          box3.setVisibility(View.VISIBLE);
          box1.setText("op1c");//questions.get(position).getOptionA());
          box2.setText("op2c");//questions.get(position).getOptionB());
          box3.setText("op3");//questions.get(position).getOptionC());
      }

        if(questions.get(position).getQuestionType().equals("video")){
            videoView.setVisibility(View.VISIBLE);
            Uri vidUri = Uri.parse(questions.get(position).getBinaryFilePath());
            videoView.setVideoURI(vidUri);
            mediaController = new MediaController(context);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.start();
            Log.d("video", videoView.isPlaying()+"..");

           // Log.d("video", vi.isPlaying()+"..");
        }
        else if(questions.get(position).getQuestionType().equals("image")){
            imageView.setVisibility(View.VISIBLE);
            Glide.with(itemView.getContext()).load(questions.get(position).getBinaryFilePath()).into(imageView);
        }else if(questions.get(position).getQuestionType().equals("audio")){

            mButtonPlay.setVisibility(View.VISIBLE);


            mButtonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Disable the play button
                    mButtonPlay.setEnabled(false);

                    // The audio url to play
                    String audioUrl = "http://10.177.7.137:8000/audio.ogg";

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
                            Toast.makeText(mContext,"End",Toast.LENGTH_SHORT).show();
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