package kt.tech.com.lomaprototypeversion11;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Categorynew extends Fragment {
    ImageButton categoryone;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_categorynew,container,false);
        categoryone = (ImageButton)v.findViewById(R.id.categoryOne);
        categoryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        return v;
    }
}