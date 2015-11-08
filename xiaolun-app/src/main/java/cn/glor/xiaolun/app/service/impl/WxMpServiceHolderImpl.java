package cn.glor.xiaolun.app.service.impl;

import cn.glor.xiaolun.app.entity.WxMenuEntity;
import cn.glor.xiaolun.app.repository.ConfigRepository;
import cn.glor.xiaolun.app.repository.WxBasicRepository;
import cn.glor.xiaolun.app.service.WxMpServiceHolder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.URIUtil;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caosh on 2015/10/7.
 */
@Service
public class WxMpServiceHolderImpl implements WxMpServiceHolder {
    Logger logger = Logger.getLogger(WxMpServiceHolderImpl.class);
    private WxMpService wxMpService;

    @Autowired
    public WxMpServiceHolderImpl(ConfigRepository configRepository, WxBasicRepository wxBasicRepository) {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(configRepository.getValue("wx.appID"));
        configStorage.setSecret(configRepository.getValue("wx.appSecret"));
        configStorage.setOauth2redirectUri(configRepository.getValue("wx.oauth2RedirectUrl"));
        configStorage.setToken(configRepository.getValue("wx.token"));

        File file = new File(configRepository.getValue("file.rootDir"));

        if(!file.exists()){
            file.mkdirs();
        }

        configStorage.setTmpDirFile(file);

        wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);

        refreshMenu(wxBasicRepository);
    }

    public WxMpService getWxMpService() {
        return wxMpService;
    }

    protected void refreshMenu(WxBasicRepository wxBasicRepository){
        WxMenu wxMenu = new WxMenu();
        List<WxMenuEntity> wxMenuEntityList = wxBasicRepository.getWxMenuEntityList();
        List<WxMenu.WxMenuButton> buttons = new ArrayList<>();
        for (WxMenuEntity wxMenuEntity: wxMenuEntityList){
            if (wxMenuEntity.getParentId() == 0){
                WxMenu.WxMenuButton pButton = newButton(wxMenuEntity);
                List<WxMenu.WxMenuButton> subButtons = new ArrayList<>();
                for (WxMenuEntity menu: wxMenuEntityList){
                    if (menu.getParentId() == wxMenuEntity.getEntityId()){
                        subButtons.add(newButton(menu));
                    }
                }
                if(subButtons.size()>0){
                    pButton.setSubButtons(subButtons);
                }
                buttons.add(pButton);
            }
        }
        wxMenu.setButtons(buttons);

        logger.debug("menuString: \n" + wxMenu.toJson());

        try {
            wxMpService.menuDelete();
            wxMpService.menuCreate(wxMenu);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    protected WxMenu.WxMenuButton newButton(WxMenuEntity wxMenuEntity){
        WxMenu.WxMenuButton button = new WxMenu.WxMenuButton();
        button.setName(wxMenuEntity.getMenuName());
        button.setKey(wxMenuEntity.getMenuKey());
        button.setType(wxMenuEntity.getMenuType());
        button.setUrl(wxMpService.oauth2buildAuthorizationUrl(WxConsts.OAUTH2_SCOPE_USER_INFO, wxMenuEntity.getMenuUrl()));
        return button;
    }
}
