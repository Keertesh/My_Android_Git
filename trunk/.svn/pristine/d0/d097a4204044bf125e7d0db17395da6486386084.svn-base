package kt.tech.com.lomaprototypeversion11;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopDetails extends Activity {
    long company_id;
    ListView companyProductListView;
    ProductCompanyList company;
    ImageView img;
    boolean end=false;
    boolean loadMore=false;
    int start=0;
    companyProductAdapter companyProductViewAdapter;
    TextView companyName,companyAddress,reviewcountcompany,companyRate;
    JSONParser jsonParser= JSONParser.jsonconn;
    Button detail,direction,firstchat,firstcall,viewmoreproducts;
    String url_company_product = "http://"+ JSONParser.ip+"/loma/public/user/companyproducts";
    ArrayList<companyProducts> productsList=new ArrayList<companyProducts>();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ShowToast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_details);
        detail=(Button) findViewById(R.id.more);
        direction=(Button) findViewById(R.id.companyproductmap);
        //firstchat=(Button) findViewById(R.id.chat);
        firstcall=(Button) findViewById(R.id.call);
        companyProductListView=(ListView) findViewById(R.id.companyproductactivitylistview);
        companyName= (TextView) findViewById(R.id.companyName);
        companyAddress= (TextView) findViewById(R.id.companydetailaddress);
        reviewcountcompany= (TextView) findViewById(R.id.reviewcountcompany);
        companyRate= (TextView) findViewById(R.id.companyRate);
        viewmoreproducts= (Button) findViewById(R.id.companyproductactivitymorebutton);
        img=(ImageView) findViewById(R.id.shopimg);
        company= (ProductCompanyList) getIntent().getSerializableExtra("company");
        companyName.setText(company.getCompanyName());
        companyAddress.setText(company.getAddressline());
        reviewcountcompany.setText(company.getReviewcount()+" ");
        companyRate.setText(company.getRate()+"");
        company_id=company.getCompanyId();
        //img.setBackgroundResource(R.drawable.mrkt2);
        img.setBackgroundColor(Color.rgb(0,0,0));
       // img.setAlpha(0.f);

            new productsAsync().execute();
        companyProductViewAdapter=new companyProductAdapter(ShopDetails.this,productsList);
        companyProductListView.setAdapter(companyProductViewAdapter);

        viewmoreproducts.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!end)
                new productsAsync().execute();
            }
        });

        companyProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                companyProducts produ=productsList.get(i);
                Intent product=new Intent(getApplicationContext(),Productdetail.class);
                product.putExtra("productId",produ.getProductId());
                startActivity(product);

            }
        });

//        list.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                String itemValue = (String) list.getItemAtPosition(position);
//
//                Toast.makeText(getApplicationContext(),
//                        "" + itemValue, Toast.LENGTH_SHORT)
//                        .show();
//
//
//            }


//        });

        detail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub

                Intent myshop=new Intent(getApplicationContext(),Half_map4shop_more.class);
                Bundle cdata=new Bundle();
                cdata.putSerializable("company",company);
                myshop.putExtras(cdata);
                startActivity(myshop);

                Toast.makeText(getApplicationContext(),"...", Toast.LENGTH_LONG).show();

            }
        });
        direction.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent showmap=new Intent(ShopDetails.this,companyMapView.class);
                showmap.putExtra("latitude",company.getLat());
                showmap.putExtra("longitude",company.getLongi());
                showmap.putExtra("companyname",company.getCompanyName());
                startActivity(showmap);
            }
        });

        firstcall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent phonecall=new Intent(Intent.ACTION_DIAL);
                phonecall.setData(Uri.parse("tel:"+company.getMobile()));
                startActivity(phonecall);
            }
        });
    }
    class productsAsync extends AsyncTask<String,String,String>{
        ProgressDialog dialog =
                new ProgressDialog(ShopDetails.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Getting Products of this shop ");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            loadMore=true;

            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("token", UserDetails.userdata.getToken()));
            params.add(new BasicNameValuePair("company_id", company_id + ""));
            params.add(new BasicNameValuePair("start", start+""));


            JSONObject productsJSON=jsonParser.makeHttpRequest(url_company_product,"POST",params);
            Log.d("products", productsJSON.toString());
            try {
                int status=productsJSON.getInt("status");
                if(status==1) {


                        JSONArray productsArray = productsJSON.getJSONArray("products");
                         int size = productsArray.length();

                        if (size == 0) {
                            loadMore = true;
                            end = true;
                        }
                        else{
                        for (int i = 0; i < productsArray.length(); i++) {
                            start++;

                            JSONObject product = productsArray.getJSONObject(i);
                            companyProducts pro = new companyProducts();
                            pro.setProductId(product.getLong("product_id"));
                            pro.setProductName(product.getString("name"));
                            pro.setProductMRP(product.getLong("product_MRP"));
                            pro.setProductPrice(product.getLong("product_price"));
                            pro.setReviewCount(product.getInt("review"));
                            String rate = product.getString("rating");
                            if (rate == "null") {
                                rate = 0 + "";
                            }
                            pro.setAverageRate(Float.parseFloat(rate));
                            productsList.add(pro);
                            Log.d("product", pro.getProductName());
                        }
                        loadMore=false;


                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
             companyProductViewAdapter.notifyDataSetChanged();
            Helper4Sd.getListViewSize(companyProductListView);
            if(loadMore && end){
                viewmoreproducts.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"No more products found",Toast.LENGTH_SHORT).show();
                //SearchedListView.removeFooterView(loadingMore);

                // SearchedListView.removeView(loadingMore);

                //SearchedListView.addFooterView(endFooter,null,false);
            }

        }
    }


}
