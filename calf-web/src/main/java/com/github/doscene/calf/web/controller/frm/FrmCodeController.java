package com.github.doscene.calf.web.controller.frm;

import com.github.doscene.calf.common.controller.BaseController;
import com.github.doscene.calf.common.dto.RestfulResult;
import com.github.doscene.calf.common.entity.FrmCode;
import com.github.doscene.calf.security.UserUtils;
import com.github.doscene.calf.service.frm.FrmCodeService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * <h1>com.github.doscene.calf.web.controller.frmcode</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Controller
@RequestMapping("frm/code")
public class FrmCodeController extends BaseController<FrmCode> {

    private final FrmCodeService frmCodeService;

    public FrmCodeController(FrmCodeService frmCodeService) {
        this.frmCodeService = frmCodeService;
    }

    @RequestMapping("toCodeList")
    public String toFrmCodeList(@ModelAttribute FrmCode frmCode) {
        return "frm/code/code_list";
    }

    @RequestMapping("codeList")
    @ResponseBody
    public RestfulResult frmCodeList(FrmCode frmCode) {
        if (Strings.isNullOrEmpty(frmCode.getParentId())) {
            frmCode.setParentId("1");
        }
        return exec(frmCodeService.getFrmCodeByParent(frmCode.getParentId()));
    }

    @RequestMapping("frmCode")
    @ResponseBody
    public RestfulResult frmCode(FrmCode frmCode) {
        return exec(frmCodeService.getFrmCodeByPid(frmCode.getPid()));
    }

    @RequestMapping("toEditFrmCode")
    public ModelAndView toEditFrmCode(FrmCode frmCode, ModelAndView mv) {
        mv.setViewName("frm/code/code_edit");
        mv.addObject("frmCode", frmCodeService.getFrmCodeByPid(frmCode.getPid()));
        return mv;
    }

    @RequestMapping("editFrmCode")
    @ResponseBody
    public RestfulResult editFrmCode(FrmCode frmCode, ModelAndView mv) {
        mv.setViewName("frm/code/code_edit");
        mv.addObject("frmCode", frmCodeService.editFrmCode(frmCode));
        return RestfulResult.ok();
    }

    @RequestMapping("toViewFrmCode")
    public ModelAndView toViewFrmCode(FrmCode frmCode, ModelAndView mv) {
        mv.setViewName("frm/code/code_view");
        mv.addObject("frmCode", frmCodeService.getFrmCodeByPid(frmCode.getPid()));
        return mv;
    }

    @RequestMapping("toSaveFrmCode")
    public ModelAndView toSaveFrmCode(@ModelAttribute("frmCode") FrmCode frmCode, ModelAndView mv) {
        mv.setViewName("frm/code/code_save");
        //mv.addObject("frmCode", frmCodeService.getFrmCodeByPid(frmCode.getPid()));
        return mv;
    }

    @RequestMapping("saveFrmCode")
    @ResponseBody
    public RestfulResult saveFrmCode(@ModelAttribute("frmCode") FrmCode frmCode, ModelAndView mv) {
        mv.setViewName("frm/code/code_save");
        frmCode.setCreateBy(UserUtils.getUserName());
        //mv.addObject("frmCode", frmCodeService.getFrmCodeByPid(frmCode.getPid()));
        return execBoolean(frmCodeService.saveFrmCode(frmCode));
    }
}
