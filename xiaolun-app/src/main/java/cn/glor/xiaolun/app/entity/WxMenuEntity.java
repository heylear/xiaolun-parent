package cn.glor.xiaolun.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by heylear on 15/10/31.
 */
@Entity
@Table(name = "tm_wx_menu", schema = "", catalog = "xlap")
public class WxMenuEntity extends BaseEntity {

    @Column(name = "menu_name")
    String menuName;

    @Column(name = "menu_type")
    String menuType;

    @Column(name = "menu_key")
    String menuKey;

    @Column(name = "menu_url")
    String menuUrl;

    @Column(name = "parent_id")
    long parentId;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
