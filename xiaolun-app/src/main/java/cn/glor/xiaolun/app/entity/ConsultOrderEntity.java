package cn.glor.xiaolun.app.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by caosh on 2015/10/7.
 */
@Entity
@Table(name = "tt_consult_order", schema = "", catalog = "xlap")
public class ConsultOrderEntity extends BaseEntity {

    @Column(name = "wanted_consultants")
    private int wantedConsultants;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "entity_id")
    private RegionEntity city;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "entity_id", nullable = false)
    private MpUserEntity customer;

    @ManyToMany
    @JoinTable(name = "tr_consult_seller", catalog = "xlap", schema = "", joinColumns = @JoinColumn(name = "consult_id", referencedColumnName = "entity_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "entity_id", nullable = false))
    private List<SellerAccountEntity> sellerList;

    @ManyToMany
    @JoinTable(name = "tr_consult_sub_brand", catalog = "xlap", schema = "", joinColumns = @JoinColumn(name = "consult_id", referencedColumnName = "entity_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "sub_brand_id", referencedColumnName = "entity_id", nullable = false))
    private List<SubBrandEntity> subBrandList;

    public int getWantedConsultants() {
        return wantedConsultants;
    }

    public void setWantedConsultants(int wantedConsultants) {
        this.wantedConsultants = wantedConsultants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RegionEntity getCity() {
        return city;
    }

    public void setCity(RegionEntity city) {
        this.city = city;
    }

    public MpUserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(MpUserEntity customer) {
        this.customer = customer;
    }

    public List<SellerAccountEntity> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<SellerAccountEntity> sellerList) {
        this.sellerList = sellerList;
    }

    public List<SubBrandEntity> getSubBrandList() {
        return subBrandList;
    }

    public void setSubBrandList(List<SubBrandEntity> subBrandList) {
        this.subBrandList = subBrandList;
    }

    @Transient
    public String getCreateTimeStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(createTime.getTime()));
    }
}
