package com.example.quizhum.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizhum.Pojo.ContestObject;
import com.example.quizhum.R;

import java.util.concurrent.TimeoutException;

public class TextFragment extends Fragment {
    View myView;
    int index;

    private static ContestObject contestObject;

    private Button sum;
    private TextView qno;
    private TextView des;
    private TextView op1;
    private TextView op2;
    private TextView op3;

    public static TextFragment newInstance(ContestObject conobj){
        contestObject = conobj;
        Bundle bundle = new Bundle();
        return new TextFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        myView=inflater.inflate(R.layout.text_layout,container,false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        qno = myView.findViewById(R.id.textView2);
        des = myView.findViewById(R.id.textView3);
        op1 = myView.findViewById(R.id.textView4);
        op2 = myView.findViewById(R.id.textView5);
        op3 = myView.findViewById(R.id.textView6);

       // qno.setText();

        sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fr = new TextFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putInt("IDX",index);
                fr.setArguments(args);
                ft.replace(R.id.back, fr);
                ft.commit();

            }
        });

    }
}
