package kt.tech.com.lomaprototypeversion11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Searchnew extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    public SliderLayout mDemoSlider;
    String url = "http://www.fundoosale.com/saleAdImage/john-players-eoss-jun-20-2014.jpg";

    ImageView imageView,imageView1,imageView2,imageView3;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_searchnew,container,false);
        imageView = (ImageView) v.findViewById(R.id.imageView3);
        imageView1 = (ImageView) v.findViewById(R.id.imageView4);
        imageView2 = (ImageView) v.findViewById(R.id.imageView6);
        imageView3 = (ImageView) v.findViewById(R.id.imageView8);
        new ImageLoadTask(url,imageView,imageView1,imageView2,imageView3).execute();

        mDemoSlider = (SliderLayout)v.findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Mobiles", "http://mohanshopping.com/images/banner1.jpg");
        url_maps.put("Laptops", "http://wirelessduniya.com/wp-content/uploads/2015/03/laptop.jpg");
        url_maps.put("Scorpio", "https://i.ytimg.com/vi/R-EKeYkv3UA/maxresdefault.jpg");
        url_maps.put("Loma", "http://store.deathwishinc.com/graphics/00000001/loma.underline.sticker.hi.jpg");
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);


        return v;
    }
    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

        //  Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();

    }
}

class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView,imageView1,imageView2,imageView3;;

    public ImageLoadTask(String url, ImageView imageView,ImageView  imageView1,ImageView imageView2,ImageView imageView3) {
        this.url = url;

        this.imageView  = imageView;
        this.imageView1 = imageView1;
        this.imageView2 = imageView2;
        this.imageView3 = imageView3;

    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();

            connection.setDoInput(true);

            connection.connect();

            InputStream input = connection.getInputStream();

            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
        imageView1.setImageBitmap(result);
        imageView2.setImageBitmap(result);
        imageView3.setImageBitmap(result);
    }

}


