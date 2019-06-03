package com.example.design1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.design1.activity.PlayStaticContest;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link rules.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link rules#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rules extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView noOfQuestions, noOfSkips, noOfHard, noOfMedium, noOfEasy;
    private Button rulesButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public rules() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment rules.
     */
    // TODO: Rename and change types and number of parameters
    public static rules newInstance(PlayStaticContest context,int noOfQuestions, int noOfSkips, int hard, int medium, int easy) {
        rules fragment = new rules();
        Bundle args = new Bundle();
        args.putInt("noOfQuestions", noOfQuestions);
        args.putInt("noOfSkips", noOfSkips);
        args.putInt("hard", hard);
        args.putInt("medium", medium);
        args.putInt("easy", easy);
        fragment.setArguments(args);

        (context.findViewById(R.id.RulesHolder)).setVisibility(View.VISIBLE);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_rules, container, false);
        noOfQuestions=(TextView)view.findViewById(R.id.total_questions);
        noOfSkips=(TextView)view.findViewById(R.id.skips_allowed);
        noOfHard=(TextView)view.findViewById(R.id.hard_questions);
        noOfMedium=(TextView)view.findViewById(R.id.medium_questions);
        noOfEasy=(TextView)view.findViewById(R.id.easy_questions);
        rulesButton=(Button)view.findViewById(R.id.rulesButton);

        Bundle bundle = getArguments();
        if(bundle != null) {
            Log.e("Bundle inside rules",bundle.toString());
            bundle.getInt("noOfQuestions", -1);
            bundle.getInt("noOfSkips", -1);
            bundle.getInt("hard", -1);
            bundle.getInt("medium", -1);
            bundle.getInt("easy", -1);

            attachThings(bundle.getInt("noOfQuestions", -1),
                    bundle.getInt("noOfSkips", -1),
                    bundle.getInt("hard", -1),
                    bundle.getInt("medium", -1),
                    bundle.getInt("easy", -1));


            noOfQuestions.setText("  "+bundle.getInt("noOfQuestions", -1));
            noOfSkips.setText("  "+bundle.getInt("noOfSkips", -1));
            noOfHard.setText("  "+bundle.getInt("hard", -1));
            noOfMedium.setText("  "+bundle.getInt("medium", -1));
            noOfEasy.setText("  " +
                    ""+bundle.getInt("easy", -1));


            rulesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (((PlayStaticContest)getContext()).findViewById(R.id.RulesHolder)).setVisibility(View.GONE);
                    // getActivity().finish();
                }
            });
        }

        return view;
    }


    public void attachThings(Integer questions, Integer skips, Integer hard, Integer medium, Integer easy){

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
