package com.OpenXcell.Product.Service;

import com.OpenXcell.Product.Model.ProductVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

	void insert(ProductVO productVO);

	List<ProductVO> search();

	List<ProductVO> findById(int id);

	void SaveImage(MultipartFile imageFile);

	List<ProductVO> findByUserID(int id);
}
