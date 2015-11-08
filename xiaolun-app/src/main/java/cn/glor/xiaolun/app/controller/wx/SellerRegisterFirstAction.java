package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.controller.common.beans.CityVo;
import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.entity.RegionEntity;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import cn.glor.xiaolun.app.entity.SubBrandEntity;
import cn.glor.xiaolun.app.repository.SellerAccountRepository;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by caosh on 2015/10/10.
 */
@ParentPackage("wx")
@InterceptorRef("wx")
public class SellerRegisterFirstAction extends BaseAction {

    private String realName;

    private CityVo city;

    private Date birthday;

    private String employer;

    private String workPhone;

    private SubBrandEntity servedBrand;

    private List<SubBrandEntity> expertBrandList;

    private String description;
    @Autowired
    private SellerAccountRepository sellerAccountRepository;

    @JSON(serialize = false)
    public String getRealName() {
        return realName;
    }

    @RequiredStringValidator(key = "realName.required")
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @JSON(serialize = false)
    public CityVo getCity() {
        return city;
    }

    @FieldExpressionValidator(expression = "city.cityId != null", key = "cityId.required")
    public void setCity(CityVo city) {
        this.city = city;
    }

    @JSON(serialize = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @JSON(serialize = false)
    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    @JSON(serialize = false)
    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    @JSON(serialize = false)
    public SubBrandEntity getServedBrand() {
        return servedBrand;
    }

    @FieldExpressionValidator(expression = "servedBrand.entityId != null", key = "servedBrand.required")
    public void setServedBrand(SubBrandEntity servedBrand) {
        this.servedBrand = servedBrand;
    }

    @JSON(serialize = false)
    public List<SubBrandEntity> getExpertBrandList() {
        return expertBrandList;
    }

    public void setExpertBrandList(List<SubBrandEntity> expertBrandList) {
        this.expertBrandList = expertBrandList;
    }

    @JSON(serialize = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private SellerAccountEntity buildSellerAccountEntity() {
        SellerAccountEntity seller = new SellerAccountEntity();
        seller.setRealName(realName);
        seller.setBirthday(birthday);
        seller.setRegion(new RegionEntity());
        seller.getRegion().setEntityId(city.getCityId());
        seller.setEmployer(employer);
        seller.setWorkPhone(workPhone);
        seller.setServedBrand(servedBrand);

        if (expertBrandList != null) {
            List<SubBrandEntity> distinctExpertBrandList = new ArrayList<>();
            for (SubBrandEntity subBrand : expertBrandList) {
                if (Collections.binarySearch(distinctExpertBrandList, subBrand) != 0) {
                    distinctExpertBrandList.add(subBrand);
                }
            }
            seller.setExpertBrandList(distinctExpertBrandList);
        }
        seller.setDescription(description);
        return seller;
    }

    @Override
    public void validate() {
        if (getLoggedMpUser() instanceof SellerAccountEntity) {
            addFieldError("", getText("seller.registered"));
        }
    }

    public String execute() throws Exception {
        MpUserEntity mpUser = getLoggedMpUser();

        mpUser.setRealName(realName);
        sellerAccountRepository.saveOrUpdate(mpUser);

        SellerAccountEntity seller = buildSellerAccountEntity();
        seller.setEntityId(mpUser.getEntityId());
        sellerAccountRepository.transformMpUserToSeller(seller);

        seller = sellerAccountRepository.get(seller.getEntityId());
        loginSeller(seller);

        return SUCCESS;
    }
}
