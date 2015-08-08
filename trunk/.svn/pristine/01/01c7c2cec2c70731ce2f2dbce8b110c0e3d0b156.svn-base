package kt.tech.com.lomaprototypeversion11;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Productdetail extends Activity {

    ListView companyList;
    ProductDetails productDetail;
    String url_product = "http://"+ JSONParser.ip+"/loma/public/user/product";
    String url_product_company = "http://"+ JSONParser.ip+"/loma/public/user/productcompany";
    long productId;
    companyDetailsAdapter companyAdapter;

    JSONParser jsonParser= JSONParser.jsonconn;

    ArrayList<ProductCompanyList> companyArrayList=new ArrayList<ProductCompanyList>();

    Button more,map,review,viewmore;
    boolean loadMore=false;
    boolean end=false;
    int start=0;
    ImageView mobileimg;
    View loadMoreView;
    RatingBar pdrate;
    TextView productName;
    TextView desc;
    TextView productrate,productreviewcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.productdetail);
        //more = (Button) findViewById(R.id.morefeature);
        map = (Button) findViewById(R.id.showmap);
        viewmore= (Button) findViewById(R.id.viewmorecompanies);
       // review = (Button) findViewById(R.id.writereview);
        //pdrate = (RatingBar) findViewById(R.id.pdratingBar);
//        pdrate.setOnRatingBarChangeListener(this);
       productName = (TextView) findViewById(R.id.mobilename);
         desc= (TextView) findViewById(R.id.productdescription);
        productrate= (TextView) findViewById(R.id.productdetailrate);
        productreviewcount= (TextView) findViewById(R.id.productdetailreveiwcount);
//        userArray.add(new User("Semiconductor world", "Supela", "Rs.12200"));
//        userArray.add(new User("xpress store", "smriti nagar", "Rs.10000"));
//        userArray.add(new User("Mobile Store", "station road", "Rs.80000"));
//        userArray.add(new User("Glaxy Store", "Surya mall", "Rs.12200"));
//        userArray.add(new User("Magneto Store", "durg", "Rs.5000"));
//        userArray.add(new User("36 Mall", "Bilaspur", "Rs.25000"));
//        userArray.add(new User("VPS Store", "Bilaspur", "Rs.150000"));
       // companyAdapter = new companyDetailsAdapter(Productdetail.this, R.layout.row, userArray);


        companyList = (ListView) findViewById(R.id.companyproductactivitylistview);

        companyAdapter=new companyDetailsAdapter(Productdetail.this,companyArrayList);
        companyList.setAdapter(companyAdapter);
        Helper.getListViewSize(companyList);
        //SearchedListView.addFooterView(loadingMore,null,false);
        companyList.setDividerHeight(10);
        companyList.setTextFilterEnabled(true);
        loadMoreView = ((LayoutInflater)this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.viewmorecompany, null, false);
        viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!end)
                new productCompanyAsync().execute();

                          }
        });
        //companyList.addFooterView(loadMoreView);

        companyList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView,  int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                Log.d("Inside Values", firstVisibleItem + " Visible" + visibleItemCount + " Total " + totalItemCount);

                if (firstVisibleItem==(visibleItemCount-1) &&firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && !(loadMore)) {
                    Log.d("Inside Values", firstVisibleItem + " Visible" + visibleItemCount + " Total " + totalItemCount);
                   // new productCompanyAsync().execute();
                }
            }
        });


        productId=getIntent().getLongExtra("productId",0);
        new ProductDetailsAsync().execute();
            new productCompanyAsync().execute();
        companyList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                // TODO Auto-generated method stub
//                TextView shoptxt = (TextView) view.findViewById(R.id.companyname);
//                String shopname = shoptxt.getText().toString();
//
//                TextView addtxt = (TextView) view.findViewById(R.id.companyaddressline);
//                String add = addtxt.getText().toString();
//
//                TextView ratetxt = (TextView) view.findViewById(R.id.productdescription);
//                String rate = ratetxt.getText().toString();
//
//                Toast.makeText(getApplicationContext(), "" + shopname + add + rate, Toast.LENGTH_SHORT).show();
                Intent gotoshop = new Intent(getApplicationContext(), ShopDetails.class);
                Bundle companyObject=new Bundle();
                Log.d("company",companyArrayList.get(position).getCompanyName());
                companyObject.putSerializable("company",companyArrayList.get(position));
                gotoshop.putExtras(companyObject);
                startActivity(gotoshop);


            }

        });

        //-------message passing coding start---------

//        mobileimg = (ImageView) findViewById(R.id.imageView1);
//        mobileimg.requestFocus();
//        mobilenametxt = (TextView) findViewById(R.id.mobilename);
//
//        Bundle bmob = getIntent().getExtras();
//        Bitmap bitmap = (Bitmap) this.getIntent().getParcelableExtra("Bitmap");
//
//        mobileimg.setImageBitmap(bitmap);
//        if (bmob != null) {
//
//            String mobilename = "" + (String) bmob.get("aditya");
//            mobileimg.setImageBitmap(bitmap);
//            mobilenametxt.setText(mobilename + "" + "mobile");
//        }
//        //---------------------message passing coding end.......
//        more.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(android.view.View v) {
//                // TODO Auto-generated method stub
//                Intent more = new Intent(getApplicationContext(), More_feat.class);
//                startActivity(more);
//            }
//        });
//        map.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(android.view.View v) {
//                // TODO Auto-generated method stub
//                Intent mymap = new Intent(getApplicationContext(), MapsActivity4pd.class);
//                startActivity(mymap);
//            }
//        });
//        review.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(android.view.View v) {
//                // TODO Auto-generated method stub
//                Intent myreview = new Intent(getApplicationContext(), Write_review.class);
//                startActivity(myreview);
//            }
//        });

    }

    class ProductDetailsAsync extends AsyncTask<String,String,ProductDetails>{
        ProductDetails product;

        @Override
        protected ProductDetails doInBackground(String... strings) {
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("product_id", productId+""));
            params.add(new BasicNameValuePair("token", UserDetails.userdata.getToken()));

            Log.d("token", UserDetails.userdata.getToken()+" ");
            JSONObject productDetailjson=jsonParser.makeHttpRequest(url_product,"POST",params);
            Log.d("details",productDetailjson.toString());
            try {
                JSONObject productJson=productDetailjson.getJSONObject("product_detail");
                 product=new ProductDetails();
                product.setProductName(productJson.getString("product_name"));
                product.setDescription(productJson.getString("Description"));
                String rate=productJson.getString("product_rate");

                if(rate.equals("null")){
                    Log.d("status", "nul");
                    rate = 0 + "";
                }
                product.setRate(Float.parseFloat(rate));
                product.setReviewCount(productJson.getInt("product_review_count"));

         }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return product;
        }

        @Override
        protected void onPostExecute(ProductDetails productDetails) {
            super.onPostExecute(productDetails);
            productName.setText(productDetails.getProductName());
            desc.setText(productDetails.getDescription());
            productrate.setText(productDetails.getRate()+"");
            productreviewcount.setText(productDetails.getReviewCount()+"");
        }
    }

    class productCompanyAsync extends AsyncTask<String,String,String>{
        ProgressDialog dialog =
                new ProgressDialog(Productdetail.this);
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog.setMessage("Fetching Sellers ... Please wait...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            loadMore=true;
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("product_id", productId+""));
            params.add(new BasicNameValuePair("token", UserDetails.userdata.getToken()));
            params.add(new BasicNameValuePair("start", start + ""));
           // Log.d("token",UserDetails.userdata.getToken()+" ");
            JSONObject companyjson=jsonParser.makeHttpRequest(url_product_company,"POST",params);
            Log.d("details", companyjson.toString());

            try {
                int status=companyjson.getInt("status");

                if(status==1) {

                    JSONArray companies = companyjson.getJSONArray("companies");
                    int size = companies.length();

                    if (size == 0) {
                        loadMore=true;
                        end=true;


                    } else {

                           for (int i = 0; i < companies.length(); i++) {
                               start++;
                        JSONObject company = companies.getJSONObject(i);
                        long cid = company.getLong("company_id");
                        int reviews=company.getInt("reviews");
                        String rate=company.getString("rate");
                        String address=company.getString("addressline");
                        String cname = company.getString("company_name");
                        int mrp = company.getInt("product_MRP");
                        int price = company.getInt("product_price");
                        long mobile = Long.parseLong(company.getString("username"));
                        double lat = Double.parseDouble(company.getString("latitude"));
                        double longi = Double.parseDouble(company.getString("longitude"));
                        float distance = (float) company.getDouble("distance");
                        ProductCompanyList company_data = new ProductCompanyList();
                        company_data.setCompanyId(cid);
                        company_data.setReviewcount(reviews);
                               if (rate.equals("null")) {
                                   Log.d("status", "nul");
                                   rate = 0 + "";
                               }
                        company_data.setRate(Float.parseFloat(rate));
                        company_data.setCompanyName(cname);
                        company_data.setAddressline(address);
                        company_data.setMrp(mrp);
                        company_data.setPrice(price);
                        company_data.setMobile(mobile);
                        company_data.setLat(lat);
                        company_data.setLongi(longi);
                        Log.d("company", cid + " " + cname + " " + lat + "d " + distance);
                        companyArrayList.add(company_data);
                    }
                        loadMore=false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                companyAdapter.notifyDataSetChanged();
                            }
                        });
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
            Helper.getListViewSize(companyList);
            if(loadMore && end){
                viewmore.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"No more companies found",Toast.LENGTH_SHORT).show();
                //SearchedListView.removeFooterView(loadingMore);

                // SearchedListView.removeView(loadingMore);

                //SearchedListView.addFooterView(endFooter,null,false);
            }
        }
    }


}



