package tool.pinyin;

import cn.glor.xiaolun.app.entity.SubBrandEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caosh on 2015/10/18.
 */
@Repository
public class SubBrandPinYin extends HibernateDaoSupport {

    @Autowired
    public SubBrandPinYin(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContent.xml");
        applicationContext.getBean(SubBrandPinYin.class).execute();
    }

    public void execute() {
        ChineseToPinYin pinYin = new ChineseToPinYin();

        List<SubBrandEntity> list = getHibernateTemplate().find("from SubBrandEntity");
        for (SubBrandEntity subBrand : list) {
            subBrand.setPinyinAbbr(pinYin.getPYString(subBrand.getName()).toLowerCase());
            getHibernateTemplate().update(subBrand);
        }
    }
}
