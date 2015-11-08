package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import cn.glor.xiaolun.app.repository.MpUserRepository;
import cn.glor.xiaolun.app.repository.SellerAccountRepository;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by caosh on 2015/10/10.
 */
@ParentPackage("wx")
@InterceptorRef("sellerLogin")
public class SellerRegisterLastAction extends BaseAction {

    private String phoneNumber;

//    private String password;
//
//    private String passwordSecond;

    @Autowired
    private SellerAccountRepository sellerAccountRepository;

    @Autowired
    private MpUserRepository mpUserRepository;

    @JSON(serialize = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @RequiredStringValidator(key = "phoneNumber.format")
    @StringLengthFieldValidator(minLength = "11", maxLength = "11", key = "phoneNumber.format")
    @RegexFieldValidator(expression = "1\\d{10}", key = "phoneNumber.format")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    @JSON(serialize = false)
//    public String getPassword() {
//        return password;
//    }
//
//    @RequiredStringValidator(key = "password.required")
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @JSON(serialize = false)
//    public String getPasswordSecond() {
//        return passwordSecond;
//    }
//
//    @FieldExpressionValidator(expression = "password == passwordSecond", key = "password.notConsist")
//    public void setPasswordSecond(String passwordSecond) {
//        this.passwordSecond = passwordSecond;
//    }

    @Override
    public void validate() {
        if (mpUserRepository.getMpUserByPhoneNumber(phoneNumber) != null) {
            addFieldError("phoneNumber", getText("phoneNumber.registered"));
        }
    }

    @Override
    public String execute() throws Exception {
        SellerAccountEntity sellerAccountEntity = (SellerAccountEntity) getLoggedMpUser();
        sellerAccountEntity.setPhoneNumber(phoneNumber);
        sellerAccountRepository.saveOrUpdate(sellerAccountEntity);
        return SUCCESS;
    }
}
