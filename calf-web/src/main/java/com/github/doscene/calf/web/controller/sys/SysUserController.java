package com.github.doscene.calf.web.controller.sys;

import com.alibaba.fastjson.JSON;
import com.github.doscene.calf.common.controller.BaseController;
import com.github.doscene.calf.common.dto.RestfulResult;
import com.github.doscene.calf.common.entity.SysPermission;
import com.github.doscene.calf.common.entity.SysUser;
import com.github.doscene.calf.security.UserUtils;
import com.github.doscene.calf.service.security.RealmService;
import com.github.doscene.calf.service.sys.SysPermissionService;
import com.github.doscene.calf.service.sys.SysUserService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * <h1>com.github.doscene.calf.web.controller.sys</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Controller
@RequestMapping("sys/user")
public class SysUserController extends BaseController<SysUser> {
    @Autowired
    private final SysUserService userService;
    private final RealmService realmService;
    private final SysPermissionService sysPermissionService;

    public SysUserController(SysUserService userService, RealmService realmService, SysPermissionService sysPermissionService) {
        this.userService = userService;
        this.realmService = realmService;
        this.sysPermissionService = sysPermissionService;
    }

    /**
     * 用户管理分页查询 页面
     *
     * @param sysUser 用户
     * @return 视图
     */
    @RequestMapping("toUserList")
    public String toUserList(@ModelAttribute("sysUser") SysUser sysUser) {
        return "sys/user/user_list";
    }

    /**
     * 用户管理-分页查询数据
     *
     * @param sysUser  实体
     * @param pageNum  页码
     * @param pageSize 页长度
     * @return 响应
     */
    @RequestMapping("getUserList")
    @ResponseBody
    public RestfulResult userList(SysUser sysUser, Integer pageNum, Integer pageSize) {
        return execPage(userService.getUserList(sysUser, pageNum, pageSize));
    }

    /**
     * 用户管理-编辑
     *
     * @param sysUser 用户
     * @param mv      视图
     * @return 视图
     */
    @RequestMapping("toUserEdit")
    public ModelAndView toUserEdit(SysUser sysUser, ModelAndView mv) {
        mv.setViewName("sys/user/user_edit");
        mv.addObject("sysUser", userService.getUserByLoginName(sysUser.getLoginName()));
        return mv;
    }

    /**
     * 用户管理-编辑
     *
     * @param sysUser 用户
     * @return 视图
     */
    @RequestMapping("userEdit")
    @ResponseBody
    public RestfulResult userEdit(SysUser sysUser) {
        return execBoolean(userService.editSysUser(sysUser));
    }

    /**
     * 保存用户界面
     *
     * @param mv 视图模型
     * @return 视图
     */
    @RequestMapping("toUserSave")
    public ModelAndView toUserSave(ModelAndView mv) {
        mv.setViewName("sys/user/user_save");
        return mv;
    }

    /**
     * 保存用户
     *
     * @param sysUser 用户
     * @param request 请求
     * @return 响应
     */
    @ResponseBody
    @PostMapping("userSave")
    public RestfulResult userEdit(SysUser sysUser, HttpServletRequest request) {
        sysUser.setCreateIp(request.getRemoteAddr());
        sysUser.setCreateBy(UserUtils.getUserName());
        return execBoolean(userService.register(sysUser));
    }

    /**
     * 查看用户
     *
     * @param sysUser 用户
     * @param mv      视图
     * @return 视图
     */
    @RequestMapping("toUserView")
    public ModelAndView toUserView(SysUser sysUser, ModelAndView mv) {
        mv.setViewName("sys/user/user_view");
        SysUser r = userService.getUserByLoginName(sysUser.getLoginName());
        //  r.setGender(CodeUtils.getSubCode("GENDER_TYPE", r.getGender()));
        mv.addObject("sysUser", r);
        return mv;
    }

    /**
     * 删除用户
     *
     * @return 响应
     */
    @RequestMapping("deleteUser")
    @ResponseBody
    public RestfulResult deleteUser(SysUser sysUser) {
        return execBoolean(userService.deleteSysUser(sysUser));
    }

    /**
     * 用户授权
     *
     * @return 响应
     */
    @RequestMapping("toUserGrant")
    public ModelAndView toUserGrant(SysUser sysUser, ModelAndView mv) {
        mv.setViewName("sys/user/user_grant");
        return mv;
    }

    /**
     * 获取用户权限列表
     *
     * @return 响应
     */
    @ResponseBody
    @RequestMapping("getUserPermissions")
    public RestfulResult getUserPermissions(String parentId) {
        if (Strings.isNullOrEmpty(parentId)) {
            parentId = "1";
        }
        List<SysPermission> permissions = sysPermissionService.getSysPermissionByParentId(parentId);
        Set<String> userP = realmService.getUserPermissionString(UserUtils.getUserName());
        userP.add("menu");
        HashMap<String,Object> result = new HashMap<>();
        result.put("allPermissions",permissions);
        result.put("userPermissions",userP);
        return RestfulResult.ok(result);
    }


}
