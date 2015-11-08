package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.controller.common.beans.CityVo;
import cn.glor.xiaolun.app.entity.RegionEntity;
import cn.glor.xiaolun.app.repository.RegionRepository;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caosh on 2015/10/24.
 */
@ParentPackage("wx")
public class CityAction extends ActionSupport {

    @Autowired
    private RegionRepository regionRepository;

    private List<CityVo> cityList;

    public List<CityVo> getCityList() {
        return cityList;
    }

    @Action("city-all")
    public String list() {
        cityList = new ArrayList<>();
        List<RegionEntity> regionEntities = regionRepository.allOpened();
        for (RegionEntity r : regionEntities) {
            cityList.add(new CityVo(r.getEntityId(), r.getRegionName()));
        }
        return SUCCESS;
    }
}
