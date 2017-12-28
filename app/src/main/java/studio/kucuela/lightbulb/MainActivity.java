package studio.kucuela.lightbulb;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
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
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;




import java.util.Random;


import studio.kucuela.lightbulb.Settings.SettingsActivity;
import studio.kucuela.lightbulb.ShakeListeners.ShakeEventListener;
import studio.kucuela.lightbulb.ShakeListeners.ShakeEventListener2;
import studio.kucuela.lightbulb.ShakeListeners.ShakeEventListener3;




public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SensorEventListener {


    private SharedPreferences prefs;
    public static String NOTIF_AUTO = "notif_auto";
    public static String NOTIF_SOUND = "notif_sound";
    public static String NOTIF_STROBE = "notif_strobe";
    public static String NOTIF_SCREEN = "notif_screen";
    public static String NOTIF_KOMPAS = "notif_kompas";

    public static String NOTIF_SHAKE = "notif_shake";
    public static String NOTIF_TIPS = "notif_tips";
    public static String NOTIF_ON = "notif_on";
    public static String NOTIF_FULLSCREEN = "notif_fullscreen";
    public static String NOTIF_NOTIF = "notif_notif";


    final Handler handler = new Handler();
    final Handler handler2 = new Handler();


    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;
    private SensorManager mSensorManager2;
    private ShakeEventListener2 mSensorListener2;
    private SensorManager mSensorManager3;
    private ShakeEventListener3 mSensorListener3;

    // define the display assembly compass picture

    private ImageView imageK;


        // record the compass picture angle turned

    private float currentDegree = 0f;



        // device sensor manager

    private SensorManager kSensorManager;








    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.layout);
        imageK = (ImageView) findViewById(R.id.imageKompas);





        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
        boolean shake = prefs.getBoolean(NOTIF_SHAKE, false);
        boolean tips = prefs.getBoolean(NOTIF_TIPS, true);
        boolean on = prefs.getBoolean(NOTIF_ON, false);
        boolean kompas = prefs.getBoolean(NOTIF_KOMPAS, false);
        boolean fullscreen = prefs.getBoolean(NOTIF_FULLSCREEN, false);
        boolean notification = prefs.getBoolean(NOTIF_NOTIF, false);
        String END_POINT = prefs.getString("PREF_LIST", "1");
        String PREF_LIST_SHAKE_SENSITIVITY = prefs.getString("PREF_LIST_SHAKE_SENSITIVITY", "2");

        if (kompas){

            kSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        }

        if (PREF_LIST_SHAKE_SENSITIVITY.matches("2")){

            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensorListener = new ShakeEventListener();

            mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

                public void onShake() {

                    handleShakeEvent();

                }
            });

        }

        if (PREF_LIST_SHAKE_SENSITIVITY.matches("1")){

            mSensorManager2 = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensorListener2 = new ShakeEventListener2();

            mSensorListener2.setOnShakeListener(new ShakeEventListener2.OnShakeListener() {

                public void onShake() {

                    handleShakeEvent();

                }
            });

        }

        if (PREF_LIST_SHAKE_SENSITIVITY.matches("3")){

            mSensorManager3 = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensorListener3 = new ShakeEventListener3();

            mSensorListener3.setOnShakeListener(new ShakeEventListener3.OnShakeListener() {

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



            final String[] r1 = new String[] {"Tip: You can change app's light source","Tip: You can start the app with lights on","Tip: Use your screen as second flashlight",
            "Tip: Turn on blinking lights for signaling","Tip: Shake your phone for various actions","Tip: Change light switch sounds",
                    "Tip: Set your screen never to turn off","Tip: Display the app in fullscreen","Tip: Set ongoing notification and use it as shortcut",
                    "Tip: Turn on compass to find your way around"};
            final int randomMsgIndex = new Random().nextInt(r1.length);

            final Snackbar snackbar = Snackbar.make(cl,r1[randomMsgIndex], Snackbar.LENGTH_LONG);

            // Set an action on it, and a handler
            snackbar.setAction("TURN OFF HELP", new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                    prefs.putBoolean("notif_tips", false);
                    prefs.commit();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));

                }
            });




            snackbar.show();



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

            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));}
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);
        }

        if (auto && END_POINT.matches("2")) {

            upali();

            mesecoff.setVisibility(View.INVISIBLE);
            mesecon.setVisibility(View.VISIBLE);

            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));}
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }

        if (auto && END_POINT.matches("3")) {

            upali();

            sunoff.setVisibility(View.INVISIBLE);
            sunon.setVisibility(View.VISIBLE);

            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));}
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }

        if (auto && END_POINT.matches("3")&&strobe) {

            strobe();

            sunoff.setVisibility(View.INVISIBLE);
            sunon.setVisibility(View.VISIBLE);

            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));}
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(sunon);
        }

        if (auto && END_POINT.matches("2")&&strobe) {

            strobe();

            mesecoff.setVisibility(View.INVISIBLE);
            mesecon.setVisibility(View.VISIBLE);

            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));}
            //lLayout.setBackgroundResource(R.drawable.nocka);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecon);
        }

        if (auto && END_POINT.matches("1")&&strobe) {

            strobe();

            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.VISIBLE);

            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));}
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





            finishAffinity();



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
                    .setDescription("This is a simple material design app that uses phone flashlight to fight the darkness.Its absolutely ad free,beautifully designed and rich with options.There are 3 light source themes at the moment and i will try to add more in the future.Feel free to send me any kind of feedback,both positive and negative and tell me what features would you like to see in the future.")
                    .setHeaderDrawable(R.drawable.nocka).withDialogAnimation(true)
                    .setIcon(R.mipmap.logo)
                    .setPositiveText("OK").onPositive(new MaterialDialog.SingleButtonCallback() {

                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    dialog.dismiss();
                }
            })
                    .setNeutralText("OPEN SOURCE LIBRARIES").onNeutral(new MaterialDialog.SingleButtonCallback() {
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
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Download FlatLight app from https://app.box.com/v/flatlight");
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
        boolean notification = prefs.getBoolean(NOTIF_NOTIF,false);
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


// Add the following line to register the Session Manager Listener onResume

            if (shake&&PREF_LIST_SHAKE_SENSITIVITY.matches("2")) {
                mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
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




        if(notification) {



            String CHANNEL_ID = "my_channel_01";
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), i, 0);
            mBuilder.setContentTitle("Flatlight");

            mBuilder.setAutoCancel(false);
            mBuilder.setOngoing(true);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            mBuilder.setPriority(Notification.PRIORITY_MAX);

            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setSmallIcon(R.drawable.status);
            mBuilder.setChannelId(CHANNEL_ID).build();



            mBuilder.setContentText("Click to go back to app");



            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(2, mBuilder.build());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

      /* Create or update. */
                NotificationChannel channel = new NotificationChannel("my_channel_01",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_LOW);
                channel.setImportance(NotificationManager.IMPORTANCE_LOW);
                channel.enableVibration(false);
                channel.enableLights(false);

                channel.setSound(null,null);





                notificationManager.createNotificationChannel(channel);
            }



        }
        if (notification==false){

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(02);
        }








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
                camManager.setTorchMode(cameraId, false);   //Turn Off
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
            },200);



            handler2.postDelayed(new Runnable() {
                public void run() {
                    upali();
                    strobe();

                }
            }, 450); }



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

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));
            //lLayout.setBackgroundResource(R.drawable.nocka);
        }

        Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);

    }

    public void bulbon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);
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

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#262545"));
        }

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

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#262545"));
        }

        Technique.WAVE.getComposer().duration(650).delay(0).playOn(mesecoff);

    }

    public void mesecoff(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
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

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));
        }
        //lLayout.setBackgroundResource(R.drawable.pustinja);

        Technique.WAVE.getComposer().duration(650).delay(0).playOn(mesecon);

    }

    //SUNCE

    public void sunon(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
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

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#262545"));
        }

        Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunoff);

    }

    public void sunoff(View view) {

        //provera podesenja
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean auto = prefs.getBoolean(NOTIF_AUTO, false);
        boolean sound = prefs.getBoolean(NOTIF_SOUND, true);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
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

        if (screen==false) {
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));
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
        boolean strobe = prefs.getBoolean(NOTIF_STROBE, false);
        boolean shake = prefs.getBoolean(NOTIF_SHAKE, false);
        boolean screen = prefs.getBoolean(NOTIF_SCREEN, false);
        String END_POINT = prefs.getString("PREF_LIST", "1");
        String PREF_LIST_SHAKE = prefs.getString("PREF_LIST_SHAKE", "3");

        if (PREF_LIST_SHAKE.matches("1")&&strobe&&END_POINT.matches("1")) {

            if (bulboff.getVisibility() == View.VISIBLE) {

            strobe();
            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.VISIBLE);

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);

                if (screen==false){
                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                lLayout.setBackgroundColor(Color.parseColor("#443d71"));}

            }


        }

        if (PREF_LIST_SHAKE.matches("1")&&strobe&&END_POINT.matches("2")) {

            if (mesecoff.getVisibility() == View.VISIBLE) {


            strobe();
            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            mesecon.setVisibility(View.VISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);

            Technique.WAVE.getComposer().duration(650).delay(0).playOn(mesecon);

                if (screen==false){
                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                lLayout.setBackgroundColor(Color.parseColor("#443d71"));}}


        }

        if (PREF_LIST_SHAKE.matches("1")&&strobe&&END_POINT.matches("3")) {

            if (sunoff.getVisibility() == View.VISIBLE) {

            strobe();
            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.VISIBLE);
            sunoff.setVisibility(View.INVISIBLE);
            Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunon);
                if (screen==false){
                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                lLayout.setBackgroundColor(Color.parseColor("#443d71"));}}


        }

        if (PREF_LIST_SHAKE.matches("1")&&strobe==false&&END_POINT.matches("1")) {

            upali();
            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.VISIBLE);

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);

            Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulbon);

            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));}

        }

        if (PREF_LIST_SHAKE.matches("1")&&strobe==false&&END_POINT.matches("2")) {

            upali();
            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            mesecon.setVisibility(View.VISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.INVISIBLE);

            Technique.WAVE.getComposer().duration(650).delay(0).playOn(mesecon);
            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));}

        }

        if (PREF_LIST_SHAKE.matches("1")&&strobe==false&&END_POINT.matches("3")) {

            upali();
            bulboff.setVisibility(View.INVISIBLE);
            bulbon.setVisibility(View.INVISIBLE);

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);

            sunon.setVisibility(View.VISIBLE);
            sunoff.setVisibility(View.INVISIBLE);
            Technique.ROTATE.getComposer().duration(650).delay(0).playOn(sunon);
            if (screen==false){
            ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
            lLayout.setBackgroundColor(Color.parseColor("#443d71"));}

        }

        if (PREF_LIST_SHAKE.matches("2")) {

            if (bulbon.getVisibility() == View.VISIBLE) {

                ugasi();
                strobeoff();
                bulboff.setVisibility(View.VISIBLE);
                bulbon.setVisibility(View.INVISIBLE);

                mesecon.setVisibility(View.INVISIBLE);
                mesecoff.setVisibility(View.INVISIBLE);

                sunon.setVisibility(View.INVISIBLE);
                sunoff.setVisibility(View.INVISIBLE);

                Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(bulboff);
                if (screen==false){
                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                lLayout.setBackgroundColor(Color.parseColor("#262545"));}}

            if (mesecon.getVisibility() == View.VISIBLE) {

                ugasi();
                strobeoff();
                bulboff.setVisibility(View.INVISIBLE);
                bulbon.setVisibility(View.INVISIBLE);

                mesecon.setVisibility(View.INVISIBLE);
                mesecoff.setVisibility(View.VISIBLE);

                sunon.setVisibility(View.INVISIBLE);
                sunoff.setVisibility(View.INVISIBLE);

                Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(mesecoff);
                if (screen==false){
                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                lLayout.setBackgroundColor(Color.parseColor("#262545"));}}

            if (sunon.getVisibility() == View.VISIBLE) {

                ugasi();
                strobeoff();
                bulboff.setVisibility(View.INVISIBLE);
                bulbon.setVisibility(View.INVISIBLE);

                mesecon.setVisibility(View.INVISIBLE);
                mesecoff.setVisibility(View.INVISIBLE);

                sunon.setVisibility(View.INVISIBLE);
                sunoff.setVisibility(View.VISIBLE);

                Technique.BOUNCE.getComposer().duration(650).delay(0).playOn(sunoff);
                if (screen==false){
                ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.layout);
                lLayout.setBackgroundColor(Color.parseColor("#262545"));}}




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
        strobeoff();
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

   /* @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) MainActivity.this, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera permission requiered!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity)MainActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
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
                    ugasi();
                    strobeoff();
                } else {

                    finishAffinity();
                }
                break;
        }
    }*/







}











