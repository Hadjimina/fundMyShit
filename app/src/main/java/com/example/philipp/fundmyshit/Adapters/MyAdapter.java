package com.example.philipp.fundmyshit.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.philipp.fundmyshit.Activities.MainActivity;
import com.example.philipp.fundmyshit.Activities.YoutubeActivity;
import com.example.philipp.fundmyshit.HelperClass.HelperClass;
import com.example.philipp.fundmyshit.JavaClasses.Challenges;
import com.example.philipp.fundmyshit.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Challenges> mDataset;
    private Context mContext;
    private HelperClass helperClass;
    private int id;

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTitle,mDesc, mPriceFraction;
        Button mPledgeButton, mWatchButton;
        ProgressBar mProgressBar;
        MyViewHolder(View v) {
            super(v);

            mPledgeButton = (Button) v.findViewById(R.id.pledgeButton);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTitle = (TextView) v.findViewById(R.id.title);
            mDesc = (TextView) v.findViewById(R.id.desc);
            mPriceFraction = (TextView) v.findViewById(R.id.priceFraction);
            mWatchButton = (Button) v.findViewById(R.id.watchButton);
            mProgressBar = (ProgressBar) v.findViewById(R.id.determinateBar);
        }

    }


    public MyAdapter(ArrayList<Challenges>myDataset, int id) {
        mDataset = myDataset;
        this.id = id;
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
        this.helperClass = new HelperClass();
        System.out.println("WHICH FRAGMENT: "+id);
        updateDataSet(this.id);
        final Challenges currentChallenge = mDataset.get(position);
        holder.mTitle.setText(currentChallenge.title);
        holder.mDesc.setText(currentChallenge.description);
        holder.mPriceFraction.setText(currentChallenge.currentPrice+" / "+ currentChallenge.price);

        int progress =(currentChallenge.currentPrice) * 100/(currentChallenge.price) ;
        holder.mProgressBar.setProgress(progress);

        if (currentChallenge.currentPrice>=currentChallenge.price){
            holder.mPriceFraction.setTextColor(Color.parseColor("#8BC34A"));

        }
        if(!currentChallenge.videoLink.equals("null") && !currentChallenge.videoLink.isEmpty() && currentChallenge.currentPrice>= currentChallenge.price){
            holder.mPledgeButton.setVisibility(View.INVISIBLE);
            holder.mWatchButton.setVisibility(View.VISIBLE);
            holder.mWatchButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    Intent intent = new Intent(mContext,YoutubeActivity.class);
                    intent.putExtra("url", currentChallenge.videoLink);
                    mContext.startActivity(intent);
                }
            });
        }else{
            holder.mWatchButton.setVisibility(View.INVISIBLE);
        }

        //get Helper class object
        //OUR OWN CHALLENGES => UPLOAD VIDEO
        if (currentChallenge.userID == MainActivity.getSessionUserID()){
            holder.mPledgeButton.setText("Upload video");

            if(currentChallenge.currentPrice < currentChallenge.price){
                holder.mPledgeButton.setEnabled(false);
                holder.mPledgeButton.setTextColor(Color.GRAY);
            }else {

                //Upload video
                holder.mPledgeButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());


                        final EditText input = new EditText(v.getContext());

                        input.setText("Upload Video");
                        input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
                        input.setSingleLine(false);
                        input.setMaxLines(1);
                        input.setGravity(Gravity.CENTER | Gravity.CENTER);

                        builder.setView(input);

                        builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Challenges nowChallenge = mDataset.get(holder.getAdapterPosition());

                                int pos = holder.getAdapterPosition();
                                Challenges curr = mDataset.get(pos);
                                int ID = currentChallenge.getChallengeID();

                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("video", input.getText().toString()));


                                helperClass.doPostRequest("https://fundmyshit.herokuapp.com/challenges/" + String.valueOf(ID) + "/add_video", params);
                                notifyDataSetChanged();


                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        builder.setTitle("Upload your shit");
                        builder.setMessage("Insert your video link");


                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });
            }


        }else {
            if (currentChallenge.currentPrice >= currentChallenge.price) {
                holder.mPledgeButton.setVisibility(View.INVISIBLE);

            } else {

                holder.mPledgeButton.setText("Fund");
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
                                int pos = holder.getAdapterPosition();

                                Challenges nowChallenge = mDataset.get(pos);

                                Challenges curr = mDataset.get(pos);
                                int ID = currentChallenge.getChallengeID();

                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("amount", input.getText().toString()));
                                params.add(new BasicNameValuePair("challenge_id", String.valueOf(ID)));
                                params.add(new BasicNameValuePair("payer_id", String.valueOf(MainActivity.getSessionUserID())));


                                helperClass.doPostRequest("https://fundmyshit.herokuapp.com/payments", params);
                                currentChallenge.updateCurrentPrice(Integer.parseInt(input.getText().toString()));
                                notifyDataSetChanged();


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
        }
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
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

    private void updateDataSet(int id){
        if(id == 0){
            mDataset = helperClass.getFeedChallenges();
        }
        else if(id == 1){
            mDataset = helperClass.getMyFundedChallenges();
        }
        else if (id == 2){
            mDataset = helperClass.getPersonalChallenges();
        }
    }
}
