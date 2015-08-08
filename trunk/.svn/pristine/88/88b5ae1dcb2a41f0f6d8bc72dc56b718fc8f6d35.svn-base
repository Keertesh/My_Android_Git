package kt.tech.com.lomaprototypeversion11;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchedProductList extends Activity  {
    ListView SearchedListView;
    Context context;
    Intent i;
    Button btnsort;
CustomAdapter dataAdapter;
    // Lazy Load
    View endFooter,loadingMore;
    int start=0;
    boolean loadMore=false;
    boolean end=false;
    //hello..
    String url_search = "http://"+ JSONParser.ip+"/loma/public/user/search";
    JSONParser jsonParser= JSONParser.jsonconn;
    ArrayList<SearchedProduct> searchedProductsList=new ArrayList<SearchedProduct>();

    //JSONArray products;
//    private  String[] product_search_name;
//    String[] product_rate;
//    int [] product_rate_count;
//    // private static String[] product_search_rate = new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity_main);
        init();


        String styledText = "<big> <font color='#ffffff'>"
                + "  Sort By" + "</font> </big>" + "<br />"
                + "<small><font color='#ff0000'>"+"Relevance"+ "</small>";
        btnsort.setText(Html.fromHtml(styledText));

        endFooter = ((LayoutInflater)this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.endproductfooter, null, false);

        loadingMore = ((LayoutInflater)this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.loadingproducts, null, false);

        dataAdapter=new CustomAdapter(SearchedProductList.this,searchedProductsList);
        SearchedListView.setAdapter(dataAdapter);
        SearchedListView.addFooterView(loadingMore,null,false);
        SearchedListView.setDividerHeight(10);

        SearchedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SearchedProduct product=searchedProductsList.get(position);
                long productId=product.getProductId();
                Intent ProductDetail=new Intent(getApplicationContext(),Productdetail.class);
                ProductDetail.putExtra("productId",productId);
                startActivity(ProductDetail);

            }
        });


        SearchedListView.setTextFilterEnabled(true);
       // SearchedListView.addFooterView(endFooter);

      //  new ProductSearchAsync().execute();

        SearchedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                Log.d("Values", firstVisibleItem + " Visible" + visibleItemCount + " Total " + totalItemCount);

                //Log.d("Scroll Values",firstVisibleItem+" Visible"+visibleItemCount+ " Total "+totalItemCount);
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && !(loadMore)) {
                    Log.d("Inside Values", firstVisibleItem + " Visible" + visibleItemCount + " Total " + totalItemCount);
                    new ProductSearchAsync().execute();
                }

            }


        });
        new ProductSearchAsync().execute();


        btnsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final CharSequence[] items = {
                        "Relevance", "Popularity", "Price High to Low", "Price Low to High"};

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchedProductList.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {//coding for click event
                        // Do something with the selection
                        String styledText = "<big> <font color='#ffffff'>"
                                + "  Sort By" + "</font> </big>" + "<br />"
                                + "<small><font color='#ff0000'>" + items[item] + "" + "</small>";
                        btnsort.setText(Html.fromHtml(styledText));

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });

    }

    private void init() {
        btnsort= (Button) findViewById(R.id.sort);
        context = this;
        SearchedListView = (ListView) findViewById(R.id.companyproductactivitylistview);
    }


    class ProductSearchAsync extends AsyncTask<String,String,String>{
        ProgressDialog dialog =
                new ProgressDialog(SearchedProductList.this);
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
           // dialog.setMessage("Fetching Products ... Please wait...");
            //dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            loadMore=true;
            String search=getIntent().getStringExtra("query");
  //      loadMore=true;
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("search", search));
            params.add(new BasicNameValuePair("start", start + ""));

            JSONObject searchedProductjson=jsonParser.makeHttpRequest(url_search,"POST",params);
            Log.d("value", searchedProductjson.toString());

            try {

                int success = searchedProductjson.getInt("status");

                if (success == 1) {
                    JSONArray json1 = new JSONArray(searchedProductjson.get("product_search").toString());
                    int size=json1.length();
                    Log.d("products", json1.toString());
                    Log.d("size",json1.length()+"");

                   // products=json1;
                    //Log.d("value",jsonobj.toString());
                    //List<String> searchList;
                    //searchList = new ArrayList<String>();

//                    product_search_name=new String[json1.length()];
//                    //product_search_rate=new String[json1.length()];
//                    product_search_name=new String[json1.length()];
//                    product_rate=new String[json1.length()];
//                    product_rate_count=new int[json1.length()];
                    if(size==0){
                        Log.d("over", "s");
                        loadMore=true;
                        end=true;
                      //  Toast.makeText(getApplicationContext(),"Sorry no more products found !!!",Toast.LENGTH_SHORT).show();
                    //   SearchedListView.addFooterView(endFooter);

                    }
                    else {
                        for (int i = 0; i < json1.length(); i++) {
                            start++;
                            Log.d("start value",start+"");
                            JSONObject jsonobj = json1.getJSONObject(i);

                            JSONObject product = jsonobj.getJSONObject("product");
                            JSONObject review = jsonobj.getJSONObject("review");
                            String name = product.getString("name");
                            String rate1 = review.getString("Rating");
                            int reviews = review.getInt("Rating_Count");
                            int id=product.getInt("product_id");

                            if (rate1.equals("null")) {
                                Log.d("status", "nul");
                                rate1 = 0 + "";
                            }
                            SearchedProduct productobj = new SearchedProduct();
                            productobj.setProductName(name);
                            productobj.setAverageRate(Float.parseFloat(rate1));
                            productobj.setReviewCount(reviews);
                            productobj.setProductId(id);
                            searchedProductsList.add(productobj);
                            //dataAdapter.add(productobj);
                            Log.d("Product", name + rate1 + "");
                            //int rate1 = review.getInt("Rating");

                        }
                        loadMore=false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dataAdapter.notifyDataSetChanged();
                            }
                        });

                    }
//


                    // product_search_rate=new String[searchList.size()];


                }

                else {
                    //failed to create product
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //dialog.dismiss();

            if(loadMore && end){
                SearchedListView.removeFooterView(loadingMore);

                //SearchedListView.removeView(loadingMore);

                SearchedListView.addFooterView(endFooter,null,false);
            } else{


            }

        }
    }
}