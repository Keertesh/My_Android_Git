package kt.tech.com.lomaprototypeversion11;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class companyDetailsAdapter extends BaseAdapter {
    Context context;
    private static LayoutInflater inflater=null;
    ArrayList<ProductCompanyList> companydata = new ArrayList<ProductCompanyList>();

    public companyDetailsAdapter(Productdetail mainActivity,
                                 ArrayList<ProductCompanyList> companydata) {


        this.context = mainActivity;
        this.companydata = companydata;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return companydata.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class Holder
    {
        TextView companyname;
        TextView companyaddressline;
        TextView productprice;
        Button call;
        Button direction;
        Button chat;
        TextView rate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row =inflater.inflate(R.layout.row, null);
       Holder holder=new Holder();

       holder.companyname= (TextView) row.findViewById(R.id.comment);
       holder.companyaddressline= (TextView) row.findViewById(R.id.companyaddressline);
        holder.productprice= (TextView) row.findViewById(R.id.productdescription);
       holder.call= (Button) row.findViewById(R.id.companycallbutton);
       holder.direction= (Button) row.findViewById(R.id.companydirection);
        holder.rate= (TextView) row.findViewById(R.id.companyRate);

        final ProductCompanyList company=companydata.get(position);
        holder.companyname.setText(company.getCompanyName());
        holder.companyaddressline.setText(company.getAddressline());
        holder.productprice.setText(company.getPrice() + " ");
        holder.rate.setText(company.getRate()+"");
        holder.call.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(context, "Calling ... ",Toast.LENGTH_SHORT).show();
                Intent phonecall=new Intent(Intent.ACTION_DIAL);
                phonecall.setData(Uri.parse("tel:"+company.getMobile()));
                context.startActivity(phonecall);

            }
        });

//        holder.btnchat.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                Toast.makeText(context, "Chat will be started",Toast.LENGTH_SHORT).show();
//                Intent pdchat = new Intent(context,Chat4pd.class);
//                context.startActivity(pdchat);
//            }
//        });

        holder.direction.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Toast.makeText(context, "Directing to maps",Toast.LENGTH_SHORT).show();
                Intent pdmap = new Intent(context,companyMapView.class);
                pdmap.putExtra("latitude",company.getLat());
                pdmap.putExtra("longitude",company.getLongi());
                pdmap.putExtra("companyname",company.getCompanyName());
                context.startActivity(pdmap);
            }
        });
        return row;
    }

}
