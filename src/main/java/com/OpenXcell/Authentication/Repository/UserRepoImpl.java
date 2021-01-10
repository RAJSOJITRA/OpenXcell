package com.OpenXcell.Authentication.Repository;

import com.OpenXcell.Authentication.Model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo{

	@Autowired
	EntityManagerFactory emf;

	@Override
	public void insert(UserVO userVO) {
		EntityManager manager = null;
		try {
			System.out.println("In insert method default id" + userVO.getId());
			manager = emf.createEntityManager();
			manager.getTransaction().begin();

			List<UserVO> registerList = new ArrayList<UserVO>();
			registerList = this.findById(userVO.getId());

			if (registerList.isEmpty()) {
				manager.persist(userVO);
				manager.flush();
			} else {
				System.out.println("in insert method update section");
				manager.merge(userVO);
				manager.flush();
			}
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<UserVO> search() {
		List<UserVO> registerList = new ArrayList<UserVO>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from UserVO where status=true");
			registerList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return registerList;
	}

	@Override
	public List<UserVO> findById(int id) {
		List<UserVO> registerList = new ArrayList<UserVO>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from UserVO where id = " + id);
			registerList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return registerList;
	}

	@Override
	public List<UserVO> getUserbyUserNameOrMail(String str) {
		System.out.println("-------UserRepoimpl str="+str);
		List<UserVO> loginList = new ArrayList<UserVO>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from UserVO where userName = :uname");
			query.setParameter("uname", str);
			//query.setParameter("mail", str);
			loginList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginList;
	}

	@Override
	public List<UserVO> findByEmail(String email) {

		List<UserVO> loginList = new ArrayList<UserVO>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from UserVO where emailId =: email");
			query.setParameter("email", email);
			loginList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginList;
	}
}
