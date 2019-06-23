package com.example.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

public class main_activity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        //Bottom Navigation Bar
        homeFragment=new HomeFragment();
        matchesFragment=new MatchesFragment();
        moreFragment=new MoreFragment();
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.nav_botm);
        frameLayout=(FrameLayout)findViewById(R.id.main2_frame);

        // bottom navigation listner
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
            }
        });

        //side Navigation Bar
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //  Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      mAuth=FirebaseAuth.getInstance();

        NavigationView navigationView = findViewById(R.id.side_nav);

        navigationView.setNavigationItemSelectedListener(this);




    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main2_frame,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            return true;
     }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.my_profile:
                setFragment(new MyProfileFragment());
                break;
        }

        mDrawerLayout.closeDrawer(Gravity.START);
        return true;
    }
}
