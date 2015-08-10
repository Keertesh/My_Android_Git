package com.example.kt.appone;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ScanProduct extends Fragment {
    public Button scanBtn,addProduct;
    public static TextView formatTxt, contentTxt;
    public ListView listView;
    private ArrayList<String> strArr;
    private ArrayAdapter<String> adapter;
    String strtext;
   public static String tagScanProduct;
    EditText text;
    Communicator com;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.scan_product, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tagScanProduct= getTag();

        scanBtn= (Button) getActivity().findViewById(R.id.scan_button);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // com.respond(String.valueOf(text));
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.initiateScan();
            }
        });
    }





}



