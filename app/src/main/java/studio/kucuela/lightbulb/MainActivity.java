package studio.kucuela.lightbulb;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.jrummyapps.android.animations.Technique;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import java.util.Random;
import studio.kucuela.lightbulb.Settings.SettingsActivity;
import studio.kucuela.lightbulb.ShakeListeners.ShakeEventListenerNormal;
import studio.kucuela.lightbulb.ShakeListeners.ShakeEventListenerLow;
import studio.kucuela.lightbulb.ShakeListeners.ShakeEventListenerSensitive;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.CENTER_INSIDE;
import static android.widget.ImageView.ScaleType.FIT_CENTER;
import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SensorEventListener {

    private SharedPreferences prefs;
    public static String NOTIF_AUTO = "notif_auto";
    public static String NOTIF_SOUND = "notif_sound";
    public static String NOTIF_SCREEN = "notif_screen";
    public static String NOTIF_KOMPAS = "notif_kompas";
    public static String NOTIF_SHAKE = "notif_shake";
    public static String NOTIF_TIPS = "notif_tips";
    public static String NOTIF_ON = "notif_on";
    public static String NOTIF_FULLSCREEN = "notif_fullscreen";

    private SensorManager mSensorManager;
    private ShakeEventListenerNormal mSensorListener;
    private SensorManager mSensorManager2;
    private ShakeEventListenerLow mSensorListener2;
    private SensorManager mSensorManager3;
    private ShakeEventListenerSensitive mSensorListener3;
    private boolean flashState;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 123;
    private ImageView imageK;
    private float currentDegree = 0f;
    private SensorManager kSensorManager;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        toolbarNavbarStartMethod();

        ImageView bulbon = (ImageView) findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView) findViewById(R.id.bulbOFF);
        ImageView mesecon = (ImageView) findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView) findViewById(R.id.mesecoff);
        ImageView sunon = (ImageView) findViewById(R.id.sunon);
        ImageView sunoff = (ImageView) findViewById(R.id.sunoff);
        ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.layout);
        imageK = (ImageView) findViewById(R.id.imageKompas);



        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
        boolean shake = prefs.getBoolean(NOTIF_SHAKE, false);
        boolean tips = prefs.getBoolean(NOTIF_TIPS, true);
        boolean on = prefs.getBoolean(NOTIF_ON, false);
        boolean kompas = prefs.getBoolean(NOTIF_KOMPAS, false);
        boolean fullscreen = prefs.getBoolean(NOTIF_FULLSCREEN, false);
        //boolean notification = prefs.getBoolean(NOTIF_NOTIF, false);
        String END_POINT = prefs.getString("PREF_LIST", "1");
        String PREF_LIST_SHAKE_SENSITIVITY = prefs.getString("PREF_LIST_SHAKE_SENSITIVITY", "2");

        if (kompas){

            kSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        }

        if (PREF_LIST_SHAKE_SENSITIVITY.matches("2")){

            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensorListener = new ShakeEventListenerNormal();
            mSensorListener.setOnShakeListener(new ShakeEventListenerNormal.OnShakeListener() {

                public void onShake() {
                    handleShakeEvent();

                }
            });

        }

        if (PREF_LIST_SHAKE_SENSITIVITY.matches("1")){

            mSensorManager2 = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensorListener2 = new ShakeEventListenerLow();
            mSensorListener2.setOnShakeListener(new ShakeEventListenerLow.OnShakeListener() {

                public void onShake() {
                    handleShakeEvent();

                }
            });

        }

        if (PREF_LIST_SHAKE_SENSITIVITY.matches("3")){

            mSensorManager3 = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensorListener3 = new ShakeEventListenerSensitive();
            mSensorListener3.setOnShakeListener(new ShakeEventListenerSensitive.OnShakeListener() {

                public void onShake() {
                    handleShakeEvent();

                }
            });

        }


        if(on){

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        }

        if(fullscreen){

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

        if (tips){

            snackBar();
        }


        if (screen==true) {

            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = 100 / 100.0f;
            getWindow().setAttributes(lp);

            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }




        if (END_POINT.matches("1")) {

            bulboff.setVisibility(View.VISIBLE);
            bulbon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);
            mesecon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);
            sunon.setVisibility(View.INVISIBLE);


        }

        if (END_POINT.matches("2")) {


            mesecoff.setVisibility(View.VISIBLE);

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);
            mesecon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);
            sunon.setVisibility(View.INVISIBLE);


        }

        if (END_POINT.matches("3")) {


            sunoff.setVisibility(View.VISIBLE);
            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);
            mesecon.setVisibility(View.INVISIBLE);
                        sunon.setVisibility(View.INVISIBLE);





        }




        if (auto && END_POINT.matches("1")) {

            getPermission();

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.VISIBLE);

            if (screen==false){

                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#E0E0E0"));}
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);
        }

        if (auto && END_POINT.matches("2")) {

            getPermission();

            mesecoff.setVisibility(View.INVISIBLE);
            mesecon.setVisibility(View.VISIBLE);

            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#E0E0E0"));}
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }

        if (auto && END_POINT.matches("3")) {

            getPermission();

            sunoff.setVisibility(View.INVISIBLE);
            sunon.setVisibility(View.VISIBLE);

            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#E0E0E0"));}
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }


        //PERMISIJE ZA KAMERU






    }






    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            finishAffinity();

        }
    }

    @Override
    public void onDestroy() {

        ugasi();


        super.onDestroy();

        }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

          if (id == R.id.nav_send) {

            new MaterialStyledDialog.Builder(this)
                    .setDescription("• FlatLight is a simple material design app that uses phone flashlight to fight the darkness.Its " +
                            "absolutely ad free,beautifully designed and rich with options.There are 3 light source themes at the moment and i will " +
                            "try to add more in the future.Feel free to send me any kind of feedback,both positive and negative and tell me what " +
                            ("features would you like to see in the future.\n\n• Icons/art credits:\nwww.freepik.com\n\n• Used libraries:\n\n-com" +
                                    ".android.support:appcompat-v7:26.1.0\n\n-com.android.support:design:26.1.0\n\n-com.android.support" +
                                    ".constraint:constraint-layout:1.0.2\n\n-junit:junit:4.12\n\n-com.android.support.test:runner:1.0.1\n\n-com" +
                                    ".android.support" +
                                    ".test.espresso:espresso-core:3.0.1\n\n-com.jaredrummler:android-animations:1.0.0\n\n-com.github.javiersantos:" +
                                    "MaterialStyledDialogs:2.1\n\n-com.android.support:cardview-v7:26.1.0\n\n-com.android" +
                                    ".support:recyclerview-v7:26.1.0\n\n" +
                                    "-com.android.support:support-annotations:26.1.0"))
                    .setHeaderDrawable(R.drawable.mainx).withDialogAnimation(true)
                    .setScrollable(true)
                    .setScrollable(true, 16)

                    .setPositiveText("OK").onPositive(new MaterialDialog.SingleButtonCallback() {

                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    dialog.dismiss();
                }
            })
                    .setNeutralText("GITHUB").onNeutral(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    String url = "https://github.com/svetaz/FlatLight";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
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
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Checkout this beautifully designed app: https://app.box.com/v/flatlight");
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


        boolean shake = prefs.getBoolean(NOTIF_SHAKE, false);
        boolean kompas = prefs.getBoolean(NOTIF_KOMPAS, false);
        String PREF_LIST_SHAKE_SENSITIVITY = prefs.getString("PREF_LIST_SHAKE_SENSITIVITY", "2");


        if (bulboff.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(1000).delay(0).playOn(bulboff);
        } else if (bulbon.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(1000).delay(0).playOn(bulbon);
        } else if (mesecoff.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(1000).delay(0).playOn(mesecoff);
        } else if (mesecon.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(1000).delay(0).playOn(mesecon);
        } else if (sunoff.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(1000).delay(0).playOn(sunoff);
        } else if (sunon.getVisibility() == View.VISIBLE) {
            Technique.BOUNCE.getComposer().duration(1000).delay(0).playOn(sunon);
        }


            if (shake&&PREF_LIST_SHAKE_SENSITIVITY.matches("2")) {
                mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager
                        .SENSOR_DELAY_UI);
            }

        if (shake&&PREF_LIST_SHAKE_SENSITIVITY.matches("1")) {
            mSensorManager2.registerListener(mSensorListener2, mSensorManager2.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        }

        if (shake&&PREF_LIST_SHAKE_SENSITIVITY.matches("3")) {
            mSensorManager3.registerListener(mSensorListener3, mSensorManager3.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        }


        if (kompas) {

            imageK = (ImageView) findViewById(R.id.imageKompas);
            imageK.setVisibility(View.VISIBLE);
            kSensorManager.registerListener(this, kSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        }


    }


    //METODE ZA PALJENJE I GASENJE BLICA
    public void upali() {

        if (getBaseContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)==true) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                String cameraId = null; // Usually back camera is at 0 position.
                try {
                    cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode(cameraId, true);   //Turn ON
                    flashState = true;

                } catch (CameraAccessException e) {
                    e.printStackTrace();

                }


            }
        }

        if (getBaseContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)==false) {


            ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.layout);
            final Snackbar snackbar = Snackbar.make(cl, "Flashlight not found on phone.You can use screen as flashlight.", Snackbar
                    .LENGTH_INDEFINITE);
            snackbar.setAction("SETTINGS", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));

                }
            });
            snackbar.show();

        }




    }



    public void ugasi() {


        if (getBaseContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)==true) {


            PackageManager pm = getBaseContext().getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getBaseContext().getPackageName());

            if (hasPerm == PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    String cameraId = null; // Usually back camera is at 0 position.
                    try {
                        cameraId = camManager.getCameraIdList()[0];
                        camManager.setTorchMode(cameraId, false);   //Turn Off
                        flashState = false;

                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (hasPerm == PackageManager.PERMISSION_DENIED) {


                ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.layout);
                final Snackbar snackbar = Snackbar.make(cl, "Please grant camera permission to FlatLight", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("PERMISSIONS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);

                    }
                });
                snackbar.show();

            }
        }

        if (getBaseContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)==false) {


            ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.layout);
            final Snackbar snackbar = Snackbar.make(cl, "Flashlight not found on phone.You can use screen as flashlight.", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("SETTINGS", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));

                }
            });
            snackbar.show();

        }






    }

    //SIJALICA

    public void bulboff(View view) {


        getPermission();






        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);



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

        ImageView bulbon = (ImageView) findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView) findViewById(R.id.bulbOFF);
        bulboff.setVisibility(View.INVISIBLE);
        bulbon.setVisibility(View.VISIBLE);

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#E0E0E0"));
            //lLayout.setBackgroundResource(R.drawable.nocka);
        }

        Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);

    }

    public void bulbon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);

        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);

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


            ugasi();



        ImageView bulbon = (ImageView) findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView) findViewById(R.id.bulbOFF);
        bulboff.setVisibility(View.VISIBLE);
        bulbon.setVisibility(View.INVISIBLE);

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#BDBDBD"));
        }

        Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulboff);




    }

    //MESEC

    public void mesecon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        String END_SOUNDS = prefs.getString("PREF_LIST_SOUNDS", "2");

        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
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


            ugasi();



        ImageView mesecon = (ImageView) findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView) findViewById(R.id.mesecoff);
        mesecoff.setVisibility(View.VISIBLE);
        mesecon.setVisibility(View.INVISIBLE);

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#BDBDBD"));
        }

        Technique.ROTATE.getComposer().duration(650).delay(0).playOn(mesecoff);

    }

    public void mesecoff(View view) {

        getPermission();

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
        String END_SOUNDS = prefs.getString("PREF_LIST_SOUNDS", "2");
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









        ImageView mesecon = (ImageView) findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView) findViewById(R.id.mesecoff);

        mesecoff.setVisibility(View.INVISIBLE);
        mesecon.setVisibility(View.VISIBLE);

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }
        //lLayout.setBackgroundResource(R.drawable.pustinja);

        Technique.ROTATE.getComposer().duration(650).delay(0).playOn(mesecon);

    }

    //SUNCE

    public void sunon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
        String END_SOUNDS = prefs.getString("PREF_LIST_SOUNDS", "2");


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



            ugasi();



        ImageView sunon = (ImageView) findViewById(R.id.sunon);
        ImageView sunoff = (ImageView) findViewById(R.id.sunoff);
        sunoff.setVisibility(View.VISIBLE);
        sunon.setVisibility(View.INVISIBLE);

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#BDBDBD"));
        }

        Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunoff);

    }

    public void sunoff(View view) {

        getPermission();

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
        String END_SOUNDS = prefs.getString("PREF_LIST_SOUNDS", "2");
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

        ImageView sunon = (ImageView) findViewById(R.id.sunon);
        ImageView sunoff = (ImageView) findViewById(R.id.sunoff);

        sunoff.setVisibility(View.INVISIBLE);
        sunon.setVisibility(View.VISIBLE);

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }
        //lLayout.setBackgroundResource(R.drawable.nocka);



            Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunon);





    }



    @Override
    public void onPause() {


        // Add the following line to unregister the Sensor Manager onPause
        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean shake = prefs.getBoolean(NOTIF_SHAKE, false);
        boolean kompas = prefs.getBoolean(NOTIF_KOMPAS, false);
        String PREF_LIST_SHAKE_SENSITIVITY = prefs.getString("PREF_LIST_SHAKE_SENSITIVITY", "2");


        if (shake&&PREF_LIST_SHAKE_SENSITIVITY.matches("2")) {
            mSensorManager.unregisterListener(mSensorListener);
        }

        if (shake&&PREF_LIST_SHAKE_SENSITIVITY.matches("1")) {
            mSensorManager2.unregisterListener(mSensorListener2);
        }

        if (shake&&PREF_LIST_SHAKE_SENSITIVITY.matches("3")) {
            mSensorManager3.unregisterListener(mSensorListener3);
        }

        if (kompas){
        kSensorManager.unregisterListener(this);

        }


        super.onPause();
    }



    private void handleShakeEvent() {

        ImageView bulbon = (ImageView) findViewById(R.id.bulbON);
        ImageView bulboff = (ImageView) findViewById(R.id.bulbOFF);
        ImageView mesecon = (ImageView) findViewById(R.id.mesecon);
        ImageView mesecoff = (ImageView) findViewById(R.id.mesecoff);
        ImageView sunon = (ImageView) findViewById(R.id.sunon);
        ImageView sunoff = (ImageView) findViewById(R.id.sunoff);

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean shake = prefs.getBoolean(NOTIF_SHAKE, false);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
        String END_POINT = prefs.getString("PREF_LIST", "1");
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);

        String END_SOUNDS = prefs.getString("PREF_LIST_SOUNDS", "3");
        String PREF_LIST_SHAKE = prefs.getString("PREF_LIST_SHAKE", "3");



        if (PREF_LIST_SHAKE.matches("1")) {

            if (bulboff.getVisibility() == View.VISIBLE) {

                getPermission();

                bulboff.setVisibility(View.INVISIBLE);
                bulbon.setVisibility(View.VISIBLE);

                mesecon.setVisibility(View.INVISIBLE);
                mesecoff.setVisibility(View.INVISIBLE);

                sunon.setVisibility(View.INVISIBLE);
                sunoff.setVisibility(View.INVISIBLE);

                Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);
                if (screen==false){
                    ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                    lLayout.setBackgroundColor(Color.parseColor("#BDBDBD"));}


                if (sound&&END_SOUNDS.matches("1")) {

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





            }

            if (mesecoff.getVisibility() == View.VISIBLE) {

                getPermission();

                bulboff.setVisibility(View.INVISIBLE);
                bulbon.setVisibility(View.INVISIBLE);

                mesecon.setVisibility(View.VISIBLE);
                mesecoff.setVisibility(View.INVISIBLE);

                sunon.setVisibility(View.INVISIBLE);
                sunoff.setVisibility(View.INVISIBLE);

                Technique.ROTATE.getComposer().duration(650).delay(0).playOn(mesecon);

                if (screen==false){
                    ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                    lLayout.setBackgroundColor(Color.parseColor("#BDBDBD"));}


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






            }}

            if (sunoff.getVisibility() == View.VISIBLE) {

                getPermission();

                bulboff.setVisibility(View.INVISIBLE);
                bulbon.setVisibility(View.INVISIBLE);

                mesecon.setVisibility(View.INVISIBLE);
                mesecoff.setVisibility(View.INVISIBLE);

                sunon.setVisibility(View.VISIBLE);
                sunoff.setVisibility(View.INVISIBLE);

                Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunon);

                if (screen==false){
                    ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                    lLayout.setBackgroundColor(Color.parseColor("#BDBDBD"));}


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






                }}







        }







        if (PREF_LIST_SHAKE.matches("2")) {

            if (bulbon.getVisibility() == View.VISIBLE) {

                ugasi();

                bulboff.setVisibility(View.VISIBLE);
                bulbon.setVisibility(View.INVISIBLE);

                mesecon.setVisibility(View.INVISIBLE);
                mesecoff.setVisibility(View.INVISIBLE);

                sunon.setVisibility(View.INVISIBLE);
                sunoff.setVisibility(View.INVISIBLE);

                Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulboff);
                if (screen==false){
                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                lLayout.setBackgroundColor(Color.parseColor("#BDBDBD"));}

                if (sound&&END_SOUNDS.matches("1")) {

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


            }

            if (mesecon.getVisibility() == View.VISIBLE) {

                ugasi();

                bulboff.setVisibility(View.INVISIBLE);
                bulbon.setVisibility(View.INVISIBLE);

                mesecon.setVisibility(View.INVISIBLE);
                mesecoff.setVisibility(View.VISIBLE);

                sunon.setVisibility(View.INVISIBLE);
                sunoff.setVisibility(View.INVISIBLE);

                Technique.ROTATE.getComposer().duration(650).delay(0).playOn(mesecoff);
                if (screen==false){
                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                lLayout.setBackgroundColor(Color.parseColor("#BDBDBD"));}


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




            }

            if (sunon.getVisibility() == View.VISIBLE) {

                ugasi();

                bulboff.setVisibility(View.INVISIBLE);
                bulbon.setVisibility(View.INVISIBLE);

                mesecon.setVisibility(View.INVISIBLE);
                mesecoff.setVisibility(View.INVISIBLE);

                sunon.setVisibility(View.INVISIBLE);
                sunoff.setVisibility(View.VISIBLE);

                Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunoff);
                if (screen==false){
                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                lLayout.setBackgroundColor(Color.parseColor("#BDBDBD"));}


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
            }
        }


        if (PREF_LIST_SHAKE.matches("3")) {

           ubijsve();
        }

        if (PREF_LIST_SHAKE.matches("4")) {

            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }



    }

    public void ubijsve(){

        ugasi();
        finishAffinity();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        float degree = Math.round(event.values[0]);
        RotateAnimation ra = new RotateAnimation(

                currentDegree,
                        -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
        0.5f);
        // how long the animation will take place
                ra.setDuration(210);
        // set the animation after the end of the reservation status
                ra.setFillAfter(true);
                // Start the animation
               imageK.startAnimation(ra);
               currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void toolbarNavbarStartMethod (){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorDark3));
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void snackBar (){

        ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.layout);
        final String[] r1 = new String[] {"Tip: You can change app's light source","Tip: You can start the app with lights on","Tip: Use screen as second flashlight",
                "Tip: Shake phone for various actions","Tip: Change light switch sounds",
                "Tip: Set screen never to turn off","Tip: Go immersive with fullscreen mode",
                "Tip: Turn on compass to find your way around"};
        final int randomMsgIndex = new Random().nextInt(r1.length);
        final Snackbar snackbar = Snackbar.make(cl,r1[randomMsgIndex], Snackbar.LENGTH_LONG);
        // Set an action on it, and a handler
        snackbar.setAction("SETTINGS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, SettingsActivity.class));

            }
        });
        snackbar.show();



    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) MainActivity.this, Manifest.permission.CAMERA)) {

                            ActivityCompat.requestPermissions((Activity)MainActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);

                } else {
                    ActivityCompat.requestPermissions((Activity)MainActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_CALENDAR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                } else {

                    ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.layout);
                    final Snackbar snackbar = Snackbar.make(cl,"Please grant camera permission to FlatLight", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("PERMISSIONS", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                        }
                    });
                    snackbar.show();

                }
                break;
        }
    }

    public void getPermission (){

        PackageManager pm = getBaseContext().getPackageManager();

        int hasPerm = pm.checkPermission(
                Manifest.permission.CAMERA,
                getBaseContext().getPackageName());

        if (hasPerm == PackageManager.PERMISSION_GRANTED) {

            upali();

        } else {

            ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.layout);
            final Snackbar snackbar = Snackbar.make(cl,"Please grant camera permission to FlatLight", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("PERMISSIONS", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);

                }
            });
            snackbar.show();

        }

    }







}



















