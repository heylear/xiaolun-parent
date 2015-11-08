package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.entity.ConsultOrderEntity;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import cn.glor.xiaolun.app.repository.ConsultOrderRepository;
import cn.glor.xiaolun.app.repository.SellerAccountRepository;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by caosh on 2015/10/7.
 */
@ParentPackage("wx")
@InterceptorRef("customerLogin")
public class CustomerConsultAction extends BaseAction {

    private Long consultId;

    private List<ConsultOrderEntity> orderList;

    private ConsultOrderEntity detail;

    private List<SellerAccountEntity> sellerList;

    private SellerAccountEntity seller;

    @Autowired
    private ConsultOrderRepository consultOrderRepository;

    @Autowired
    private SellerAccountRepository sellerAccountRepository;

    public void setConsultId(Long consultId) {
        this.consultId = consultId;
    }

    public List<ConsultOrderEntity> getOrderList() {
        return orderList;
    }

    public ConsultOrderEntity getDetail() {
        return detail;
    }

    public List<SellerAccountEntity> getSellerList() {
        return sellerList;
    }

    public SellerAccountEntity getSeller() {
        return seller;
    }

    public void setSeller(SellerAccountEntity seller) {
        this.seller = seller;
    }

    @Action("customer-consult-list")
    public String list() {
        orderList = consultOrderRepository.findByCustomer(getLoggedMpUser().getEntityId());
        for (ConsultOrderEntity consult : orderList) {
            consult.setCustomer(null);
            consult.setSellerList(null);
            consult.setSubBrandList(null);
            consult.setCity(null);
            consult.setDescription(null);
        }
        return SUCCESS;
    }

    @Action("customer-consult-detail")
    public String detail() {
        detail = consultOrderRepository.get(consultId);
        detail.setSellerList(null);
        return SUCCESS;
    }

    @Action("customer-seller-list")
    public String sellerList() {
        sellerList = consultOrderRepository.findSellerListByCustomer(getLoggedMpUser().getEntityId());
        return SUCCESS;
    }

    @Action("customer-seller-detail")
    public String sellerDetail() {
        seller = sellerAccountRepository.get(seller.getEntityId());
        return SUCCESS;
    }
}
