package kt.tech.com.lomaprototypeversion11;

/**
 * Created by Prateek on 7/17/2015.
 */
public class SuggestedProduct {
    long product_id;
    String product_name;

    public SuggestedProduct(long product_id, String product_name) {
        this.product_id = product_id;
        this.product_name = product_name;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
