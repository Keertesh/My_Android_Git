package kt.tech.com.lomaprototypeversion11;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    //----signup variable
    Intent i,j;
    ViewPager viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences session=getSharedPreferences("user",0);
        String username=session.getString("username","");
        int isActive=session.getInt("isActive",0);
        Log.d("status", username + isActive);
        if(isActive==1){
            Intent home=new Intent(getApplicationContext(),Home.class);
            home.putExtra("username",username);
            startActivity(home);
            finish();
        }

        //-----------signup
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new ImageAdapter(MainActivity.this);
        viewPager.setAdapter(adapter);


        //--------------
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
                            Toast.makeText(getApplicationContext(), "Turn On Internet", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .show();
        }

      //  readsp();

    }



    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    public void signup(View v)
    {

        i=new Intent(getApplicationContext(),Signup.class);
        startActivity(i);
        finish();
    }

    public void loghome(View v)
    {

        j=new Intent(getApplicationContext(),Login.class);
        startActivity(j);
        finish();
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

}
