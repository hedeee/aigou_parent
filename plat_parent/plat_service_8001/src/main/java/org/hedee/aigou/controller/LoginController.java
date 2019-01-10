package org.hedee.aigou.controller;

import org.hedee.aigou.domain.Employee;
import org.hedee.aigou.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee){
        if ("admin".equals(employee.getName())&&"admin".equals(employee.getPassword()))
            return AjaxResult.getAjaxResult();
        return AjaxResult.getAjaxResult().setSuccess(false).setMessage("用户名或者密码错误");
    }
}
