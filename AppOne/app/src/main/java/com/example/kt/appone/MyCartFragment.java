package com.example.kt.appone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyCartFragment extends Fragment {

    ListView lvMyCart;
    static ArrayList<String> strArr;
    static ArrayAdapter<String> adapterMyCart;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.my_cart_fragment, container, false);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvMyCart = (ListView) getActivity().findViewById(R.id.listViewMyCart);
        strArr = new ArrayList<String>();
        adapterMyCart = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strArr);
        lvMyCart.setAdapter(adapterMyCart);
        UpdateView();

    }

    @Override
    public void onStart() {
        super.onStart();
        UpdateView();
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateView();
    }




    public static void viewUpdater()
    {



    }
    public  void UpdateView() {


        SharedPreferences get = getActivity().getSharedPreferences("addToList", 0);
        String addToCart = get.getString("item", "null");
        //do whatever you want to do with the varaibles
        if (addToCart==null || addToCart =="null"|| addToCart== "No Value") {
          //Do Nothing

        } else
        {
            strArr.add(addToCart);
        adapterMyCart.notifyDataSetChanged();
    }
        SharedPreferences.Editor editor = get.edit();
        editor.clear();
        editor.commit();
    }


    public void UpdateViewSuper(String addTOList) {
        strArr.add(addTOList);
        adapterMyCart.notifyDataSetChanged();
    }
}