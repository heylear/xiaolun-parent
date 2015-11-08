package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.common.exception.BusinessException;
import cn.glor.xiaolun.app.controller.common.BaseAction;
import cn.glor.xiaolun.app.entity.ConsultOrderEntity;
import cn.glor.xiaolun.app.entity.SellerAccountEntity;
import cn.glor.xiaolun.app.repository.ConsultOrderRepository;
import cn.glor.xiaolun.app.repository.SellerAccountRepository;
import cn.glor.xiaolun.app.service.ConsultOrderService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by caosh on 2015/10/11.
 */
@ParentPackage("wx")
@InterceptorRef("sellerLogin")
public class SellerConsultAction extends BaseAction {

    private Long consultId;

    private List<ConsultOrderEntity> orderList;

    @Autowired
    private ConsultOrderService consultOrderService;

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

    @Action("seller-consult-catch-list")
    public String catchList() {
        SellerAccountEntity sellerAccountEntity = (SellerAccountEntity) getLoggedMpUser();
        orderList = consultOrderRepository.findCatchableOrderListBySeller(sellerAccountEntity);
        return SUCCESS;
    }

    @Action("seller-consult-catch")
    public String sellerConsultCatch() throws BusinessException {
        consultOrderService.catchBySeller(getLoggedMpUser().getEntityId(), consultId);
        return SUCCESS;
    }

    @Action("seller-customer-list")
    public String customerList() {
        orderList = consultOrderRepository.findBySeller(getLoggedMpUser().getEntityId());
        for (ConsultOrderEntity o : orderList) {
            o.setSellerList(null);
        }
        return SUCCESS;
    }
}
