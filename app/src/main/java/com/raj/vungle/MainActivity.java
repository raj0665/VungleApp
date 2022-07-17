package com.raj.vungle;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;
import static com.vungle.warren.Vungle.getValidPlacements;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.raj.vungle.databinding.ActivityMainBinding;
import com.vungle.warren.InitCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.VungleSettings;
import com.vungle.warren.error.VungleException;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSDK();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    private void initSDK() {


        final long MEGABYTE = 1024L * 1024L;
        final VungleSettings vungleSettings =
                new VungleSettings.Builder()
                        .setMinimumSpaceForAd(20 * MEGABYTE)
                        .setMinimumSpaceForInit(21 * MEGABYTE)
                        .setAndroidIdOptOut(false)
                        .build();


        Vungle.init("62cd0cae7c7dd2415825d350", getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {

                makeToast("Vungle SDK initialized");
                Log.d(LOG_TAG, "InitCallback - onSuccess");
                Log.d(LOG_TAG, "Valid placement list:");

                for (String validPlacementReferenceIdId : getValidPlacements()) {
                    Log.d(LOG_TAG, validPlacementReferenceIdId);

                }

            }

            @Override
            public void onError(VungleException vungleException) {
                if (vungleException != null) {
                    Log.d(LOG_TAG, "InitCallback - onError: " + vungleException.getLocalizedMessage());
                } else {
                    Log.d(LOG_TAG, "VungleException is null");
                }
            }

            @Override
            public void onAutoCacheAdAvailable(final String placementReferenceID) {
                Log.d(LOG_TAG, "InitCallback - onAutoCacheAdAvailable" +
                        "\n\tPlacement Reference ID = " + placementReferenceID);

            }
        }, vungleSettings);
    }

    private void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}