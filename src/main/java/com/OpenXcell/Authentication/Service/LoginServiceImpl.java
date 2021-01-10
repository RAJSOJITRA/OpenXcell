package com.OpenXcell.Authentication.Service;

import com.OpenXcell.Authentication.Model.AuthenticationRequest;
import com.OpenXcell.Authentication.Repository.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements  LoginService{

	@Autowired
	private LoginRepo loginRepo;

	@Override
	public void insert(AuthenticationRequest loginVO) {
		this.loginRepo.insert(loginVO);
	}

	@Override
	public List<AuthenticationRequest> search() {
		return this.loginRepo.search();
	}

	@Override
	public List<AuthenticationRequest> findById(int id) {
		return this.loginRepo.findById(id);
	}

	@Override
	public List<AuthenticationRequest> findByUserName(String str) {
		return this.loginRepo.findByUserName(str);
	}

}
