package com.OpenXcell.Product.Contoller;

import com.OpenXcell.Authentication.Model.UserVO;
import com.OpenXcell.Authentication.Service.UserService;
import com.OpenXcell.Product.Model.ProductVO;
import com.OpenXcell.Product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@ResponseBody
	@PostMapping(value = "/CreateProductAPI")
	public String CreateProductAPI(@RequestParam("image") MultipartFile files,@RequestParam("data") ProductVO product) {

		//image strore...
		String current= Paths.get(".").toAbsolutePath().normalize().toString();
		System.out.println("path :"+current);
		try{
			this.productService.SaveImage(files);
		}
		catch (Exception e)
		{
			System.out.println("error in saving photo....");
			e.printStackTrace();
		}
		product.setImage(current);
		this.productService.insert(product);
		return "Product Entered Successfully..!!";
	}


	@ResponseBody
	@RequestMapping(value = "/ProductDetailAPI",method = RequestMethod.POST)
	public List<ProductVO> ProductDetailAPI(@RequestParam("productId") String productId) {
		List<ProductVO> productList=null;
		try
		{
			int id=Integer.parseInt(productId);
			productList=this.productService.findById(id);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return productList;
	}

	@ResponseBody
	@RequestMapping(value = "/ViewProductFilterAPI",method = RequestMethod.POST)
	public List<ProductVO> ViewProductFilterAPI(@RequestParam("uName") String uname) {
		List<ProductVO> productList=null;
		try
		{
			List<UserVO> userList=this.userService.getUserbyUserNameOrMail(uname);
			if(userList.size()!=0)
			{
				productList=this.productService.findByUserID(userList.get(0).getId());
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return productList;
	}
}
