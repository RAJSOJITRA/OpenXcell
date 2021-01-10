package com.OpenXcell.Product.Model;

import javax.persistence.*;

@Entity
@Table(name ="ProductVO")
public class ProductVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column(name = "productName")
	private String productName;

	@Column(name = "quantity")
	private String quantity;

	@Column(name = "category")
	private String category;

	@Column(name = "image")
	private String image;

	@Column(name = "userId")
	private int userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ProductVO{" +
				"id=" + id +
				", productName='" + productName + '\'' +
				", quantity='" + quantity + '\'' +
				", category='" + category + '\'' +
				", image='" + image + '\'' +
				", userId='" + userId + '\'' +
				'}';
	}
}
