package tiimiss.globalgps.ba.tiimiss.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import tiimiss.globalgps.ba.tiimiss.Fragments.TaskDetailFragment;
import tiimiss.globalgps.ba.tiimiss.Fragments.TaskMapFragment;
import tiimiss.globalgps.ba.tiimiss.R;

public class DashboardActivity extends AppCompatActivity {

    AHBottomNavigation mBottomNavigation;
    FragmentManager fragmentManager = getSupportFragmentManager();

    TaskDetailFragment taskDetailFragment = TaskDetailFragment.newInstance();
    TaskMapFragment taskMapFragment = TaskMapFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mBottomNavigation = (AHBottomNavigation)findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null){
            loadContent(0);
        }
        setupBottomBavigation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void setupBottomBavigation() {

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tasks_type, R.drawable.ic_assignment_white_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.map_type, R.drawable.ic_map_white_24dp, R.color.colorPrimary);

        // Add items
        mBottomNavigation.addItem(item1);
        mBottomNavigation.addItem(item2);

        // Set background color
        mBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        // Disable the translation inside the CoordinatorLayout
        mBottomNavigation.setBehaviorTranslationEnabled(false);

        // Change colors
//        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
//        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        // Force to tint the drawable (useful for font with icon for example)
        mBottomNavigation.setForceTint(true);

        // Force the titles to be displayed (against Material Design guidelines!)
//        bottomNavigation.setForceTitlesDisplay(true);

        // Use colored navigation with circle reveal effect
        mBottomNavigation.setColored(true);

        // Set current item programmatically
        mBottomNavigation.setCurrentItem(0);

        // Customize notification (title, background, typeface)
        mBottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));


        // Set listener
        mBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if ( wasSelected == false ){
                    loadContent(position);
                    return false;
                }
                return true;
            }
        });
    }

    private void loadContent(int id) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (id){
            case 0:
                fragmentTransaction.replace(R.id.main_container, taskDetailFragment);
                break;
            case 1:
                //mapFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.main_container, taskMapFragment);
                break;
        }
        fragmentTransaction.commit();
    }
}
