package com.OpenXcell.Authentication.Service;

import com.OpenXcell.Authentication.Model.UserVO;
import com.OpenXcell.Authentication.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public void insert(UserVO userVO) {
		this.userRepo.insert(userVO);
	}

	@Override
	public List<UserVO> search() {
		return this.userRepo.search();
	}

	@Override
	public List<UserVO> findById(int id) {
		return this.userRepo.findById(id);
	}

	@Override
	public List<UserVO> getUserbyUserNameOrMail(String str) {
		return this.userRepo.getUserbyUserNameOrMail(str);
	}

	@Override
	public List<UserVO> findByEmail(String email) {
		return this.userRepo.findByEmail(email);
	}
}
