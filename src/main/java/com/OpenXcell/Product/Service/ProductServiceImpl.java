package com.OpenXcell.Product.Service;

import com.OpenXcell.Product.Model.ProductVO;
import com.OpenXcell.Product.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepo productRepo;

	@Override
	public void insert(ProductVO productVO) {
		this.productRepo.insert(productVO);
	}

	@Override
	public List<ProductVO> search() {
		return this.productRepo.search();
	}

	@Override
	public List<ProductVO> findById(int id) {
		return this.productRepo.findById(id);
	}

	@Override
	public void SaveImage(MultipartFile imageFile) {
		this.productRepo.SaveImage(imageFile);
	}

	@Override
	public List<ProductVO> findByUserID(int id) {
		return this.productRepo.findByUserID(id);
	}
}
