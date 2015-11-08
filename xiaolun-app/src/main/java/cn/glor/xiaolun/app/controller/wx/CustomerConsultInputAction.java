package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.controller.common.SessionKeys;
import cn.glor.xiaolun.app.controller.common.beans.CityVo;
import cn.glor.xiaolun.app.entity.ConsultOrderEntity;
import cn.glor.xiaolun.app.entity.RegionEntity;
import cn.glor.xiaolun.app.entity.SubBrandEntity;
import cn.glor.xiaolun.app.service.ConsultOrderService;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by caosh on 2015/10/11.
 */
@ParentPackage("wx")
@InterceptorRef("wx")
public class CustomerConsultInputAction extends BaseAction {

    private CityVo city;

    private Integer wantedConsultants;

    private List<SubBrandEntity> subBrandList;

    private String description;

    private boolean needRegister = false;

    @Autowired
    private ConsultOrderService consultOrderService;

    @JSON(serialize = false)
    public CityVo getCity() {
        return city;
    }

    @FieldExpressionValidator(expression = "city.cityId != null", key = "cityId.required")
    public void setCity(CityVo city) {
        this.city = city;
    }

    @JSON(serialize = false)
    public Integer getWantedConsultants() {
        return wantedConsultants;
    }

    @RequiredFieldValidator(key = "wantedConsultants.required")
    @IntRangeFieldValidator(min = "1", max = "10", key = "wantedConsultants.range")
    public void setWantedConsultants(Integer wantedConsultants) {
        this.wantedConsultants = wantedConsultants;
    }

    @JSON(serialize = false)
    public List<SubBrandEntity> getSubBrandList() {
        return subBrandList;
    }

    @FieldExpressionValidator(expression = "subBrandList[0].entityId != null", key = "wantedBrand.required")
    public void setSubBrandList(List<SubBrandEntity> subBrandList) {
        this.subBrandList = subBrandList;
    }

    @JSON(serialize = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNeedRegister() {
        return needRegister;
    }

    private ConsultOrderEntity buildConsultOrder() {
        ConsultOrderEntity consult = new ConsultOrderEntity();
        consult.setCity(new RegionEntity());
        consult.getCity().setEntityId(city.getCityId());
        consult.setWantedConsultants(wantedConsultants);
        consult.setDescription(description);

        if (subBrandList != null) {
            List<SubBrandEntity> distinctExpertBrandList = new ArrayList<>();
            for (SubBrandEntity subBrand : subBrandList) {
                if (Collections.binarySearch(distinctExpertBrandList, subBrand) != 0) {
                    distinctExpertBrandList.add(subBrand);
                }
            }
            consult.setSubBrandList(distinctExpertBrandList);
        }
        consult.setCustomer(getLoggedMpUser());
        return consult;
    }

    @Override
    public String execute() throws Exception {
        ConsultOrderEntity consultOrder = buildConsultOrder();
        if (getLoggedMpUser().getPhoneNumber() != null) {
            consultOrderService.create(consultOrder);
            needRegister = false;
        } else {
            putSession(SessionKeys.SESSION_KEY_UNSAVED_CONSULT_ORDER, consultOrder);
            needRegister = true;
        }
        return SUCCESS;
    }

    @Action("customer-consult-input-input")
    public String input() {
        return INPUT;
    }
}
