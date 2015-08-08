package kt.tech.com.lomaprototypeversion11;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

public class Signup extends Activity implements View.OnClickListener {

    Intent otp;
    EditText editname,editpassword,editemail,editmobile;
    Button signup;

    // Progress Dialog
  //  private ProgressDialog pDialog;


    JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_sign_up = "http://"+JSONParser.ip+"/loma/public/user/register";

    // JSON Node names
    private static final String TAG_MESSAGE= "message";

    private static final String TAG_STATUS = "status";
    int result=0;
    String rMessage;
    String username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        editname=(EditText) findViewById(R.id.name);
        editpassword=(EditText) findViewById(R.id.pw);
        editemail=(EditText) findViewById(R.id.email);
        editmobile=(EditText) findViewById(R.id.mob);
        signup= (Button)findViewById(R.id.signupbtn);
        signup.setOnClickListener(this);

        Toast.makeText(getApplicationContext(), "Welcome to Signup", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }


    @Override
    public void onClick(View v) {

        Toast.makeText(getApplicationContext(), "Sign up Button", Toast.LENGTH_SHORT).show();

        String mobilenumber=editmobile.getText().toString();

        String passwordvalue= editpassword.getText().toString();

        String mailtext=editemail.getText().toString();

        int mailsize=mailtext.length();
        int pwsize=passwordvalue.length();
        int mobsize=(mobilenumber.length());

        if(editname.getText().toString().equals("") || passwordvalue.equals("") || mailtext.equals("")|| mobilenumber.equals(""))
        {
            Toast.makeText(getApplicationContext(), "All Values are Mandatory",Toast.LENGTH_LONG).show();
        }
        else
        {
            if(pwsize<6)
            {
                editpassword.setError("Password must be atleast six digits");
            }
            else if(mailsize<8)
            {
                editemail.setError("please check your email id");
            }
            else if(mobsize==10)
            {
                otp=new Intent(getApplicationContext(),OneTimePassword.class);
                startActivity(otp);

                Toast.makeText(getApplicationContext(), "Sign up Sucessfully",Toast.LENGTH_LONG).show();
                finish();
            }


            else
            {
                Toast.makeText(getApplicationContext(), "Check your Mobile No.", Toast.LENGTH_LONG).show();
            }
        }

        new SignupJsonRequest().execute();

    }



    class SignupJsonRequest extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // pDialog = new ProgressDialog(Signup.this);
            //pDialog.setMessage("Logging in..");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {


            String name =editname.getText().toString();
            String password =editpassword.getText().toString();
            String email=editemail.getText().toString();
            String username =editmobile.getText().toString();
            Signup.this.username= username;




            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",username));

            params.add(new BasicNameValuePair("password",password));

            params.add(new BasicNameValuePair("name",name));

            params.add(new BasicNameValuePair("email",email));



            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_sign_up,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {

                int status =json.getInt(TAG_STATUS);
                String message = json.getString(TAG_MESSAGE);

                if (status == 1) {
                    // successfully created product
                    result= status;
                    rMessage=message;
                    Log.d("Success","yes");
                  //  Toast.makeText(getApplicationContext(), TAG_MESSAGE, Toast.LENGTH_LONG).show();

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
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done

           // pDialog.dismiss();
            if(result==1)
            {

                Toast.makeText(getApplicationContext(), rMessage, Toast.LENGTH_SHORT).show();
                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), OneTimePassword.class);
                intent_name.putExtra("username", username);
                startActivity(intent_name);
            }

        }

    }
}