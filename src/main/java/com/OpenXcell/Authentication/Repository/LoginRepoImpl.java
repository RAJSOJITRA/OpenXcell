package com.OpenXcell.Authentication.Repository;

import com.OpenXcell.Authentication.Model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginRepoImpl implements LoginRepo {

	@Autowired
	EntityManagerFactory emf;

	@Override
	public void insert(AuthenticationRequest loginVO) {
		EntityManager manager = null;
		try {
			System.out.println("In insert method default id" + loginVO.getId());
			manager = emf.createEntityManager();
			manager.getTransaction().begin();

			List<AuthenticationRequest> loginList = new ArrayList<AuthenticationRequest>();
			loginList = this.findById(loginVO.getId());

			if (loginList.isEmpty()) {
				manager.persist(loginVO);
				manager.flush();
			} else {
				System.out.println("in insert method update section");
				manager.merge(loginVO);
				manager.flush();
			}
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<AuthenticationRequest> search() {
		List<AuthenticationRequest> loginList = new ArrayList<AuthenticationRequest>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from AuthenticationRequest");
			loginList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginList;
	}

	@Override
	public List<AuthenticationRequest> findById(int id) {
		List<AuthenticationRequest> loginList = new ArrayList<AuthenticationRequest>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from AuthenticationRequest where id = " + id);
			loginList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginList;
	}

	@Override
	public List<AuthenticationRequest> findByUserName(String str) {
		List<AuthenticationRequest> loginList = new ArrayList<AuthenticationRequest>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from AuthenticationRequest where userName = :uname");
			query.setParameter("uname", str);
			loginList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginList;
	}

}
