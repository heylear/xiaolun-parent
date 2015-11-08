package cn.glor.xiaolun.app.entity;

import cn.glor.xiaolun.app.entity.enumtype.AuditStatus;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by caosh on 2015/10/7.
 */
@Entity
@Table(name = "tt_seller_account", schema = "", catalog = "xlap")
public class SellerAccountEntity extends MpUserEntity {

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "entity_id")
    private RegionEntity region;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "employer")
    private String employer;

    @Column(name = "work_phone")
    private String workPhone;

    @ManyToOne
    @JoinColumn(name = "served_brand_id", referencedColumnName = "entity_id")
    private SubBrandEntity servedBrand;

    @ManyToMany
    @JoinTable(name = "tr_seller_expert_brand", catalog = "xlap", schema = "", joinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "entity_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "sub_brand_id", referencedColumnName = "entity_id", nullable = false))
    private List<SubBrandEntity> expertBrandList;

    @Column(name = "description")
    private String description;

    @Column(name = "consult_count_this_week")
    private int consultCountThisWeek;

    @Column(name = "audit_status")
    private AuditStatus auditStatus;

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(RegionEntity region) {
        this.region = region;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public SubBrandEntity getServedBrand() {
        return servedBrand;
    }

    public void setServedBrand(SubBrandEntity servedBrand) {
        this.servedBrand = servedBrand;
    }

    public List<SubBrandEntity> getExpertBrandList() {
        return expertBrandList;
    }

    public void setExpertBrandList(List<SubBrandEntity> expertBrandList) {
        this.expertBrandList = expertBrandList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getConsultCountThisWeek() {
        return consultCountThisWeek;
    }

    public void setConsultCountThisWeek(int consultCountThisWeek) {
        this.consultCountThisWeek = consultCountThisWeek;
    }

    public AuditStatus getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Override
    public String toString() {
        return "SellerAccountEntity{" +
                "region=" + region +
                ", birthday=" + birthday +
                ", employer='" + employer + '\'' +
                ", workPhone='" + workPhone + '\'' +
                ", servedBrand=" + servedBrand +
                ", expertBrandList=" + expertBrandList +
                ", description='" + description + '\'' +
                ", consultCountThisWeek=" + consultCountThisWeek +
                ", auditStatus=" + auditStatus +
                '}';
    }
}
