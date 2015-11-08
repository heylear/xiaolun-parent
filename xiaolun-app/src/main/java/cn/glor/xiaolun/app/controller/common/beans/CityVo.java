package cn.glor.xiaolun.app.controller.common.beans;

/**
 * Created by caosh on 2015/11/8.
 */
public class CityVo {

    private Long cityId;

    private String name;

    public CityVo() {
    }

    public CityVo(Long cityId, String name) {
        this.cityId = cityId;
        this.name = name;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
