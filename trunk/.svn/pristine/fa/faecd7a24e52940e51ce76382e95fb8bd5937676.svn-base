package kt.tech.com.lomaprototypeversion11;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Shop_more extends Activity {

    Button mycall,mychat,mydirection,myreview,myfullreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_more);
        mycall=(Button) findViewById(R.id.companydetailcall);
       // mychat=(Button) findViewById(R.id.chating);
        mydirection=(Button) findViewById(R.id.companydetailmap);
        myreview=(Button) findViewById(R.id.rvbtn);
        myfullreview=(Button) findViewById(R.id.fulrv);

        //--lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        //--lock screen for always Portrait mode


        mycall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                // TODO Auto-generated method stub
                Intent shopcall=new Intent(Intent.ACTION_DIAL);
                shopcall.setData(Uri.parse("tel:9993689602"));
                startActivity(shopcall);
            }
        });
        mychat.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "chat started",Toast.LENGTH_SHORT).show();
            }
        });
        mydirection.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                // TODO Auto-generated method stub
                Intent shopdetail=new Intent(Shop_more.this,Maps4shop_more.class);
                startActivity(shopdetail);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
