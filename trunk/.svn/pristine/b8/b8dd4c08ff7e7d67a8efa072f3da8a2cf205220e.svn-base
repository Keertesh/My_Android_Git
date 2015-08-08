package kt.tech.com.lomaprototypeversion11;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

public class Splash extends Activity {

    ImageView img;
    String username;
    String password;
//    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);



    protected void onCreate(Bundle savedInstanceState) {


        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        long delay = 1;
        setContentView(R.layout.act_splash);


        img = (ImageView) findViewById(R.id.splashscreen);
        img.setVisibility(ImageView.VISIBLE);
        img.setBackgroundResource(R.anim.anim);
        AnimationDrawable ani = (AnimationDrawable) img.getBackground();
        ani.start();
        ani.setDither(true);



        if(!isNetworkAvailable()){
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing the App")
                    .setMessage("No Internet Connection,check your settings")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .show();
        }
        else
        {
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {

                    // TODO Auto-generated method stub
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
            }, delay * 1000);
            //   }
        }






        //--lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        //--lock screen for always Portrait mode



    }




    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }








}
