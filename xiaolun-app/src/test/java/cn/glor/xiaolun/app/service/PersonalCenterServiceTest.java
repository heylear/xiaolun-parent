package cn.glor.xiaolun.app.service;

import cn.glor.xiaolun.app.entity.AttachEntity;
import cn.glor.xiaolun.app.excep.PersonalCenterOperationException;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by heylear on 15/10/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:applicationContent.xml"
})
public class PersonalCenterServiceTest {

    final static Logger logger = Logger.getLogger(PersonalCenterServiceTest.class);

    @Autowired
    PersonalCenterService personalCenterService;

    @Ignore
    public void testAddCarPic() throws PersonalCenterOperationException{
        personalCenterService.addCarPic("4323453646", "weixin://421412414", 1);
    }

    @Ignore
    public void testDelCarPic() throws PersonalCenterOperationException{
        personalCenterService.delCarPic(2);
    }

    @Ignore
    public void testGetAttachEntityListByUserId() throws PersonalCenterOperationException{
        List<AttachEntity> attachEntityList = personalCenterService.getAttachEntityListByUserId(1);

        for (AttachEntity attachEntity : attachEntityList){
            logger.debug(attachEntity.getAttachName());
        }
    }

    @Ignore
    public void testAddInteraction() throws PersonalCenterOperationException{
        /*personalCenterService.addInteraction(3,"1");*/
    }

    @Test
    public void testReplyQuestion() throws PersonalCenterOperationException{
       /* personalCenterService.replyQuestion("测试提问",1,0);
        personalCenterService.replyQuestion("测试提问",1,0);
        personalCenterService.replyQuestion("测试提问",1,0);
        personalCenterService.replyQuestion("测试回答",1,1);
        personalCenterService.replyQuestion("测试回答",1,2);
        personalCenterService.replyQuestion("测试回答",1,3);
        personalCenterService.replyQuestion("测试提问",1,0);
        personalCenterService.replyQuestion("测试提问",1,0);*/
    }
}
