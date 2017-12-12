package studio.kucuela.lightbulb;


import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.jrummyapps.android.animations.Technique;

import java.security.Policy;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView bulbon = (ImageView)findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView)findViewById(R.id.bulbOFF);

        //Technique.WOBBLE.getComposer().duration(3000).delay(0).playOn(bulboff);



    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                String cameraId = null; // Usually back camera is at 0 position.
                try {
                    cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode(cameraId, false);   //Turn ON
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /*@Override
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
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            // Handle the camera action
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void bulboff(View view) {

        MediaPlayer mp;
        mp = MediaPlayer.create(MainActivity.this, R.raw.clicky);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.reset();
                mp.release();
                mp = null;
            }

        });
        mp.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String cameraId = null; // Usually back camera is at 0 position.
            try {
                cameraId = camManager.getCameraIdList()[0];
                camManager.setTorchMode(cameraId, true);   //Turn ON
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        ImageView bulbon = (ImageView)findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView)findViewById(R.id.bulbOFF);

        bulboff.setVisibility(View.INVISIBLE);
        bulbon.setVisibility(View.VISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#303030"));
        //lLayout.setBackgroundResource(R.drawable.nocka);

        Technique.BOUNCE.getComposer().duration(500).delay(0).playOn(bulbon);




    }

    public void bulbon(View view) {

        MediaPlayer mp;
        mp = MediaPlayer.create(MainActivity.this, R.raw.clicky);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.reset();
                mp.release();
                mp = null;
            }

        });
        mp.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String cameraId = null; // Usually back camera is at 0 position.
            try {
                cameraId = camManager.getCameraIdList()[0];
                camManager.setTorchMode(cameraId, false);   //Turn ON
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        ImageView bulbon = (ImageView)findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView)findViewById(R.id.bulbOFF);
        bulboff.setVisibility(View.VISIBLE);
        bulbon.setVisibility(View.INVISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#161616"));



        Technique.BOUNCE.getComposer().duration(500).delay(0).playOn(bulboff);



    }

    @Override
    public void onResume () {

        super.onResume();

        ImageView bulbon = (ImageView)findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView)findViewById(R.id.bulbOFF);

        if (bulboff.getVisibility()==View.VISIBLE){
        Technique.BOUNCE.getComposer().duration(500).delay(0).playOn(bulboff);}

        else if (bulbon.getVisibility()==View.VISIBLE){
            Technique.BOUNCE.getComposer().duration(500).delay(0).playOn(bulbon);}


    }

}
