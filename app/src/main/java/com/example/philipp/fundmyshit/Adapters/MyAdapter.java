package com.example.philipp.fundmyshit.Adapters;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.philipp.fundmyshit.JavaClasses.Challenges;
import com.example.philipp.fundmyshit.R;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Challenges> mDataset;

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTitle,mDesc, mPriceFraction;
        Button mButtonPledge;

        MyViewHolder(View v) {
            super(v);

            mButtonPledge = (Button) v.findViewById(R.id.pledgeButton);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTitle = (TextView) v.findViewById(R.id.title);
            mDesc = (TextView) v.findViewById(R.id.desc);
            mPriceFraction = (TextView) v.findViewById(R.id.pledgeButton);

        }
    }

    public MyAdapter(ArrayList<Challenges>myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTitle.setText(mDataset.get(position).title);
        //holder.mTextViewDesc.setText(mDataset.get(position).desc);
        //holder.mImageView.setImageResource(mDataset.get(position).coverImg);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void removeAndUpdateData(String itemToRemove){
        ArrayList<Challenges> listCopy = mDataset;


        for (int i = 0; i < mDataset.size(); i++){
            if(listCopy.get(i).title.equals(itemToRemove)){
                listCopy.remove(i);
            }
        }


        mDataset = listCopy;
        notifyDataSetChanged();
    }
}