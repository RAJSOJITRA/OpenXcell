package com.OpenXcell.Authentication.Repository;

import com.OpenXcell.Authentication.Model.AuthenticationRequest;
import com.OpenXcell.Authentication.Model.UserVO;

import java.util.List;

public interface LoginRepo {

	void insert(AuthenticationRequest loginVO);

	List<AuthenticationRequest> search();

	List<AuthenticationRequest> findById(int id);

	List<AuthenticationRequest> findByUserName(String str);
}
