package the.beacon.emsvue.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import the.beacon.emsvue.entity.User;
import the.beacon.emsvue.service.UserService;
import the.beacon.emsvue.utils.VerifyCodeUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TheBeacon
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    // 生成验证码图片
    @GetMapping("getImage")
    public String getImageCode(HttpServletRequest request) throws IOException {
        // 使用工具类生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        // 前后端分离不考虑session，因此存入servletContext作用域
        request.getServletContext().setAttribute("code", code);
        // 将图片转为base64输出
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(120, 30, byteArrayOutputStream, code);
        return "data:image/png;base64,"+Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
    }

    @Autowired
    private UserService userService;

    // 用户注册方法
    @PostMapping("register")
    public Map<String, Object> register(@RequestBody User user, String code, HttpServletRequest request) {  // axios提交是json，使用requestbody生成对象
        log.info("用户信息：[{}]", user.toString());
        log.info("验证码：[{}]", code);

        Map<String, Object> map = new HashMap<>();
        // 调用业务方法
        try {
            String t_code = (String) request.getServletContext().getAttribute("code");
            if (t_code.equalsIgnoreCase(code)) {
                userService.register(user);
                map.put("state", true);
                map.put("msg", "注册成功");
            } else {
                throw new RuntimeException("验证码错误...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", "注册失败，"+e.getMessage());
        }
        return map;
    }


    // 用户登录
    @PostMapping("login")
    public Map<String, Object> login (@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            User userDB = userService.login(user);
            map.put("state", true);
            map.put("msg", "登录成功");
            map.put("user", userDB);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

}
