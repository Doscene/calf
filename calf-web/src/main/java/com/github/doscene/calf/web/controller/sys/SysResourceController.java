package com.github.doscene.calf.web.controller.sys;

import com.github.doscene.calf.common.controller.BaseController;
import com.github.doscene.calf.common.dto.RestfulResult;
import com.github.doscene.calf.common.entity.SysResource;
import com.github.doscene.calf.security.UserUtils;
import com.github.doscene.calf.service.sys.SysResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>com.github.doscene.calf.web.controller.sys</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Controller
@RequestMapping("sys/resource")
public class SysResourceController extends BaseController<SysResource> {
    private final SysResourceService sysResourceService;

    public SysResourceController(SysResourceService sysResourceService) {
        this.sysResourceService = sysResourceService;
    }

    /**
     * 资源列表
     *
     * @param sysResource 资源参数
     * @return 视图
     */
    @RequestMapping("toResourceList")
    public String toResourceList(@ModelAttribute("resource") SysResource sysResource) {
        return "sys/resource/resource_list";
    }

    /**
     * 资源列表数据
     *
     * @param sysResource 资源参数
     * @return 数据
     */
    @ResponseBody
    @RequestMapping("resourceList")
    public RestfulResult resourceList(SysResource sysResource, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return execPage(sysResourceService.getResourcePageList(sysResource, pageNum, pageSize));
    }

    @RequestMapping("toSaveResource")
    public ModelAndView toSaveResource(ModelAndView mv) {
        mv.setViewName("sys/resource/resource_save");
        return mv;
    }

    @ResponseBody
    @RequestMapping("saveResource")
    public RestfulResult saveResource(SysResource sysResource, HttpServletRequest request) {
        sysResource.setCreateBy(UserUtils.getUserName());
        sysResource.setCreateIp(request.getRemoteAddr());
        return execBoolean(sysResourceService.saveResource(sysResource));
    }

    @RequestMapping("toEditResource")
    public ModelAndView toEditResource(String pid, ModelAndView mv) {
        mv.addObject("resource", sysResourceService.getResourceByPid(pid));
        mv.setViewName("sys/resource/resource_edit");
        return mv;
    }

    @ResponseBody
    @RequestMapping("editResource")
    public RestfulResult editResource(SysResource sysResource) {
        return execBoolean(sysResourceService.editResource(sysResource));
    }

    @RequestMapping("toResourceView")
    public ModelAndView toResourceView(String pid, ModelAndView mv) {
        mv.addObject("resource", sysResourceService.getResourceByPid(pid));
        mv.setViewName("sys/resource/resource_view");
        return mv;
    }

    @ResponseBody
    @RequestMapping("deleteResource")
    public RestfulResult deleteResource(String pid) {
        return execBoolean(sysResourceService.deleteResourceByPid(pid));
    }
}
