package studio.kucuela.lightbulb;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.view.SupportActionModeWrapper;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.jrummyapps.android.animations.Technique;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import android.app.Activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;




import java.io.File;
import java.security.Policy;









public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private SharedPreferences prefs;

    public static String NOTIF_AUTO = "notif_auto";
    public static String NOTIF_SOUND = "notif_sound";
    public static String NOTIF_STROBE = "notif_strobe";
    public static String NOTIF_SCREEN = "notif_screen";
    public static String NOTIF_SHAKE = "notif_shake";


    final Handler handler = new Handler();
    Handler handler2 = new Handler();

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;




    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


// ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                handleShakeEvent(count);
            }
        });






        ugasi();
        strobeoff();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView bulbon = (ImageView) findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView) findViewById(R.id.bulbOFF);
        ImageView mesecon = (ImageView) findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView) findViewById(R.id.mesecoff);
        ImageView sunon = (ImageView) findViewById(R.id.sunon);
        ImageView sunoff = (ImageView) findViewById(R.id.sunoff);
        TextView shakejpg = (TextView) findViewById(R.id.imageShake);
        TextView flashjpg = (TextView) findViewById(R.id.imageFlash);
        ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.layout);





        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
        boolean shake = prefs.getBoolean(NOTIF_SHAKE, false);

        String END_POINT = prefs.getString("PREF_LIST", "1");


        if (shake){

            shakejpg.setVisibility(View.VISIBLE);



        }

        if (strobe){

            flashjpg.setVisibility(View.VISIBLE);


        }
















        if (screen==true) {

            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = 100 / 100.0f;
            getWindow().setAttributes(lp);


        }

        if (END_POINT.matches("1")) {

            bulboff.setVisibility(View.VISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);



        }

        if (END_POINT.matches("2")) {

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.VISIBLE);

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);



        }

        if (END_POINT.matches("3")) {

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.VISIBLE);

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);



        }




        if (auto && END_POINT.matches("1")) {

            upali();

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.VISIBLE);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#FF473D40"));
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);
        }

        if (auto && END_POINT.matches("2")) {

            upali();

            mesecoff.setVisibility(View.INVISIBLE);
            mesecon.setVisibility(View.VISIBLE);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#FF473D40"));
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }

        if (auto && END_POINT.matches("3")) {

            upali();

            sunoff.setVisibility(View.INVISIBLE);
            sunon.setVisibility(View.VISIBLE);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#FF473D40"));
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }

        if (auto && END_POINT.matches("3")&&strobe) {

            strobe();

            sunoff.setVisibility(View.INVISIBLE);
            sunon.setVisibility(View.VISIBLE);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#FF473D40"));
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(sunon);
        }

        if (auto && END_POINT.matches("2")&&strobe) {

            strobe();

            mesecoff.setVisibility(View.INVISIBLE);
            mesecon.setVisibility(View.VISIBLE);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#FF473D40"));
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }

        if (auto && END_POINT.matches("1")&&strobe) {

            strobe();

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.VISIBLE);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#FF473D40"));
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);
        }
    }






    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            ugasi();
            strobeoff();


            super.onBackPressed();


        }
    }

    @Override
    public void onDestroy() {


            ugasi();
            strobeoff();

        super.onDestroy();

        }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

          if (id == R.id.nav_send) {

            new MaterialStyledDialog.Builder(this)
                    .setTitle("About")
                    .setDescription("• A simple material design app that uses phone flashlight\n• No ads\n• Tried to keep it as minimal as possible,without any fancy buttons or useless options\n• There are 3 light source themes at the moment and i will try to add more in the future\n• Feel free to send me any kind of feedback,both positive and negative and tell me what features would you like to see in this app\n")
                    .setHeaderDrawable(R.drawable.noc).withDialogAnimation(true)
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveText("OK").onPositive(new MaterialDialog.SingleButtonCallback() {

                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    dialog.dismiss();
                }
            })
                    .setNeutralText("Open source libraries").onNeutral(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            new LibsBuilder()
                            //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                            .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                            //start the activity
                            .start(MainActivity.this);
                }
            })


                    .show();

        } else if (id == R.id.nav_donate) {

            String url = "https://paypal.me/svetaz";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

     else if (id == R.id.nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Simple Flashlight app from https://app.box.com/v/minimalflashlight");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

    }

    else if (id==R.id.nav_feedback){

              Intent intent = new Intent(Intent.ACTION_VIEW);
              intent.setData(Uri.parse("mailto:" + "rollbarbullbar@gmail.com"));
              intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
              startActivity(Intent.createChooser(intent, "Send email"));
          }
          else if (id==R.id.nav_settings){

              startActivity(new Intent(MainActivity.this, SettingsActivity.class));

          }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    //ON RESUME
    @Override
    public void onResume() {

        super.onResume();

        ImageView bulbon = (ImageView) findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView) findViewById(R.id.bulbOFF);
        ImageView mesecon = (ImageView) findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView) findViewById(R.id.mesecoff);
        ImageView sunon = (ImageView) findViewById(R.id.sunon);
        ImageView sunoff = (ImageView) findViewById(R.id.sunoff);
        TextView shakejpg = (TextView) findViewById(R.id.imageShake);
        TextView flashjpg = (TextView) findViewById(R.id.imageFlash);
        boolean shake = prefs.getBoolean(NOTIF_SHAKE, false);


        if (bulboff.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(850).delay(0).playOn(bulboff);
        } else if (bulbon.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(850).delay(0).playOn(bulbon);
        } else if (mesecoff.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(850).delay(0).playOn(mesecoff);
        } else if (mesecon.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(850).delay(0).playOn(mesecon);
        } else if (sunoff.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(850).delay(0).playOn(sunoff);
        } else if (sunon.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(850).delay(0).playOn(sunon);
        }


// Add the following line to register the Session Manager Listener onResume


        if(shake){

            mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);


        }

        Technique.BOUNCE_IN_DOWN.getComposer().duration(850).delay(0).playOn(shakejpg);
        Technique.BOUNCE_IN_DOWN.getComposer().duration(850).delay(0).playOn(flashjpg);




    }


    //METODE ZA PALJENJE I GASENJE BLICA
    public void upali() {



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

    public void ugasi() {

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

    public void strobe () {

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String END_STROBE = prefs.getString("PREF_LIST_STROBE", "1");

        if (END_STROBE.matches("1")) {

        handler.postDelayed(new Runnable() {
            public void run() {
                ugasi();

            }
        },650);



        handler2.postDelayed(new Runnable() {
            public void run() {
                upali();
                strobe();

            }
        }, 1000); }

        if (END_STROBE.matches("2")) {

            handler.postDelayed(new Runnable() {
                public void run() {
                    ugasi();

                }
            },450);



            handler2.postDelayed(new Runnable() {
                public void run() {
                    upali();
                    strobe();

                }
            }, 750); }

        if (END_STROBE.matches("3")) {

            handler.postDelayed(new Runnable() {
                public void run() {
                    ugasi();

                }
            },350);



            handler2.postDelayed(new Runnable() {
                public void run() {
                    upali();
                    strobe();

                }
            }, 350); }



    }

    public void strobeoff (){

        handler.removeMessages(0);
        handler2.removeMessages(0);
    }



    //SIJALICA

    public void bulboff(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);

        if (sound) {

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
        }

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);


        if (strobe) {

            strobe();

        }
        if (strobe==false) {

            upali();

        }

        ImageView bulbon = (ImageView) findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView) findViewById(R.id.bulbOFF);

        bulboff.setVisibility(View.INVISIBLE);
        bulbon.setVisibility(View.VISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#FF473D40"));
        //lLayout.setBackgroundResource(R.drawable.nocka);

        Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);

    }

    public void bulbon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);


        if (sound) {

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
        }

        if (strobe) {

            strobeoff();
            ugasi();
            //Toast.makeText(this,"T",Toast.LENGTH_SHORT).show();
        }
        if (strobe==false) {

            strobeoff();
            ugasi();

        }

        ImageView bulbon = (ImageView) findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView) findViewById(R.id.bulbOFF);
        bulboff.setVisibility(View.VISIBLE);
        bulbon.setVisibility(View.INVISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#332b2d"));

        Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulboff);




    }

    //MESEC

    public void mesecon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        String END_SOUNDS = prefs.getString("PREF_LIST_SOUNDS", "1");
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);
        //if (END_SOUNDS.matches("3")){


        if (sound&&END_SOUNDS.matches("1")) {

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
            mp.start();
        }

        if (sound&&END_SOUNDS.matches("2")) {

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

        }

        if (strobe) {

            strobeoff();
            ugasi();
            //Toast.makeText(this,"T",Toast.LENGTH_SHORT).show();
        }
        if (strobe==false) {

            strobeoff();
            ugasi();

        }

        ImageView mesecon = (ImageView) findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView) findViewById(R.id.mesecoff);
        mesecoff.setVisibility(View.VISIBLE);
        mesecon.setVisibility(View.INVISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#332b2d"));

        Technique.WAVE.getComposer().duration(650).delay(0).playOn(mesecoff);

    }

    public void mesecoff(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        String END_SOUNDS = prefs.getString("PREF_LIST_SOUNDS", "1");
        //if (END_SOUNDS.matches("3")){


        if (sound&&END_SOUNDS.matches("1")) {

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
            mp.start();
        }

        if (sound&&END_SOUNDS.matches("2")) {

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

        }

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);


        if (strobe) {

            strobe();

        }
        if (strobe==false) {

            upali();

        }

        ImageView mesecon = (ImageView) findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView) findViewById(R.id.mesecoff);

        mesecoff.setVisibility(View.INVISIBLE);
        mesecon.setVisibility(View.VISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#FF473D40"));
        //lLayout.setBackgroundResource(R.drawable.pustinja);

        Technique.WAVE.getComposer().duration(650).delay(0).playOn(mesecon);

    }

    //SUNCE

    public void sunon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        String END_SOUNDS = prefs.getString("PREF_LIST_SOUNDS", "1");
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);

        //if (END_SOUNDS.matches("3")){


        if (sound&&END_SOUNDS.matches("1")) {

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
            mp.start();
        }

        if (sound&&END_SOUNDS.matches("2")) {

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

        }


        if (strobe) {

           strobeoff();
            ugasi();
            //Toast.makeText(this,"T",Toast.LENGTH_SHORT).show();
        }
        if (strobe==false) {

            strobeoff();
            ugasi();

        }

        ImageView sunon = (ImageView) findViewById(R.id.sunon);
        ImageView sunoff = (ImageView) findViewById(R.id.sunoff);
        sunoff.setVisibility(View.VISIBLE);
        sunon.setVisibility(View.INVISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#332b2d"));

        Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunoff);

    }

    public void sunoff(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        String END_SOUNDS = prefs.getString("PREF_LIST_SOUNDS", "1");
        //if (END_SOUNDS.matches("3")){


        if (sound&&END_SOUNDS.matches("1")) {

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
            mp.start();
        }

        if (sound&&END_SOUNDS.matches("2")) {

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

        }

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);



        if (strobe) {

            strobe();

        }
        if (strobe==false) {

            upali();

        }




        ImageView sunon = (ImageView) findViewById(R.id.sunon);
        ImageView sunoff = (ImageView) findViewById(R.id.sunoff);

        sunoff.setVisibility(View.INVISIBLE);
        sunon.setVisibility(View.VISIBLE);

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
        lLayout.setBackgroundColor(Color.parseColor("#FF473D40"));
        //lLayout.setBackgroundResource(R.drawable.nocka);



            Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunon);




    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
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
*/





    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    private void handleShakeEvent(int count) {

        ugasi();
        strobeoff();
        finish();

      //Toast.makeText(this,"SHAKED",Toast.LENGTH_SHORT).show();

    }



}

