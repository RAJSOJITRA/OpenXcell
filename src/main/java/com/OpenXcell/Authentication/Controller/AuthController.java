package com.OpenXcell.Authentication.Controller;

import Utils.jwtUtil;
import com.OpenXcell.Authentication.Model.AuthenticationRequest;
import com.OpenXcell.Authentication.Model.AuthenticationResponse;
import com.OpenXcell.Authentication.Model.UserVO;
import com.OpenXcell.Authentication.Service.LoginService;
import com.OpenXcell.Authentication.Service.MyUserDetailsService;
import com.OpenXcell.Authentication.Service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private jwtUtil jwtTokenUtil;


	@RequestMapping(value = "/UserRegistrationAPI", method = RequestMethod.GET)
	public ResponseEntity loadRegister(@RequestBody UserVO userVO) throws Exception {

		AuthenticationRequest loginVO=new AuthenticationRequest();
		loginVO.setUsername(userVO.getUserName());
		loginVO.setPassword(userVO.getPassword());
		String message=null;

		List<UserVO> EmailList=this.userService.findByEmail(userVO.getEmailId());
		List<AuthenticationRequest> uNameList=this.loginService.findByUserName(userVO.getUserName());
		if(Integer.parseInt(userVO.getAge())<19)
		{
			message="User Age is Under 18..";
		}
		if(EmailList.size()>0)
		{
			message+=" Email id Already exist..";
		}
		if(uNameList.size()>0)
		{
			message+=" UserName Already Taken..";
		}

		if(message!=null)
		{
			this.userService.insert(userVO);
			this.loginService.insert(loginVO);
			message="sucessfullly done... ";
		}
		return ResponseEntity.ok(message);
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
			System.out.println("Auth controller  username="+authenticationRequest.getUsername()+" && authenticationRequest="+authenticationRequest);
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("In AuthController= Incorrect Username and Password...");
		}
		final UserDetails userDetails= userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final  String jwt= jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}


	@RequestMapping(value = "/hello")
	public String helloCheck() {
		System.out.println("in hello methods..");
		return "HelloWord";
	}

	@ResponseBody
	@RequestMapping(value = "/LoginAPI",method = RequestMethod.POST)
	public String Login(@RequestParam("uName") String uName ) {

		List<UserVO> user=this.userService.getUserbyUserNameOrMail(uName);
		String message;
		JSONObject json = new JSONObject();
		try {
			json.put("firstName", user.get(0).getFirstName());
			json.put("lastName", user.get(0).getLastName());
			json.put("userName", user.get(0).getUserName());
			json.put("EmailId", user.get(0).getEmailId());
			json.put("Age", user.get(0).getAge());
			message = json.toString();
		} catch (JSONException e) {
			message="User is Not Login (Exception).";
			e.printStackTrace();
		}

		System.out.println(message);
		return message;
	}

	@ResponseBody
	@RequestMapping(value = "/LogoutAPI",method = RequestMethod.GET)
	public String LogOut() {
		this.jwtTokenUtil.SetKey();
		return "SuccessFully LogOut...!!";
	}


	@RequestMapping(value = "/ForgotPasswordAPI",method = RequestMethod.POST)
	public ResponseEntity<?> ForgotPassword(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			System.out.println("Auth controller  username="+authenticationRequest.getUsername()+" && authenticationRequest="+authenticationRequest);
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("In AuthController= Incorrect Username and Password...");
		}
		final UserDetails userDetails= userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final  String jwt= jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}


	@ResponseBody
	@RequestMapping(value = "/ChangePasswordAPI",method = RequestMethod.POST)
	public String ChangePassword(@RequestParam("uName") String uName,@RequestParam("newPassword") String newPassword,@RequestParam("currPassword") String CurrPassword) {

		List<UserVO> user=this.userService.getUserbyUserNameOrMail(uName);
		List<AuthenticationRequest> login=this.loginService.findByUserName(uName);
		String message="In Change Password";

		if(CurrPassword.equals(user.get(0).getPassword()))
		{
			if(user.size()!=0 && login.size()!=0)
			{
				user.get(0).setPassword(newPassword);
				login.get(0).setPassword(newPassword);
				this.userService.insert(user.get(0));
				this.loginService.insert(login.get(0));
			}
			else
			{
				message="User Not Found Or Token is Not Correct";
			}
		}
		else {
			message = "Current Password is not Match.";
		}
		message="Password Changed Successfully..!!";
		System.out.println(message);
		return message;
	}

}
