package com.example.simple;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.simple.query.fetchEarthquakeData;

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
    private CricMatchAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        //criMatch api
        ListView  cricMatchListView=(ListView)  findViewById(R.id.cric_list);

        mAdapter=new CricMatchAdapter(this,new ArrayList<cricMatch>());

       cricMatchListView.setAdapter(mAdapter);
        cricMatchAsyncTask task = new cricMatchAsyncTask();
        task.execute(CRIC_URL);

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
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      mAuth=FirebaseAuth.getInstance();


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
    private class cricMatchAsyncTask extends AsyncTask<String, Void, List<cricMatch>> {


        protected List<cricMatch> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<cricMatch> result = query.fetchEarthquakeData(CRIC_URL);
            return result;
        }


        protected void onPostExecute(List<cricMatch> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);

            }
        }
    }

}
