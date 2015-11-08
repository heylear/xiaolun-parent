package cn.glor.xiaolun.app.repository;

import cn.glor.xiaolun.app.entity.SellerAccountEntity;

/**
 * Created by caosh on 2015/10/10.
 */
public interface SellerAccountRepository extends BaseRepository {

    void transformMpUserToSeller(SellerAccountEntity sellerAccount);

    @Deprecated
    SellerAccountEntity get(Long id);

    @Deprecated
    SellerAccountEntity getByWxUnionId(String unionId);

    @Deprecated
    SellerAccountEntity findByPhoneNumber(String phoneNumber);
}
