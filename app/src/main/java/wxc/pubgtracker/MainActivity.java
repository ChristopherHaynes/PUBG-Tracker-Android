package wxc.pubgtracker;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Global variables for maintaining and transitioning of the active fragment
    Fragment activeFragment;
    FragmentManager fm;
    FragmentTransaction transaction;

    // Class reference to the global PUBG manager instance
    PUBGManager manager;

    // Options menu holder
    Menu optionsMenu;

    // Shared preferences reference for persistent variables
    SharedPreferences preferences;

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
        manager.loadAPIKEY(this);
        preferences = getSharedPreferences("userprefs", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.GONE);

        activeFragment = new LoadingFragment();
        UpdateActiveFragment();

        WaitForLoadedStats();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        menu.findItem(R.id.default_name).setVisible(false);
        menu.findItem(R.id.change_api_key).setVisible(false);
        optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.default_name:
                DialogFragment defaultUserDialog = new UserDiaglogFragment();
                defaultUserDialog.show(getSupportFragmentManager(), "defaultUserDialog");
                return true;
            case R.id.change_api_key:
                DialogFragment changeAPIDialog = new ChangeAPIKEYDialogFragment();
                changeAPIDialog.show(getSupportFragmentManager(), "changeAPIDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        void WaitForLoadedStats () {
        invalidateOptionsMenu();
        manager = MyProperties.getInstance().pubgManager;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (manager.players.size() == 4) {
                    // If all players data is loaded, launch the main app interface
                    if (manager.players.get(0).getStatsLoaded() &&
                            manager.players.get(1).getStatsLoaded() &&
                            manager.players.get(2).getStatsLoaded() &&
                            manager.players.get(3).getStatsLoaded()) {
                        // Start the main fragment
                        activeFragment = new GeneralFragment();
                        UpdateActiveFragment();
                        // Make the bottom navigation and options menu visible
                        BottomNavigationView navigation = findViewById(R.id.navigation);
                        navigation.setVisibility(View.VISIBLE);
                        optionsMenu.findItem(R.id.default_name).setVisible(true);
                        optionsMenu.findItem(R.id.change_api_key).setVisible(true);
                        // Compile the last WXC from the populated player data
                        manager.CompileWXCStatsFromMatches();

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

    protected void UpdateActiveFragment() {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, activeFragment);
        transaction.commit();
    }

    protected void SaveVariable(String tag, String value) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString(tag, value).commit();
    }
}
