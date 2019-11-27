package com.github.doscene.calf.web.controller.security;

import com.github.doscene.calf.common.controller.BaseController;
import com.github.doscene.calf.service.security.PermissionService;
import com.github.doscene.calf.service.sys.SysUserService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <h1>登录action</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
@Controller
@RequestMapping("login")
public class LoginController extends BaseController<Object> {
    private final DefaultKaptcha captchaProducer;
    private final PermissionService permissionService;
    private final SysUserService sysUserService;

    @Autowired
    public LoginController(DefaultKaptcha captchaProducer, PermissionService permissionService, SysUserService sysUserService) {
        this.captchaProducer = captchaProducer;
        this.permissionService = permissionService;
        this.sysUserService = sysUserService;
    }

    /**
     * 登录界面
     *
     * @return 视图
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String login() {
        return "login";
    }


    /**
     * 未授权页面
     *
     * @return 未授权
     */
    @GetMapping("unauth")
    public String unauth() {
        return "unauth";
    }

    /**
     * 验证码
     *
     * @param response 响应
     * @param session  会话
     * @throws IOException 异常
     */
    @RequestMapping("captcha")
    public void captcha(HttpServletResponse response, HttpSession session) throws IOException {
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        //将验证码存到session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        //write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

}
