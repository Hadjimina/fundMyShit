package com.example.philipp.fundmyshit.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.philipp.fundmyshit.Adapters.MyAdapter;
import com.example.philipp.fundmyshit.HelperClass.HelperClass;
import com.example.philipp.fundmyshit.JavaClasses.Challenges;
import com.example.philipp.fundmyshit.R;

import java.util.ArrayList;


public class TwoFragment extends Fragment{

    public ArrayList<Challenges> myFundedChallenges;
    public MyAdapter adapter;
    private HelperClass helperClass;
    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get Helper class object
        this.helperClass = new HelperClass();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragmen_two, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        //get currentLessons data from MainActivity


        myFundedChallenges = helperClass.getMyFundedChallenges();
        adapter = new MyAdapter(myFundedChallenges,1);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    //update currentLessons list & notify adapter of the change
    public void updateCards(){
        //get currentLessons data from MainActivity
        myFundedChallenges = helperClass.getMyFundedChallenges();
        adapter.notifyDataSetChanged();

    }
}
