package com.OpenXcell.Product.Repository;

import com.OpenXcell.Product.Model.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepoImpl implements ProductRepo {

	@Autowired
	EntityManagerFactory emf;

	@Override
	public void insert(ProductVO productVO) {
		EntityManager manager = null;
		try {
			System.out.println("In insert method default id" + productVO.getId());
			manager = emf.createEntityManager();
			manager.getTransaction().begin();

			List<ProductVO> productList = new ArrayList<ProductVO>();
			productList = this.findById(productVO.getId());

			if (productList.isEmpty()) {
				manager.persist(productVO);
				manager.flush();
			} else {
				System.out.println("in insert method update section");
				manager.merge(productVO);
				manager.flush();
			}
			manager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ProductVO> search() {
		List<ProductVO> productList = new ArrayList<ProductVO>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from ProductVO");
			productList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public List<ProductVO> findById(int id) {
		List<ProductVO> productList = new ArrayList<ProductVO>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from ProductVO where id = " + id);
			productList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public void SaveImage(MultipartFile imageFile) {
		try
		{
			String current= Paths.get(".").toAbsolutePath().normalize().toString();
			String folder= current + "\\src\\main\\resources\\static\\productImage\\";
			System.out.println("path :"+folder);
			byte[] bytes=imageFile.getBytes();
			Path path=Paths.get(folder+imageFile.getOriginalFilename());
			Files.write(path,bytes);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public List<ProductVO> findByUserID(int uid) {
		List<ProductVO> productList = new ArrayList<ProductVO>();
		EntityManager manager = null;
		try {
			manager = emf.createEntityManager();
			Query query = manager.createQuery("from ProductVO where userId = " + uid);
			productList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}
}
