package com.github.doscene.calf.web.controller.security;

import com.github.doscene.calf.common.controller.BaseController;
import com.github.doscene.calf.common.dto.RestfulResult;
import com.github.doscene.calf.common.entity.SysPermission;
import com.github.doscene.calf.security.UserUtils;
import com.github.doscene.calf.service.security.PermissionService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.h2.engine.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <h1>com.github.doscene.calf.web.controller.security</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Controller
@RequestMapping("index")
public class IndexController extends BaseController {
    private final PermissionService permissionService;
    private final TaskService taskService;

    public IndexController(PermissionService permissionService, TaskService taskService) {
        this.permissionService = permissionService;
        this.taskService = taskService;
    }

    /**
     * 主页
     *
     * @param mv 视图
     * @return 视图
     */
    @GetMapping
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("index");
        SysPermission permission = new SysPermission();
        permission.setParentId("2");
        long count = taskService.createTaskQuery().taskAssignee(UserUtils.getUserName()).count();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(UserUtils.getUserName()).listPage(0, 3);

        mv.addObject("menu", permissionService.getPermissionByParentId(permission));
        mv.addObject("taskCount", count);
        mv.addObject("tasks", tasks);
        return mv;
    }

    /**
     * 主页读取权限列表
     *
     * @param permission 权限
     * @return 权限列表
     */
    @ResponseBody
    @RequestMapping("getPermission")
    public RestfulResult getPermission(SysPermission permission) {
        return exec(permissionService.getPermissionByParentId(permission));
    }
}
