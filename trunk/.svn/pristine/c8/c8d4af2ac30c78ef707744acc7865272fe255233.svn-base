package kt.tech.com.lomaprototypeversion11;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class companyProductAdapter extends BaseAdapter{
    Context context;
    ImageView pic;
    ArrayList<companyProducts> companyProductsList;
    private static LayoutInflater inflater=null;
    public companyProductAdapter(ShopDetails Activity, ArrayList<companyProducts> companyProductsList) {
        // TODO Auto-generated constructor stub
        context=Activity;
        this.companyProductsList=companyProductsList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    public void add(SearchedProduct product){
//        Log.v("add",product.getProductName());
//        this.searchedProductArrayList.add(product);
//    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return companyProductsList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name;

        TextView productMRP;
        TextView productPrice;

        TextView ratingcount;
        ImageView pic;
        TextView review_count;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.companyproductview, null);

        holder.name=(TextView) rowView.findViewById(R.id.companyproductactivityproductname);
        holder.ratingcount=(TextView) rowView.findViewById(R.id.companyproductactivityproductrate);
        holder.review_count=(TextView) rowView.findViewById(R.id.companyproductactivityreviewcount);
        holder.productMRP= (TextView) rowView.findViewById(R.id.companyproductactivityproductMRP);
        holder.productPrice= (TextView) rowView.findViewById(R.id.companyproductactivityproductPrice);
        holder.pic= (ImageView) rowView.findViewById(R.id.companyProductImageview);
        holder.pic.setAdjustViewBounds(true);
        // holder.pic.setMaxHeight(100);
        //holder.pic.setMaxWidth(100);
        companyProducts product=companyProductsList.get(position);

        holder.name.setText(product.getProductName());
        holder.review_count.setText(product.getReviewCount()+" ");
        String rate=product.getAverageRate()+"";
        if(rate.equals(null)){
            Log.d("null",rate.toString());
            rate=0+"";
        }
        else{

        }

        holder.ratingcount.setText(rate);
        holder.productMRP.setText(product.getProductMRP()+"");
        holder.productPrice.setText(product.getProductPrice()+"");

        return rowView;
    }
}