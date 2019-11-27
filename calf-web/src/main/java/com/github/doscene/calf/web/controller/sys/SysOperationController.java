package com.github.doscene.calf.web.controller.sys;

import com.github.doscene.calf.common.entity.SysResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <h1>com.github.doscene.calf.web.controller.sys</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Controller
@RequestMapping("sys/operation")
public class SysOperationController {
    @RequestMapping("pageList")
    public ModelAndView pageList(ModelAndView mv, SysResource sysResource) {
        mv.setViewName("sys/operation/operation_list");
        mv.addObject("sysResource", sysResource);
        return mv;
    }
}
