package cn.glor.xiaolun.app.dto;

import java.io.Serializable;

/**
 * Created by caosh on 2015/11/4.
 */
public class ConsultSellerDto implements Serializable {

    private Long consultId;

    private Long sellerId;

    public ConsultSellerDto(Long consultId, Long sellerId) {
        this.consultId = consultId;
        this.sellerId = sellerId;
    }

    public Long getConsultId() {
        return consultId;
    }

    public void setConsultId(Long consultId) {
        this.consultId = consultId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "ConsultSellerDto{" +
                "consultId=" + consultId +
                ", sellerId=" + sellerId +
                '}';
    }
}
