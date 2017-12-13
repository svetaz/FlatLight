package studio.kucuela.lightbulb;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.view.SupportActionModeWrapper;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.jrummyapps.android.animations.Technique;

import java.security.Policy;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences prefs;
    public static String NOTIF_AUTO = "notif_auto";
    public static String NOTIF_SOUND = "notif_sound";




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
        ImageView mesecon = (ImageView)findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView)findViewById(R.id.mesecoff);
        ImageView sunon = (ImageView)findViewById(R.id.sunon);
        ImageView sunoff = (ImageView)findViewById(R.id.sunoff);





        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        String END_POINT = prefs.getString("PREF_LIST", "1");
        //if (END_POINT.matches("3")){

        if(END_POINT.matches("1")){

            bulboff.setVisibility(View.VISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);



        }

        if(END_POINT.matches("2")){

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.VISIBLE);

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);



        }

        if(END_POINT.matches("3")){

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.VISIBLE);

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);



        }





        if (auto&&END_POINT.matches("1")){

            upali();

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.VISIBLE);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#303030"));
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);
        }

        if (auto&&END_POINT.matches("2")){

            upali();

            mesecoff.setVisibility(View.INVISIBLE);
            mesecon.setVisibility(View.VISIBLE);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#303030"));
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }

        if (auto&&END_POINT.matches("3")){

            upali();

            sunoff.setVisibility(View.INVISIBLE);
            sunon.setVisibility(View.VISIBLE);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#303030"));
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }


    }




    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

            ugasi();

        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_send) {

            new MaterialStyledDialog.Builder(this)

                    .setDescription("This is a simple material design app that uses a flashlight.\n")
                    .setHeaderDrawable(R.drawable.noc)
                    .withDialogAnimation(true)
                    .setPositiveText("OK")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            dialog.dismiss();
                        }})

                    .setNegativeText("GITHUB")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            String url = "https://github.com/svetaz";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }})

                    .show();

        } else if (id == R.id.nav_donate) {

            String url = "https://paypal.me/svetaz";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }












    //ON RESUME
    @Override
    public void onResume () {

        super.onResume();

        ImageView bulbon = (ImageView)findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView)findViewById(R.id.bulbOFF);
        ImageView mesecon = (ImageView)findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView)findViewById(R.id.mesecoff);
        ImageView sunon = (ImageView)findViewById(R.id.sunon);
        ImageView sunoff = (ImageView)findViewById(R.id.sunoff);


        if (bulboff.getVisibility()==View.VISIBLE){
        Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulboff);}

        else if (bulbon.getVisibility()==View.VISIBLE){
            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);}

        else if (mesecoff.getVisibility()==View.VISIBLE){
            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecoff);}

        else if (mesecon.getVisibility()==View.VISIBLE){
            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);}

        else if (sunoff.getVisibility()==View.VISIBLE){
            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(sunoff);}

        else if (sunon.getVisibility()==View.VISIBLE){
            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(sunon);}




    }


    //METODE ZA PALJENJE I GASENJE BLICA
    public void upali (){

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


    }

    public void ugasi (){

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

    //SIJALICA

    public void bulboff(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);

        if (sound){

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
            mp.start();}

        upali();

        ImageView bulbon = (ImageView)findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView)findViewById(R.id.bulbOFF);

        bulboff.setVisibility(View.INVISIBLE);
        bulbon.setVisibility(View.VISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#303030"));
        //lLayout.setBackgroundResource(R.drawable.nocka);

        Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);

    }

    public void bulbon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);


        if (sound){

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
            mp.start(); }

        ugasi();

        ImageView bulbon = (ImageView)findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView)findViewById(R.id.bulbOFF);
        bulboff.setVisibility(View.VISIBLE);
        bulbon.setVisibility(View.INVISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#161616"));

        Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulboff);





    }

    //MESEC

    public void mesecon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);


        if (sound){

            MediaPlayer mp;
            mp = MediaPlayer.create(MainActivity.this, R.raw.moon);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.reset();
                    mp.release();
                    mp = null;
                }

            });
            mp.start(); }

        ugasi();

        ImageView mesecon = (ImageView)findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView)findViewById(R.id.mesecoff);
        mesecoff.setVisibility(View.VISIBLE);
        mesecon.setVisibility(View.INVISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#161616"));

        Technique.WAVE.getComposer().duration(650).delay(0).playOn(mesecoff);

    }

    public void mesecoff(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);

        if (sound){

            MediaPlayer mp;
            mp = MediaPlayer.create(MainActivity.this, R.raw.moon);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.reset();
                    mp.release();
                    mp = null;
                }

            });
            mp.start();}

        upali();

        ImageView mesecon = (ImageView)findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView)findViewById(R.id.mesecoff);

        mesecoff.setVisibility(View.INVISIBLE);
        mesecon.setVisibility(View.VISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#303030"));
        //lLayout.setBackgroundResource(R.drawable.nocka);

        Technique.WAVE.getComposer().duration(650).delay(0).playOn(mesecon);

    }

    //SUNCE

    public void sunon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);


        if (sound){

            MediaPlayer mp;
            mp = MediaPlayer.create(MainActivity.this, R.raw.sun);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.reset();
                    mp.release();
                    mp = null;
                }

            });
            mp.start(); }

        ugasi();

        ImageView sunon = (ImageView)findViewById(R.id.sunon);
        ImageView sunoff = (ImageView)findViewById(R.id.sunoff);
        sunoff.setVisibility(View.VISIBLE);
        sunon.setVisibility(View.INVISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#161616"));

        Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunoff);

    }

    public void sunoff(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);

        if (sound){

            MediaPlayer mp;
            mp = MediaPlayer.create(MainActivity.this, R.raw.sun);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.reset();
                    mp.release();
                    mp = null;
                }

            });
            mp.start();}

        upali();

        ImageView sunon = (ImageView)findViewById(R.id.sunon);
        ImageView sunoff = (ImageView)findViewById(R.id.sunoff);

        sunoff.setVisibility(View.INVISIBLE);
        sunon.setVisibility(View.VISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#303030"));
        //lLayout.setBackgroundResource(R.drawable.nocka);

        Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunon);

    }




}
