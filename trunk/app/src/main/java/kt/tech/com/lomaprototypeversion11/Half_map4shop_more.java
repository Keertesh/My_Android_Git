package kt.tech.com.lomaprototypeversion11;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Half_map4shop_more extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    JSONParser jsonParser = JSONParser.jsonconn;

    private String url_companydetail = "http://" + JSONParser.ip + "/loma/public/user/companydetails";

    private final String TAG_STATUS = "status";
    TextView shopname,shopaddress,companyrate,companyreview,companycontact,companyemail,companytime,companyholiday,companyfinance,companycategory;
    Button mycall, mydirection, myreview, myfullreview;

    int rvcat_name,cat_id;
    int rvprice, rvaftersale,rvid,rvuser_id;
    String username,rvtitle,rvdescript ;
    String companyname,companyaddress,rate,review,contact,email,time,holiday,finance;
    int  companyid;
    double logni;
    double lati;
    String createat ;
    int brandid ;
    String cat_name;
    String descript;
    ProductCompanyList company;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_more);
        company= (ProductCompanyList) getIntent().getSerializableExtra("company");
        latitude=company.getLat();
        longitude=company.getLongi();
        shopname= (TextView) findViewById(R.id.companydetailname);
        shopaddress=(TextView) findViewById(R.id.companydetailaddress);
        companyrate= (TextView) findViewById(R.id.companydetailsrate);
        companyreview= (TextView) findViewById(R.id.companydetailreview);
        companycontact= (TextView) findViewById(R.id.companydetailcontact);
        companytime= (TextView) findViewById(R.id.companydetailtiming);
        companyemail= (TextView) findViewById(R.id.companydetailemail);
        companyholiday= (TextView) findViewById(R.id.companydetailclosed);
        companyfinance= (TextView) findViewById(R.id.companydetailfinance);
        companycategory= (TextView) findViewById(R.id.companydetailcategory);
       // description=(TextView) findViewById(R.id.address);
        mycall = (Button) findViewById(R.id.companydetailcall);
        //mychat = (Button) findViewById(R.id.chating);
        mydirection = (Button) findViewById(R.id.companydetailmap);
        myreview = (Button) findViewById(R.id.rvbtn);
        myfullreview = (Button) findViewById(R.id.fulrv);
       // Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_SHORT).show();
        new CreateNewProduct().execute();
        mycall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                // TODO Auto-generated method stub
                Intent shopcall = new Intent(Intent.ACTION_DIAL);
                shopcall.setData(Uri.parse("tel:"+company.getMobile()));
                startActivity(shopcall);
            }
        });
        mydirection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                // TODO Auto-generated method stub
                Intent shopdetail = new Intent(getApplicationContext(), Maps4shop_more.class);
                startActivity(shopdetail);
            }
        });
        setUpMapIfNeeded();

      }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
            if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {

        LatLng latlong=new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(latlong).title(company.getCompanyName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong,15));

    }

    class CreateNewProduct extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            String companyId=company.getCompanyId()+"";

            Log.d("Create Response", companyId);

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();

            params1.add(new BasicNameValuePair("company_id", companyId));

            // getting JSON Object

            JSONObject jsonProducts = jsonParser.makeHttpRequest(url_companydetail,
                    "POST", params1);

            Log.d("JSON Response", jsonProducts.toString());

            try {

                int status = jsonProducts.getInt(TAG_STATUS);
                if (status == 1)
                {
                    JSONObject company = jsonProducts.getJSONObject("Company_details");
                    email=company.getString("email_id");
                    time=company.getString("opening_time")+" - "+company.getString("closing_time");
                    holiday=company.getString("closed_on");
                    finance=company.getInt("finance")+"";
                    if(finance.equals("1")){
                        finance="YES";
                    }
                    else
                        finance="No";
                    //JSONArray category = jsonProducts.getJSONArray("category");
                    //JSONArray review = jsonProducts.getJSONArray("reviews");
                    //companyname = company.getString("company_name");//addressline
                    //companyaddress = company.getString("addressline");
                    //companyid = company.getInt("company_id");
                    //logni = company.getDouble("longitude");
                    //lati = company.getDouble("latitude");


                    //Log.d("name", companyname);
                    //int size = category.length();
                    //int size2 = review.length();
                    //Log.d("category", category.toString());
                    //Log.d("size", category.length() + "");
//                    Log.d("review", review.toString());
//                    Log.d("size2", review.length() + "");
//                    Log.d("Id",companyid+"");
//                    Log.d("logni",logni+"");
//                    Log.d("lati",lati+"");
//                    Log.d("Success", "yes");

//                    if(size==0)
//                    {
//                        Log.d("over", "s");
//                    }
//                    else {
////                            for (int i = 0; i < category.length(); i++)
////                            {
////
////                            JSONObject jsonobj = category.getJSONObject(i);
////
////                            cat_id = jsonobj.getInt("company_id");
////
////                             createat = jsonobj.getString("created_at");
////                          //  int brandid = jsonobj.getInt("brand_id");
////                             cat_name=jsonobj.getString("category_name");
////
////                             descript=jsonobj.getString("description");
////                        }
////                        for (int j = 0; j < review.length(); j++) {
////
////                            JSONObject jsonobjrv = review.getJSONObject(j);
////
////                             rvid = jsonobjrv.getInt("company_id");
////                             rvuser_id = jsonobjrv.getInt("user_id");
////                             rvdescript = jsonobjrv.getString("description");
////                             rvcat_name = jsonobjrv.getInt("behaviour");
////                             rvprice=jsonobjrv.getInt("price");
////                             rvaftersale=jsonobjrv.getInt("aftersale");
////                             rvtitle = jsonobjrv.getString("title");
////                             username = jsonobjrv.getString("name");
////                        }
//                    }
                }
                else
                {
                    //failed to create shop details
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }
            protected void onPostExecute(String file_url) {
            shopname.setText(company.getCompanyName());
            shopaddress.setText(company.getAddressline());
            companycontact.setText(company.getMobile()+"");
          companyrate.setText(company.getRate()+"");
                companyreview.setText(company.getReviewcount()+"");
            companytime.setText(time);
            companyholiday.setText(holiday);
            companyfinance.setText(finance);


                //Toast.makeText(getApplicationContext(),rvtitle,Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),descript,Toast.LENGTH_SHORT).show();
        }
    }
}