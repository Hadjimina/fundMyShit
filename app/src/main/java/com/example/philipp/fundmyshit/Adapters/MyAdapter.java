package com.example.philipp.fundmyshit.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.philipp.fundmyshit.JavaClasses.Challenges;
import com.example.philipp.fundmyshit.R;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Challenges> mDataset;
    private Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTitle,mDesc, mPriceFraction;
        Button mPledgeButton;

        MyViewHolder(View v) {
            super(v);

            mPledgeButton = (Button) v.findViewById(R.id.pledgeButton);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTitle = (TextView) v.findViewById(R.id.title);
            mDesc = (TextView) v.findViewById(R.id.desc);
            mPriceFraction = (TextView) v.findViewById(R.id.priceFraction);

        }
    }

    public MyAdapter(ArrayList<Challenges>myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        mContext = parent.getContext();

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Challenges currentChallenge = mDataset.get(position);
        holder.mTitle.setText(currentChallenge.title);
        holder.mDesc.setText(currentChallenge.description);
        holder.mPriceFraction.setText(currentChallenge.currentPrice+" / "+ currentChallenge.price);


        holder.mPledgeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());


                final EditText input = new EditText(v.getContext());

                input.setText("0");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setSingleLine(false);
                input.setMaxLines(1);
                input.setGravity(Gravity.CENTER | Gravity.CENTER);

                builder.setView(input);

                builder.setPositiveButton("Fund", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //TODO add fund amount on challenge

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.setTitle("Fund this shit");
                builder.setMessage("Insert your amount");


                AlertDialog dialog = builder.create();
                dialog.show();

            }
            });
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