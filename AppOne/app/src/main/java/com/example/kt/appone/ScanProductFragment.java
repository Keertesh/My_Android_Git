package com.example.kt.appone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScanProductFragment extends Fragment {
    ListView lv;
   static ArrayList<String> strArr;
     static ArrayAdapter<String> adapter;
   static TextView textContent;
    Communicator com;
    String item;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.scan_product_fragment, container, false);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            lv = (ListView) getActivity().findViewById(R.id.listView2);
            strArr = new ArrayList<String>();
            adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, strArr);
            lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public SharedPreferences.Editor bundle;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                item = lv.getItemAtPosition(position).toString();

                //This is Showing null pointer exception
                Toast.makeText(getActivity(), "Product Added To Cart!!! " + item,
                        Toast.LENGTH_LONG).show();



                SharedPreferences settings = getActivity().getSharedPreferences("addToList", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("item", item);
                editor.commit();



            }
        });


    }



    public static void UpdateContent(String data)
    {
try {

    if (data == null)
    {

    }
    else {
        strArr.add(data);
        adapter.notifyDataSetChanged();
       MyCartFragment detailsView = new  MyCartFragment();
        Bundle args=new Bundle();
        args.putString("data",data);

        detailsView.setArguments(args);
    }
}
catch(Exception e)
{

}

    }


}