package in.co.rays.project_3.dto;

import java.util.Date;

public class ProductDTO extends BaseDTO {

	private String productName;
	private String productAmmount;
	private Date purchaseDate;
	private String productCategory;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductAmmount() {
		return productAmmount;
	}

	public void setProductAmmount(String productAmmount) {
		this.productAmmount = productAmmount;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Override
	public String getKey() {
		 
		return id + "";
	}

	@Override
	public String getValue() {
		 
		return productName;
	}

	@Override
	public int compareTo(BaseDTO o) {
	 
		return 0;
	}

}
