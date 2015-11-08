package cn.glor.xiaolun.app.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by caosh on 2015/11/1.
 */
@Entity
@Table(name = "tm_region", schema = "", catalog = "xlap")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RegionEntity extends BaseEntity {

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "region_py_short")
    private String regionPyShort;

    @Column(name = "region_py")
    private String regionPy;

    @Column(name = "parent_code")
    private String parentCode;

    @Column(name = "biz_opened")
    private Integer bizOpened;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionPyShort() {
        return regionPyShort;
    }

    public void setRegionPyShort(String regionPyShort) {
        this.regionPyShort = regionPyShort;
    }

    public String getRegionPy() {
        return regionPy;
    }

    public void setRegionPy(String regionPy) {
        this.regionPy = regionPy;
    }
    
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getBizOpened() {
        return bizOpened;
    }

    public void setBizOpened(Integer bizOpened) {
        this.bizOpened = bizOpened;
    }
}
