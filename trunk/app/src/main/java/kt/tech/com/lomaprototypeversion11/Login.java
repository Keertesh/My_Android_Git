package kt.tech.com.lomaprototypeversion11;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Login extends ActionBarActivity implements View.OnClickListener,Serializable {

    Intent homepage;
    Home passobject;
    EditText mobilenm,passwordtxt;
    ViewPager viewPager;
    Button loginn;
    ImageView clickImage;

    int result = 0;
    String rMessage;
    // Progress Dialog
    // private ProgressDialog pDialog;

    String token;


   public JSONParser jsonParser = new JSONParser();



   Context context;
    String username;
    String password;
    String id;
    String lastlogin;

//----------------------------------------------------------------------------
    //public static android.content.SharedPreferences SharedPreferences = null;

    private static final String PREFER_NAME = "Reg";
    // User Session Manager Class
    //UserSession session;

    private SharedPreferences sharedPreferences;










//-----------------------------------------------------------------------------

    // url to create new product
    private static String url_login = "http://"+JSONParser.ip+"/loma/public/user/login";

    // JSON Node names
    private static final String TAG_SUCCESS = "status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);




        mobilenm  =(EditText)findViewById(R.id.mobile_number);
        passwordtxt=(EditText)findViewById(R.id.password);
        loginn= (Button) findViewById(R.id.login_button);
        loginn.setOnClickListener(this);




        //--------Shared Preferences
        //session = new UserSession(getApplicationContext());



        //--------Shared Preferences

        //--lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        //--lock screen for always Portrait mode


        //---------Check Internet Connection
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




    }

    //---------Check Internet Connection

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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




    @Override
    public void onClick(View v) {

        // Get username, password from EditText
        String abc = mobilenm.getText().toString();
        String password =passwordtxt.getText().toString();
        Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
       // username = Integer.parseInt(abc);
      //  Loadsp(username,passwordtxt);

            // creating new product in background thread
            new LoginJsonRequest().execute();    //[use this line for communicate with server]

       // Intent intentt = new Intent(Login.this, Home.class);
      //  startActivity(intentt);
       // finish();
        }


      //

public void Loadsp(String username,String password)
{

    SharedPreferences session= getSharedPreferences("user", 0);
    SharedPreferences.Editor ed=session.edit();
    ed.putInt("isActive", 1);
    ed.putString("username", username);
    ed.putString("token",token);
    ed.putString("password", password);
    ed.commit();
    Log.d("saved",1+"");


}



    class LoginJsonRequest extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  pDialog = new ProgressDialog(Login.this);
           // pDialog.setMessage("Logging in..");
           // pDialog.setIndeterminate(false);
           // pDialog.setCancelable(true);
           // pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String phone =  mobilenm.getText().toString();
            String passwordd =  passwordtxt.getText().toString();

            String username = phone;

            String password = passwordd;

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",username));
            //params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password",password));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_login,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int status = json.getInt(TAG_SUCCESS);
                JSONObject user = json.getJSONObject("user");

                username = user.getString("username");
                id = user.getString("id");
                token = user.getString("token");
                lastlogin = user.getString("last_login");


                UserDetails ob = UserDetails.userdata;
                ob.setUsername(username);
                ob.setId(Double.parseDouble(id));
                ob.setToken(token);
                ob.setLastlogin(lastlogin);
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

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done

           // pDialog.dismiss();
            if (result == 1) {
                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), Home.class);
                startActivity(intent_name);
                /*
                SharedPreferences sp = getSharedPreferences("MYKEY",0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username" , username);
                editor.putString("password" , password);
*/


                Loadsp(username,password);
                finish();
            }
            else
            {
                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent_name);
                finish();
            }

        }

        }

}
