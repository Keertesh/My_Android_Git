package kt.tech.com.lomaprototypeversion11;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationList extends ActionBarActivity {
    Toolbar mToolbar;
    private AutoCompleteTextView autoComplete;
    Button autoLocate;

    private ArrayAdapter<String> adapter;

    String selectedcity;
    public JSONParser jsonParsernew = new JSONParser();


    int result = 0;

    String pageTitle = null;
    String longitude;
    String latitude;


    public JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String location_details_link = "http://" + JSONParser.ip + "/loma/public/user/location";

    private static final String TAG_MESSAGE = "message";
    private static final String TAG_STATUS = "status";
    private Boolean flag = false;
    private LocationManager locationMangaernew = null;
    private LocationListener locationListenernew = null;
    private static final String TAG = "Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_search_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        locationMangaernew = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        autoLocate = (Button) findViewById(R.id.Auto_locate);


        autoLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolBarLocation();
            }
        });
        //--lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        //--lock screen for always Portrait mode

        // get the defined string-array
        String[] cityname = getResources().getStringArray(R.array.cityList);
        //------clear location

        //----clear location

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityname);

        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);

        autoComplete.setAdapter(adapter);

        autoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //   String locationname= autoComplete.getText().toString();

        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedcity = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Selected City->" + selectedcity, Toast.LENGTH_SHORT).show();
                Intent home = new Intent(getApplicationContext(), Home.class);
                home.putExtra("cityname", selectedcity);

                SharedPreferences settings = getSharedPreferences("first_run", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("title", selectedcity);
                editor.commit();

                if (selectedcity.equals("Bhilai")) {
                    latitude = "21.214599";
                    longitude = "81.317547";
                    home.putExtra("latitude ", latitude);
                    home.putExtra("longitude", longitude);
                    new SendLocationCityDetails().execute();
                    startActivity(home);
                    finish();


                }
                if (selectedcity.equals("Durg")) {
                    latitude = "21.171579";
                    longitude = "81.293843";
                    home.putExtra("latitude ", latitude);
                    home.putExtra("longitude", longitude);
                    new SendLocationCityDetails().execute();
                    startActivity(home);
                    finish();
                }

                if (selectedcity.equals("Raipur")) {
                    latitude = "21.251187";
                    longitude = "81.632635";
                    home.putExtra("latitude ", latitude);
                    home.putExtra("longitude", longitude);
                    new SendLocationCityDetails().execute();
                    startActivity(home);
                    finish();
                }

            }

            class SendLocationCityDetails extends AsyncTask<String, String, String> {

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
                    String log = longitude;
                    String lat = latitude;
                    String city = selectedcity;
                    UserDetails ud = UserDetails.userdata;
                    String token = ud.getToken();
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("longitude", longitude));
                    params.add(new BasicNameValuePair("latitude", latitude));
                    params.add(new BasicNameValuePair("city", city));
                    params.add(new BasicNameValuePair("token", token));

                    // getting JSON Object
                    // Note that create product url accepts POST method
                    JSONObject jsonnew = jsonParsernew.makeHttpRequest(location_details_link,
                            "POST", params);

                    // check log cat fro response
                    Log.d("Create Response Manual", jsonnew.toString());

                    try {
                        int status = jsonnew.getInt(TAG_SUCCESS);
                        result = status;

                        if (status == 1) {
                            // successfully created product


                            Log.d("Successnew", "yes");

                            // closing this screen

                        } else {

                            // failed to create product
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                protected void onPostExecute(String file_url) {
                    Toast.makeText(getApplicationContext(), "Manual Location Submitted", Toast.LENGTH_SHORT).show();
                }
            }


        });
        autoComplete.setThreshold(1);


    }

    public void ToolBarLocation() {

        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");

            // editLocation.setText("Please!! move your device to"+
            //         " see the changes in coordinates."+"\nWait..");

            //  pb.setVisibility(View.VISIBLE);

            locationListenernew = new MyLocationListener();

            locationMangaernew.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 50000000, 1, locationListenernew);


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

            longitude = "Longitude: " + loc.getLongitude();
            Log.v(TAG, longitude);
            latitude = "Latitude: " + loc.getLatitude();
            Log.v(TAG, latitude);

    /*----------to get City-Name from coordinates ------------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }

            pageTitle = cityName;
            mToolbar.setTitle(pageTitle);
           // new SendLocationCityDetails().execute();
            SharedPreferences settings = getSharedPreferences("first_run", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("title", pageTitle);
            editor.commit();
            Toast.makeText(getApplicationContext(), "Auto Locate->"+ pageTitle, Toast.LENGTH_SHORT).show();
            locationMangaernew.removeUpdates(locationListenernew);
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), Home.class);
            startActivity(intent);


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        public class SendLocationCityDetails extends AsyncTask<String, String, String> {
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
                String city = pageTitle;
                //  UserDetails ud = new UserDetails();
                //String token = ud.getToken();
                UserDetails ud = UserDetails.userdata;
                String token = ud.getToken();
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("longitude", longitude));
                params.add(new BasicNameValuePair("latitude", latitude));
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


                        Log.d("Success", "yes");

                        // closing this screen

                    } else {

                        // failed to create product
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            protected void onPostExecute(String file_url) {
                Toast.makeText(getApplicationContext(), "Location Submitted", Toast.LENGTH_SHORT).show();
            }
        }


    }
}