package cn.glor.xiaolun.app.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by caosh on 2015/10/30.
 */
@Entity
@Table(name = "tm_config", schema = "", catalog = "xlap")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConfigEntity extends BaseEntity {

    @Column(name = "cfg_key")
    private String key;

    @Column(name = "cfg_value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConfigEntity{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
