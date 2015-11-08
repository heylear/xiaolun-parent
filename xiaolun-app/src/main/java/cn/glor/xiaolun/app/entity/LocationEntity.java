package cn.glor.xiaolun.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by heylear on 15/10/31.
 */
@Entity
@Table(name = "tt_location", schema = "", catalog = "xlap")
public class LocationEntity extends BaseEntity {

    @Column(name = "mem_user_id")
    long memUserId;

    @Column(name = "cur_loc_x")
    double curLocX;

    @Column(name = "cur_loc_y")
    double curLocY;

    @Column(name = "pre_loc_x")
    double preLocX;

    @Column(name = "pre_loc_y")
    double preLocY;

    @Column(name = "cur_prov")
    String cur_prov;

    @Column(name = "cur_city")
    String cur_city;

    public long getMemUserId() {
        return memUserId;
    }

    public void setMemUserId(long memUserId) {
        this.memUserId = memUserId;
    }

    public String getCur_prov() {
        return cur_prov;
    }

    public void setCur_prov(String cur_prov) {
        this.cur_prov = cur_prov;
    }

    public String getCur_city() {
        return cur_city;
    }

    public void setCur_city(String cur_city) {
        this.cur_city = cur_city;
    }

    public double getCurLocX() {
        return curLocX;
    }

    public void setCurLocX(double curLocX) {
        this.curLocX = curLocX;
    }

    public double getCurLocY() {
        return curLocY;
    }

    public void setCurLocY(double curLocY) {
        this.curLocY = curLocY;
    }

    public double getPreLocX() {
        return preLocX;
    }

    public void setPreLocX(double preLocX) {
        this.preLocX = preLocX;
    }

    public double getPreLocY() {
        return preLocY;
    }

    public void setPreLocY(double preLocY) {
        this.preLocY = preLocY;
    }
}
