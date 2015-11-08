package cn.glor.xiaolun.app.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by caosh on 2015/10/18.
 */
@Entity
@Table(name = "tm_sub_brand", schema = "", catalog = "xlap")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class SubBrandEntity extends BaseEntity implements Comparable<SubBrandEntity> {

    @Column(name = "bd_name")
    private String name;

    @Column(name = "pinyin_abbr")
    private String pinyinAbbr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyinAbbr() {
        return pinyinAbbr;
    }

    public void setPinyinAbbr(String pinyinAbbr) {
        this.pinyinAbbr = pinyinAbbr;
    }

    @Override
    public int compareTo(SubBrandEntity o) {
        if (entityId == null) {
            return -1;
        }

        if (o == null || o.entityId == null) {
            return 1;
        }

        return entityId.compareTo(o.getEntityId());
    }

    @Override
    public String toString() {
        return "SubBrandEntity{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", pinyinAbbr='" + pinyinAbbr + '\'' +
                '}';
    }
}
