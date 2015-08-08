package kt.tech.com.lomaprototypeversion11;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Home extends ActionBarActivity
        implements NavigationDrawerCallbacks,ViewPager.OnPageChangeListener {

    private static final String PREFS_NAME ="first_run" ;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    SlidingTabStrip tabStrips;
    private ProgressBar pb =null;
    String citynames;
    CharSequence Titles[]={"Search","Category","Near By"};
    int Numboftabs =3;
    private static String url_create_signout = "http://"+JSONParser.ip+"/loma/public/user/disp";
    int result = 0;
    String rMessage;
    private LocationManager locationMangaer=null;
    private LocationListener locationListener=null;
    private static final String TAG = "Debug";
    private Boolean flag = false;
    Context context;
    String pageTitle= null;
    String longitude;
    String latitude;


    public JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String location_details_link = "http://"+JSONParser.ip+"/loma/public/user/location";

    private static final String TAG_MESSAGE = "message";
    private static final String TAG_STATUS = "status";
    SharedPreferences prefs;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        prefs = getSharedPreferences("kt.tech.com.lomaprototypeversion11", 0);
        mToolbar.setClickable(true);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loc = new Intent(Home.this, LocationList.class);
                startActivity(loc);
                finish();
            }
        });


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);


        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
      /*  tabStrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(null, "tabs", Toast.LENGTH_SHORT).show();
            }
        });
        */




        //------------------

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("LOMA", "Your Own Shopping Guide", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));


        //lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);






        locationMangaer = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);



        //---------Check Internet Connection
        if(!isNetworkAvailable()){
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing the App")
                    .setMessage("No Internet Connection,check your settings")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Turn On Internet", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .show();
        }





//-------------first run
            SharedPreferences settings = getSharedPreferences("first_run", MODE_PRIVATE);
            if (settings.getBoolean("isFirstRun", true)) {
                Toast.makeText(getApplicationContext(), "First Run", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isFirstRun", false);
                editor.commit();
                ToolBarLocation();
                editor.putString("title", pageTitle);
            }
            else
            { Toast.makeText(getApplicationContext(), "Not First Run", Toast.LENGTH_SHORT).show();

                SharedPreferences get=getSharedPreferences("first_run",0);
                pageTitle=get.getString("title","null");
                Log.d("store", pageTitle);

                mToolbar.setTitle(pageTitle);
                setSupportActionBar(mToolbar);


              //  home.putExtra("toolbarTitle",pageTitle);
                mToolbar.setClickable(true);
                mToolbar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent loc = new Intent(Home.this, LocationList.class);
                        startActivity(loc);
                        finish();
                    }
                });

                mNavigationDrawerFragment = (NavigationDrawerFragment)
                        getFragmentManager().findFragmentById(R.id.fragment_drawer);

                // Set up the drawer.
                mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
                // populate the navigation drawer
                mNavigationDrawerFragment.setUserData("LOMA", "Your Own Shopping Guide", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));



            }

//-------------first run

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.


        savedInstanceState.putString("title", pageTitle);
        // etc.
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        citynames = savedInstanceState.getString("title","");
    }





    //---------Check Internet Connection

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    //Double Tap Exit
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "Tap Again To Exit", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//firstTime
                    return true;
                } else {
                       finish();
                    System.exit(0);

                }
                break;
        }

        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
       // Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
        if (+position== 4)
        {
            signout();
        }

        switch(position) {
            case 3:
                startActivity(new Intent(this, kt.tech.com.lomaprototypeversion11.Settings.class));
        }
    }



    public void navigatetosettings()
    {
        Intent setingsintent = new Intent(Home.this,Settings.class);
        startActivity(setingsintent);
        finish();
    }

    public void signout()
    {
        SharedPreferences session=getSharedPreferences("user",0);
        SharedPreferences.Editor ed=session.edit();
        ed.clear();
        ed.commit();
        SharedPreferences settings = getSharedPreferences("first_run", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();


        Intent Main=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(Main);
        finish();

        //new SignoutJsonRequest().execute();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class SignoutJsonRequest extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected String doInBackground(String... args) {
            JSONParser jsonParsersignout = new JSONParser();
            String abcd = "abcd";

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // params.add(new BasicNameValuePair("abcd", abcd));
            JSONObject json1 = jsonParsersignout.makeHttpRequest(url_create_signout,
                    "GET", params);

          Log.d("Create Response signout", json1.toString());
            try {

                int status = json1.getInt((TAG_STATUS));
             //  String message = json1.getString(TAG_MESSAGE);
                result = status;
            //    rMessage = message;
                    Log.d("Status",status+"");
                if (status == 1) {
                    // successfully created product
                    result = status;
                 //   rMessage = message;
                    Log.d("Signout Successfully", "yes");


                }
                else
                {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            if (result == 1) {

               // Intent intent_name = new Intent();
              //  intent_name.setClass(getApplicationContext(), MainActivity.class);
               // startActivity(intent_name);
               // finish();
            }
            else
            {

            }


        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_activity2, menu);
            return true;
        }
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
        if (id == R.id.action_search) {
            opensearch();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void opensearch() {

   Intent listsearch = new Intent(getApplicationContext(),Search.class);
        startActivity(listsearch);
    }

/*
    @Override
    public void onClick(View v) {
        Intent locations = new Intent(Home.this,LocationList.class);
        startActivity(locations);

    }
*/

    @Override
    protected void onResume() {
        super.onResume();



    }



    public void ToolBarLocation()
    {

        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");

            // editLocation.setText("Please!! move your device to"+
            //         " see the changes in coordinates."+"\nWait..");

          //  pb.setVisibility(View.VISIBLE);

            locationListener = new MyLocationListener();

            locationMangaer.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 500000000, 1, locationListener);


        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }
    }
    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("Gps Status")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Select City",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent_name = new Intent();
                                intent_name.setClass(getApplicationContext(), LocationList.class);
                                startActivity(intent_name);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }



    /*----------Listener class to get coordinates ------------- */
    public class MyLocationListener implements LocationListener {



        @Override
        public void onLocationChanged(Location loc) {

            // editLocation.setText("");
          //  pb.setVisibility(View.INVISIBLE);
            //   Toast.makeText(getBaseContext(),"Location changed : Lat: " +
            //                 loc.getLatitude()+ " Lng: " + loc.getLongitude(),
            //       Toast.LENGTH_SHORT).show();

             longitude = "Longitude: " +loc.getLongitude();
            Log.v(TAG, longitude);
             latitude = "Latitude: " +loc.getLatitude();
            Log.v(TAG, latitude);

    /*----------to get City-Name from coordinates ------------- */
            String cityName=null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName=addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }

            pageTitle =cityName;
            mToolbar.setTitle(pageTitle);
            new SendLocationCityDetails().execute();
            SharedPreferences settings = getSharedPreferences("first_run", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("title",pageTitle);
            editor.commit();
            locationMangaer.removeUpdates(locationListener);




        }
        public class SendLocationCityDetails extends AsyncTask<String, String, String>
        {
            private static final String TAG_SUCCESS = "status";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //  pDialog = new ProgressDialog(Login.this);
                // pDialog.setMessage("Logging in..");
                // pDialog.setIndeterminate(false);
                // pDialog.setCancelable(true);
                // pDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                //String log =longitude;
               // String lat =latitude;
                String city =pageTitle;
              //  UserDetails ud = new UserDetails();
                //String token = ud.getToken();
                UserDetails ud = UserDetails.userdata;
                String token = ud.getToken();
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("longitude",longitude));
                params.add(new BasicNameValuePair("latitude",latitude));
                params.add(new BasicNameValuePair("city", city));
                params.add(new BasicNameValuePair("token", token));


                // getting JSON Object
                // Note that create product url accepts POST method
                JSONObject json = jsonParser.makeHttpRequest(location_details_link,
                        "POST", params);

                // check log cat fro response
                Log.d("Create Response", json.toString());

                try {
                    int status = json.getInt(TAG_SUCCESS);
                    result = status;

                    if (status == 1) {
                        // successfully created product



                        Log.d("Success","yes");

                        // closing this screen

                    } else {

                        // failed to create product
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
            protected void onPostExecute(String file_url)
            {
                Toast.makeText(getApplicationContext(), "Location Submitted", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }

}
