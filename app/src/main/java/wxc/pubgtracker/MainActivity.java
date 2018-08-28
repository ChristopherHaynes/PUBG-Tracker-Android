package wxc.pubgtracker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

    PUBGManager manager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_player_stats:
                    activeFragment = new GeneralFragment();
                    UpdateActiveFragment();
                    return true;
                case R.id.navigation_match_stats:
                    activeFragment = new MatchFragment();
                    UpdateActiveFragment();
                    return true;
                case R.id.navigation_wxc:
                    activeFragment = new WXCFragment();
                    UpdateActiveFragment();
                    return true;
                case R.id.navigation_leaderboards:
                    activeFragment = new LeaderboardFragment();
                    UpdateActiveFragment();
                    return true;
            }
            return false;
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        manager = MyProperties.getInstance().pubgManager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.GONE);

        activeFragment = new LoadingFragment();
        UpdateActiveFragment();

        WaitForLoadedStats();
    }

    void WaitForLoadedStats () {
        manager = MyProperties.getInstance().pubgManager;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (manager.players.size() == 4) {
                    if (manager.players.get(0).getStatsLoaded() &&
                            manager.players.get(1).getStatsLoaded() &&
                            manager.players.get(2).getStatsLoaded() &&
                            manager.players.get(3).getStatsLoaded()) {
                        activeFragment = new GeneralFragment();
                        UpdateActiveFragment();
                        BottomNavigationView navigation = findViewById(R.id.navigation);
                        navigation.setVisibility(View.VISIBLE);

                    } else {
                        WaitForLoadedStats();
                        activeFragment = new LoadingFragment();
                        UpdateActiveFragment();
                    }
                } else {
                    WaitForLoadedStats();
                    activeFragment = new LoadingFragment();
                    UpdateActiveFragment();
                }

            }
        }, 10);
    }

    void UpdateActiveFragment() {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, activeFragment);
        transaction.commit();
    }
}
