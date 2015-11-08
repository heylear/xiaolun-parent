package cn.glor.xiaolun.app.controller.wx;

import cn.glor.xiaolun.app.entity.SubBrandEntity;
import cn.glor.xiaolun.app.repository.SubBrandRepository;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Created by caosh on 2015/10/18.
 */
@ParentPackage("wx")
public class SubBrandAction extends ActionSupport {

    private String key;

    private List<SubBrandEntity> subBrandList;

    @Autowired
    private SubBrandRepository subBrandRepository;

    public void setKey(String key) {
        this.key = key;
    }

    public List<SubBrandEntity> getSubBrandList() {
        return subBrandList;
    }

    @Action("sub-brand-search")
    public String search() {
        if (!key.isEmpty()) {
            subBrandList = subBrandRepository.searchByPinYin(key);
        } else {
            subBrandList = Collections.emptyList();
        }
        return SUCCESS;
    }
}
