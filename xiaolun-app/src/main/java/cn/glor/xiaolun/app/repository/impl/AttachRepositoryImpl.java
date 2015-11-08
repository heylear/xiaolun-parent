package cn.glor.xiaolun.app.repository.impl;

import cn.glor.xiaolun.app.entity.AttachEntity;
import cn.glor.xiaolun.app.repository.AttachRepository;
import cn.glor.xiaolun.app.service.WxMpServiceHolder;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.activation.FileTypeMap;
import java.io.File;
import java.util.List;

/**
 * Created by heylear on 2015/10/24.
 */
@Repository
public class AttachRepositoryImpl extends AbstractEntityRepository implements AttachRepository {

    Logger logger = Logger.getLogger(AttachRepositoryImpl.class);

    WxMpServiceHolder wxMpServiceHolder;

    @Autowired
    public AttachRepositoryImpl(SessionFactory sessionFactory, WxMpServiceHolder wxMpServiceHolder) {
        super(sessionFactory);
        this.wxMpServiceHolder = wxMpServiceHolder;
    }

    @Override
    public List<AttachEntity> getAttachEntityListByUserId(long userId) {
        String sql = "SELECT a.*" +
                " FROM tt_attach a INNER JOIN tr_car_pic b ON a.entity_id = b.attach_id" +
                " WHERE b.mem_user_id = ? and a.entity_status = '1' and b.entity_status = '1'";
        return queryEntityListBySQL(sql, AttachEntity.class, userId);
    }

    @Override
    public AttachEntity updateOrLoadAttachEntityById(long attachId) {
        AttachEntity attachEntity = queryUniqueEntity(AttachEntity.class, Restrictions.eq("entityId",attachId));
        if (attachEntity.getAttachNote() != null){
            try {
                File file = wxMpServiceHolder.getWxMpService().mediaDownload(attachEntity.getAttachNote());
                attachEntity.setAttachName(file.getName());
                attachEntity.setAttachType(FileTypeMap.getDefaultFileTypeMap().getContentType(attachEntity.getAttachName()));
                attachEntity.setAttachNote(null);
                attachEntity.setAttachUri(file.getAbsolutePath());
                super.saveOrUpdate(attachEntity);
            } catch (WxErrorException e) {
                logger.debug(e.getMessage(),e);
            }
        }
        return attachEntity;
    }
}
