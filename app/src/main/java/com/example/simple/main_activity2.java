package com.example.simple;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class main_activity2 extends AppCompatActivity {

    private Button logOut;
    private FirebaseAuth mAuth;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    //bottom navigation
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private MoreFragment moreFragment;
    private MatchesFragment matchesFragment;
    private HomeFragment homeFragment;
    private static final String TAG="rohith";
    // url for cricket matches
    private static final String CRIC_URL="https://cricapi.com/api/matches?apikey=4abtK9oio2g3oCOJ1KaSaFcyrI43";
    private static CricMatchAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        //criMatch api
        ListView  cricMatchListView = (ListView) findViewById(R.id.cric_list);

        mAdapter=new CricMatchAdapter(this,new ArrayList<cricMatch>());

        cricMatchListView.setAdapter(mAdapter);
        cricMatchAsyncTask task = new cricMatchAsyncTask();
        task.execute(CRIC_URL);

        cricMatchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                cricMatch currentCricMatch = mAdapter.getItem(i);

                String team1 = currentCricMatch.getMteamA();

                String team2 = currentCricMatch.getMteamB();

                Intent intent = new Intent(main_activity2.this,Bet_activity.class);
                intent.putExtra("team1",team1);
                intent.putExtra("team2",team2);
                startActivity(intent);
            }
        });


        //Bottom Navigation Bar
        homeFragment=new HomeFragment();
        matchesFragment=new MatchesFragment();
        moreFragment=new MoreFragment();
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.nav_botm);
        frameLayout=(FrameLayout)findViewById(R.id.main2_frame);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFragment);
                        return true;

                    case R.id.my_matches:
                        bottomNavigationView.setItemBackgroundResource(R.color.cardview_light_background);
                        setFragment(matchesFragment);
                        return true;
                    case R.id.more:
                        bottomNavigationView.setItemBackgroundResource(R.color.cardview_dark_background);
                        setFragment(moreFragment);
                        return true;

                    default:
                        return  false;


                }
            }});

        //side Navigation Bar
        mDrawerLayout=findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth=FirebaseAuth.getInstance();

        NavigationView navigationView = findViewById(R.id.side_nav);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.my_profile:
                        setFragment(new MyProfileFragment());
                        break;
                    default:
                        return false;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main2_frame,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private static class cricMatchAsyncTask extends AsyncTask<String, Void, List<cricMatch>> {


        protected List<cricMatch> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<cricMatch> result = query.fetchData(CRIC_URL);
            Log.i(TAG,"fecthdata request called");
            return result;
        }


        protected void onPostExecute(List<cricMatch> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
                Log.i(TAG,"data added");

            }
        }
    }

}