package be.adam.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="productviews")
public class ProductView {
    @Id
    private long productId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public ProductView(){}

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
