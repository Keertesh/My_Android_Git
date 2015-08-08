package kt.tech.com.lomaprototypeversion11;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Search extends Activity implements TextWatcher {
    private ListView lv;
    ArrayList<SuggestedProduct> items=new ArrayList<SuggestedProduct>();
    JSONParser jsonParser = JSONParser.jsonconn;
    private String url_autosearch = "http://"+ JSONParser.ip+"/loma/public/user/autosearch";
    private final String TAG_STATUS = "status";

    private static String[] product = new String[10];
    //JSONArray items;
    EditText search;
    Button back, cancel;
    // ArrayList for Listview
    //ArrayList<HashMap<String, String>> productList;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_search);
        init();

        cancel.setVisibility(View.INVISIBLE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
                lv.setAdapter(null);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finishActivity();
                Intent bk = new Intent(getApplicationContext(), Home.class);
                startActivity(bk);

            }
        });





        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }

            private void performSearch() {

                String productname = search.getText().toString();

                Intent product_search = new Intent(getApplicationContext(), SearchedProductList.class);
                product_search.putExtra("query", productname);

                startActivity(product_search);

                //Toast.makeText(getApplicationContext(), productname, Toast.LENGTH_SHORT).show();
            }
        });


        search.addTextChangedListener(this);


        Log.d("Create Response", search.toString());

    }

    private void init() {
        search =   (EditText) findViewById(R.id.inputSearch);
        back  =  (Button) findViewById(R.id.back);
        cancel =  (Button) findViewById(R.id.cancel);
        lv = (ListView) findViewById(R.id.searchList);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        if (search.length()>1) {
            cancel.setVisibility(View.VISIBLE);
            new CreateNewProduct().execute();

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {


    }

    class CreateNewProduct extends AsyncTask<String, String, String> {
       /**
         * Before starting background thread Show Progress Dialog
         */

        @Override
        protected void onPreExecute() {
        super.onPreExecute();
        }

        /**
         * Adding product
         */
        protected String doInBackground(String... args) {
            String search = Search.this.search.getText().toString();
            Log.d("Create Response", search);

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();

            params1.add(new BasicNameValuePair("autosearch", search));

            // getting JSON Object

            JSONObject jsonProducts = jsonParser.makeHttpRequest(url_autosearch,
                    "POST", params1);

            Log.d("JSON Response", jsonProducts.toString());

            // check for success tag
            try {
                    items.clear();
                int status = jsonProducts.getInt(TAG_STATUS);

                if (status == 1) {
                    JSONArray jsonProductArray = new JSONArray(jsonProducts.get("Product").toString());
                    product=new String[jsonProductArray.length()];
                    Log.v("Response Product Array", jsonProductArray.toString());

                    for (int i = 0; i < jsonProductArray.length(); i++) {
                        JSONObject productJSON = jsonProductArray.getJSONObject(i);
                        String productName = productJSON.getString("product_name");
                        int productId= productJSON.getInt("product_id");
                        product[i]=productName;
                        SuggestedProduct product=new SuggestedProduct(productId,productName);

                        Log.d("Product", product+ "");
                        items.add(product);
                    }
                }
                else {
                    //failed to create product
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Search.this, R.layout.search_list_item, R.id.product_name,product);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        SuggestedProduct product=items.get(position);
                        long product_id=product.getProduct_id();
                        Intent productIntent=new Intent(Search.this,Productdetail.class);
                        productIntent.putExtra("productId",product_id);
                        startActivity(productIntent);
                    Toast.makeText(getApplicationContext(),product_id+" ", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}