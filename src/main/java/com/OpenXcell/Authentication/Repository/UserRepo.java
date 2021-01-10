package com.OpenXcell.Authentication.Repository;

import com.OpenXcell.Authentication.Model.UserVO;

import java.util.List;

public interface UserRepo {

	void insert(UserVO userVO);

	List<UserVO> search();

	List<UserVO> findById(int id);

	List<UserVO> getUserbyUserNameOrMail(String str);

	List<UserVO> findByEmail(String email);
}
