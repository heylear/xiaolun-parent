package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.controller.common.SessionKeys;
import cn.glor.xiaolun.app.entity.ConsultOrderEntity;
import cn.glor.xiaolun.app.entity.MpUserEntity;
import cn.glor.xiaolun.app.repository.MpUserRepository;
import cn.glor.xiaolun.app.service.ConsultOrderService;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by caosh on 2015/10/6.
 */
@ParentPackage("wx")
@InterceptorRef("wx")
public class CustomerRegisterAction extends BaseAction {

	private String phoneNumber;

//	private String password;

//	private String passwordSecond;

	private String realName;

	@Autowired
	private MpUserRepository mpUserRepository;

	@Autowired
	private ConsultOrderService consultOrderService;

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
//
//	@JSON(serialize = false)
//	public String getPassword() {
//		return password;
//	}
//
//	@RequiredStringValidator(key = "password.required")
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	@JSON(serialize = false)
//	public String getPasswordSecond() {
//		return passwordSecond;
//	}
//
//	@FieldExpressionValidator(expression = "password == passwordSecond", key = "password.notConsist")
//	public void setPasswordSecond(String passwordSecond) {
//		this.passwordSecond = passwordSecond;
//	}

	@JSON(serialize = false)
	public String getRealName() {
		return realName;
	}

	@RequiredStringValidator(key = "realName.required")
	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Action("customer-register-input")
	public String input() {
		return INPUT;
	}

	@Override
	public void validate() {
		if (mpUserRepository.getMpUserByPhoneNumber(phoneNumber) != null) {
			addFieldError("phoneNumber", getText("phoneNumber.registered"));
		}
	}

	@Override
	public String execute() throws Exception {
		MpUserEntity mpUserEntity = getLoggedMpUser();
		mpUserEntity.setPhoneNumber(phoneNumber);
		mpUserEntity.setRealName(realName);

		mpUserRepository.saveOrUpdate(mpUserEntity);

		if (containsSessionKey(SessionKeys.SESSION_KEY_UNSAVED_CONSULT_ORDER)) {
			ConsultOrderEntity consultOrder = (ConsultOrderEntity) getSession(SessionKeys.SESSION_KEY_UNSAVED_CONSULT_ORDER);
			consultOrder.setCustomer(mpUserEntity);
			consultOrderService.create(consultOrder);
		}

		return SUCCESS;
	}
}
