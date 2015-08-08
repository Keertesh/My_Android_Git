package kt.tech.com.lomaprototypeversion11;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OneTimePassword extends ActionBarActivity implements View.OnClickListener {

    EditText otptext;
    Button confirm_button;


    // Progress Dialog
  //  private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_create_otp = "http://"+JSONParser.ip+"/loma/public/user/otp";

    // JSON Node names
    private static final String TAG_MESSAGE= "message";

    private static final Integer TAG_STATUS = 0;


    int result=0;
    String rMessage;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.one_time_password);
        Intent usernm = getIntent();
        username = usernm.getStringExtra("username");
        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "one time pass", Toast.LENGTH_LONG).show();
        confirm_button = (Button) findViewById(R.id.confirmbtn);
        confirm_button.setOnClickListener(this);





      //  otptext=(EditText)findViewById(R.id.otp_editText);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_one_time_password, menu);
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

        Toast.makeText(getApplicationContext(), "OTP Button", Toast.LENGTH_SHORT).show();
        new Onetimepasswordrequest().execute();

    }



    class Onetimepasswordrequest extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // pDialog = new ProgressDialog(OneTimePassword.this);
            //pDialog.setMessage("otp check");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            String onetimepassword = otptext.getText().toString();
            String usernamee=username;

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("otp", onetimepassword));
            params.add(new BasicNameValuePair("username",usernamee));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_otp,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int status = json.getInt(String.valueOf(TAG_STATUS));
                String message = json.getString(TAG_MESSAGE);


                if (status == 1) {
                    // successfully created product
                    result = status;
                    rMessage = message;

                    Log.d("Success", "yes");

                    // closing this screen
                    finish();
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
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done

            //pDialog.dismiss();
            if (result == 1) {

                Toast.makeText(getApplicationContext(), rMessage, Toast.LENGTH_SHORT).show();
                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), Home.class);
                startActivity(intent_name);

            }

        }
    }
    }


