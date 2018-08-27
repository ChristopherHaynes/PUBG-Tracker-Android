package wxc.pubgtracker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Fragment activeFragment;
    FragmentManager fm;
    FragmentTransaction transaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_player_stats:
                    activeFragment = new GeneralFragment();
                    fm = getSupportFragmentManager();
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.contentFragment, activeFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_match_stats:
                    activeFragment = new MatchFragment();
                    fm = getSupportFragmentManager();
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.contentFragment, activeFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_wxc:
                    activeFragment = new WXCFragment();
                    fm = getSupportFragmentManager();
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.contentFragment, activeFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_leaderboards:
                    activeFragment = new LeaderboardFragment();
                    fm = getSupportFragmentManager();
                    transaction = fm.beginTransaction();
                    transaction.replace(R.id.contentFragment, activeFragment);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        activeFragment = new GeneralFragment();
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, activeFragment);
        transaction.commit();
    }
}
