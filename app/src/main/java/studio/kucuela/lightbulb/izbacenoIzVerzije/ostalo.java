package studio.kucuela.lightbulb.izbacenoIzVerzije;


/**
 * Created by Filip 3 on 31-Dec-2017.
 */

public @interface ostalo

    //IZBACENO IZ VERZIJE:

    //NOTIFIKACIJE

     /*if(notification) {



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

      *//* Create or update. *//*
                NotificationChannel channel = new NotificationChannel("my_channel_01",
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_LOW);
                channel.setImportance(NotificationManager.IMPORTANCE_LOW);
                channel.enableVibration(false);
                channel.enableLights(false);
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                channel.setSound(null,null);





                notificationManager.createNotificationChannel(channel);
            }



        }
        if (notification==false){

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(02);
        }*/



/*
        if (flashState==false&&bulbon.getVisibility()==View.VISIBLE){

            bulbon.setVisibility(View.INVISIBLE);
            bulboff.setVisibility(View.VISIBLE);
            Technique.BOUNCE.getComposer().duration(1000).delay(0).playOn(bulboff);

        }

        if (flashState==false&&sunon.getVisibility()==View.VISIBLE){

            sunon.setVisibility(View.INVISIBLE);
            sunoff.setVisibility(View.VISIBLE);
            Technique.ROTATE.getComposer().duration(1000).delay(0).playOn(sunoff);

        }

        if (flashState==false&&mesecon.getVisibility()==View.VISIBLE){

            mesecon.setVisibility(View.INVISIBLE);
            mesecoff.setVisibility(View.VISIBLE);
            Technique.ROTATE.getComposer().duration(1000).delay(0).playOn(mesecoff);

        }

        if (flashState==true&&bulboff.getVisibility()==View.VISIBLE){

            bulbon.setVisibility(View.VISIBLE);
            bulboff.setVisibility(View.INVISIBLE);
            Technique.BOUNCE.getComposer().duration(1000).delay(0).playOn(bulbon);

        }

        if (flashState==true&&sunoff.getVisibility()==View.VISIBLE){

            sunon.setVisibility(View.VISIBLE);
            sunoff.setVisibility(View.INVISIBLE);
            Technique.ROTATE.getComposer().duration(1000).delay(0).playOn(sunon);

        }

        if (flashState==true&&mesecoff.getVisibility()==View.VISIBLE){

            mesecon.setVisibility(View.VISIBLE);
            mesecoff.setVisibility(View.INVISIBLE);
            Technique.ROTATE.getComposer().duration(1000).delay(0).playOn(mesecon);

        }*/


            //PERMISIJE

    /*@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
                    alertBuilder.setIcon(R.drawable.bulbon);
                    alertBuilder.setMessage("FlatLight app requires camera permission in order to turn your phone flashlight on and off.");
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

                    upali();

                } else {
                    ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.layout);
                    final Snackbar snackbar = Snackbar.make(cl,"Please grant camera permission to FlatLight app", Snackbar.LENGTH_LONG);

                    snackbar.show();

                }
                break;
        }
    }
*/{


}
