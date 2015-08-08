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

public class CustomAdapter extends BaseAdapter{
    Context context;
    ImageView pic;
    ArrayList<SearchedProduct> searchedProductArrayList;
    private static LayoutInflater inflater=null;
    public CustomAdapter(SearchedProductList mainActivity, ArrayList<SearchedProduct> searchedProductsList) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        searchedProductArrayList=searchedProductsList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(SearchedProduct product){
        Log.v("add",product.getProductName());
        this.searchedProductArrayList.add(product);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return searchedProductArrayList.size();
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
        TextView ratingcount;
        ImageView pic;
        TextView review_count;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();

        View rowView;

        rowView = inflater.inflate(R.layout.product_list, null);

        holder.name=(TextView) rowView.findViewById(R.id.comment);
        holder.ratingcount=(TextView) rowView.findViewById(R.id.productRatecompanyproduct);
        holder.review_count=(TextView) rowView.findViewById(R.id.reviewCount_companyproduct);

        holder.pic= (ImageView) rowView.findViewById(R.id.imageView2);
        holder.pic.setAdjustViewBounds(true);
        // holder.pic.setMaxHeight(100);
        //holder.pic.setMaxWidth(100);
        SearchedProduct product=searchedProductArrayList.get(position);

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
        return rowView;
    }
}