package com.example.philipp.fundmyshit.Activities;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.philipp.fundmyshit.Fragments.OneFragment;
import com.example.philipp.fundmyshit.Fragments.ThreeFragment;
import com.example.philipp.fundmyshit.Fragments.TwoFragment;
import com.example.philipp.fundmyshit.HelperClass.HelperClass;
import com.example.philipp.fundmyshit.JavaClasses.Challenges;
import com.example.philipp.fundmyshit.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.input;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private int[] tabIcons = {
            R.drawable.ic_home_white,
            R.drawable.ic_check_white,
            R.drawable.ic_people_white
    };
    private ViewPagerAdapter adapter;
    private HelperClass helperClass;
    private ArrayList<Challenges> feedChallenges;
    private static Integer sessionUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get Helper class object
        this.helperClass = new HelperClass();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        sessionUserID = getIntent().getIntExtra("userID", 1);




        //fab setup & onclick
        fab = (FloatingActionButton) findViewById(R.id.fab);
        final EditText title_input = (EditText) findViewById(R.id.title_input);
        final EditText description = (EditText) findViewById(R.id.description);
        final EditText shmeckles = (EditText) findViewById(R.id.shmeckles);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO change dummyID to correct id (from shared preference)


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                final EditText title = new EditText(v.getContext());
               /* final EditText desc = new EditText(v.getContext());
                final EditText schmeckles = new EditText(v.getContext());*/
                title.setText("0");
                title.setInputType(InputType.TYPE_CLASS_NUMBER);
                title.setSingleLine(false);
                title.setGravity(Gravity.CENTER | Gravity.CENTER);
               /* desc.setText("0");
                desc.setInputType(InputType.TYPE_CLASS_NUMBER);
                desc.setSingleLine(false);
                desc.setGravity(Gravity.CENTER | Gravity.CENTER);*/

                builder.setView(input);

                builder.setPositiveButton("Fund", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String titleString = title.getText().toString();
                       /* String descString = desc.getText().toString();
                        String schmecklesString = schmeckles.getText().toString();*/

                        String url = "https://fundmyshit.herokuapp.com/challenges";
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("title", titleString));
                        /*params.add(new BasicNameValuePair("description", descString));
                        params.add(new BasicNameValuePair("price", schmecklesString));*/
                        params.add(new BasicNameValuePair("id", sessionUserID.toString()));

                        helperClass.doPostRequest(url,params);

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


/*
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();

                builder.setTitle("New challenge")
                        .setView(inflater.inflate(R.layout.dialog_new_challenge, null))
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String titleString = title_input.getText().toString();
                        String descriptionString = description.getText().toString();
                        Integer shmecklesInt =  Integer.parseInt(shmeckles.getText().toString());

                        Challenges newChallenge = new Challenges(titleString, sessionUserID, shmecklesInt, descriptionString);

                        String url = "https://fundmyshit.herokuapp.com/challenges";
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("title", titleString));
                        params.add(new BasicNameValuePair("description", descriptionString));
                        params.add(new BasicNameValuePair("price", shmecklesInt.toString()));
                        params.add(new BasicNameValuePair("id", sessionUserID.toString()));
*/
                        //????

            }
        });




    }


    //Disable back button
    @Override
    public void onBackPressed() {}

    //Options Menu
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    //Tabs Settup
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        //setup tabs
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "ONE");
        adapter.addFrag(new TwoFragment(), "TWO");
        adapter.addFrag(new ThreeFragment(), "THREE");


        viewPager.setAdapter(adapter);


        //setup fab
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fab.show();
                        break;

                    default:
                        fab.hide();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        SparseArray<Fragment> registeredFragments = new SparseArray<>();

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
    public static int getSessionUserID(){
        return sessionUserID;
    }
}
