package com.nanshen.web.controller;


import com.nanshen.common.controller.BaseController;
import com.nanshen.common.utils.EhCacheUtil;
import com.nanshen.common.vo.ReMessage;
import com.nanshen.component.jwt.utils.JwtUtil;
import com.nanshen.component.shiro.tokens.MUsernamePasswordToken;
import com.nanshen.module.system.entity.SysUser;
import com.nanshen.module.system.service.SysUserService;
import com.nanshen.web.enums.LoginEnum;
import com.nanshen.web.enums.SysUserEnum;
import net.sf.ehcache.Element;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class LoginController extends BaseController {

    @Autowired
    SysUserService sysUserService;


    @PostMapping("/login")
    public ReMessage login(@RequestBody Map<String ,Object> loginMap, HttpServletRequest request){
        String name=(String)loginMap.get("name");
        String password=(String)loginMap.get("pwd");
        if(name==null||password==null||name.equals("")||password.equals("")){
            return error(LoginEnum.NAME_NOT_EMPTY);
        }
        Subject subject= SecurityUtils.getSubject();
        MUsernamePasswordToken usernamePasswordToken=new MUsernamePasswordToken(name,password);
        subject.login(usernamePasswordToken);
        SysUser user=sysUserService.findByName(name);
        String token= JwtUtil.sign(user.getId());
        return success(LoginEnum.LOGIN_SUCCESS,token);
    }

    @PostMapping("/logout")
    public ReMessage logout(@RequestHeader(name = "Authorization") String token){
        EhCacheUtil.getDictCache().put(new Element(token,token));
        return success(LoginEnum.LOGOUT_SUCCESS);
    }


}
