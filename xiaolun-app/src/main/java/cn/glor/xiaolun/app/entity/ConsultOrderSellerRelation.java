package cn.glor.xiaolun.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by caosh on 2015/10/11.
 */
@Entity
@Table(name = "tr_consult_seller", schema = "", catalog = "xlap")
public class ConsultOrderSellerRelation extends BaseEntity {

    @Column(name = "consult_id")
    private long consultId;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "seller_id")
    private long sellerId;

    public long getConsultId() {
        return consultId;
    }

    public void setConsultId(long consultId) {
        this.consultId = consultId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

}
