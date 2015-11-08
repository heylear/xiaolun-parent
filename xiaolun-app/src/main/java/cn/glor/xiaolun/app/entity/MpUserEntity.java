package cn.glor.xiaolun.app.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by heylear on 15/11/1.
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "tt_mp_user", schema = "", catalog = "xlap")
public class MpUserEntity extends BaseEntity{
    @Column(name = "is_subscribe")
    protected String subscribe;
    @Column(name = "openid")
    protected String openId;
    @Column(name = "nickname")
    protected String nickname;
    @Column(name = "sex")
    protected String sex;
    @Column(name = "language")
    protected String language;
    @Column(name = "city")
    protected String city;
    @Column(name = "province")
    protected String province;
    @Column(name = "country")
    protected String country;
    @Column(name = "headimgurl")
    protected String headImgUrl;
    @Column(name = "subscribe_time")
    protected Date subscribeTime;
    @Column(name = "unionid")
    protected String unionId;
    @Lob
    @Column(name = "remark",columnDefinition = "TEXT")
    protected String remark;
    @Column(name = "groupid")
    protected String groupId;

    @Column(name = "phone_number")
    protected String phoneNumber;

    @Column(name = "real_name")
    protected String realName;

    @Column(name = "password_digest")
    protected String passwordDigest;

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "MpUserEntity{" +
                "subscribe='" + subscribe + '\'' +
                ", openId='" + openId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", language='" + language + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", subscribeTime=" + subscribeTime +
                ", unionId='" + unionId + '\'' +
                ", remark='" + remark + '\'' +
                ", groupId='" + groupId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", realName='" + realName + '\'' +
                ", passwordDigest='" + passwordDigest + '\'' +
                '}';
    }
}
