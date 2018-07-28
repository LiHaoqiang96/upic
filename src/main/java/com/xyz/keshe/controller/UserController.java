package com.xyz.keshe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.xyz.keshe.domain.User;
import com.xyz.keshe.service.impl.UserServiceImpl;

@Controller
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	
	/**
	 * 测试框架
	 */
	@RequestMapping("/select")
	@ResponseBody
	public List<User> selectAll(HttpServletRequest request) {
		return userService.selectAll();
	} 
	
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST )
	public Map<String,Object> login(HttpServletRequest request,@RequestParam("userName")String user,@RequestParam("userPwd")String password) {
		int log=0;
		Map<String,Object> map = new HashMap<>();
		log=userService.selectUser(user, password);
		map.put("isSuccess", 0);
		if(log==1)  
			map.put("isSuccess", 1);
		 return map;
	}
	
	
	/**
	 * 注册
	 * @param usernum:手机号或者学号
	 * @param email:邮箱
	 * @param name:名字，如：李浩强
	 * @param password:密码
	 * @param pas:确认密码
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Map<String,Object>  register(HttpServletRequest request,
			@RequestParam("userNumber")String  usernum,
			@RequestParam("userEmail")String email,
			@RequestParam("userName")String  name,
			@RequestParam("userPwd")String password,
			@RequestParam("pas")String pas
			) {
		int log=0;
		Map<String,Object> map = new HashMap<>();
		map.put("isSuccess", 0);
		if(password.equals(pas)) {
			log=userService.insertUser(name, usernum, email, password);
		}
		if(log==1) {
			map.put("isSuccess", 1);
		}
		  if(log==2){
			 map.put("error", "用户已存在");
		}
		  return map;
	}
	
	
	/**
	 * 发送短信验证码
	 * @param tel:手机号
	 */
	@RequestMapping("/sendSms")
	public void sendSms(@RequestParam("userPhone") String userPhone, HttpServletRequest request) throws InterruptedException, ClientException {
		SendSmsResponse response = userService.sendSms(userPhone);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

        Thread.sleep(3000L);

        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = userService.querySendDetails(userPhone, response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());       
        }
	}
	
}
