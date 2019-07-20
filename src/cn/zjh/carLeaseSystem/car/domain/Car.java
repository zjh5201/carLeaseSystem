package cn.zjh.carLeaseSystem.car.domain;

public class Car {
	private String carId;
	private String brand;
	private String model;
	private int gear;
	private int price;
	private int type;
	private CarCompany cc;
	private String carImage;
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	@Override
	public String toString() {
		return "Car [carId=" + carId + ", brand=" + brand + ", model=" + model + ", gear=" + gear + ", price=" + price
				+ ", type=" + type + ", cc=" + cc + ", carImage=" + carImage + "]";
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getGear() {
		return gear;
	}
	public void setGear(int gear) {
		this.gear = gear;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public CarCompany getCc() {
		return cc;
	}
	public void setCc(CarCompany cc) {
		this.cc = cc;
	}
	public String getCarImage() {
		return carImage;
	}
	public void setCarImage(String carImage) {
		this.carImage = carImage;
	}

	
}
