package com.OpenXcell.Authentication.Service;

import com.OpenXcell.Authentication.Model.AuthenticationRequest;
import com.OpenXcell.Authentication.Model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private LoginService loginService;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		List<UserVO> l1= this.userService.getUserbyUserNameOrMail(s);
		List<AuthenticationRequest> u1=null;
		AuthenticationRequest user=null;
		if(l1!=null)
		{
			u1=this.loginService.findByUserName(l1.get(0).getUserName());
			if(u1!=null) {
				user = u1.get(0);
			}
		}
		return new MyUserDetails(user);
	}
}
