// -----------------------------------------------------
// Assignment 1
// Question: 1 
// Written by: Graeme Killick, 40089907
// -----------------------------------------------------


public class Appliance {

	//declare attributes
	private String type, brand;
	private int serialNumber;
	private double price;
	//use this to generate serial number
	private static int count = 0;
	
	//default constructor
	Appliance(){
		
		brand = "LG";
		type = "Fridge";
		serialNumber = 1000000 + count;
		price = 1.0;
		//increment count
		count++;
	}
	
	//custom constructor
	Appliance(String brand, String type, double price){
		this.brand = brand;
		this.type = (validateType(type)) ? type : "Fridge";
		this.price = (validatePrice(price)) ? price : 1.0;
		serialNumber = 1000000 + count;
		//increment count
		count++;
	}
	//copy constructor
	Appliance(Appliance appliance){
		brand = appliance.brand;
		type = appliance.type;
		price = appliance.price;
		serialNumber = 1000000 + count;
		count++;
	}
	
	//compare two appliances
	public static boolean equals(Appliance app1, Appliance app2) {
		return app1.brand == app2.brand && app1.price == app2.price && app1.type == app2.type;
	}
	
	//getter methods
	public String getBrand(){
		return brand;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public double getPrice() {
		return price;
	}
	public String getType() {
		return type;
	}
	//get number of product created (static)
	public static int findNumberOfCreatedAppliances() {
		return count;
	}
	
	//setter methods
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setType(String type) {
		if(validateType(type)) {
			this.type = type;
		}
	}
	public void setPrice(double price) {
		if(validatePrice(price)) {
			this.price = price;
		}
	}
	
	//toString method
	public String toString() {
		return "Appliance Type: "+type+" Brand: "+brand+" price: $"+String.format("%.2f", price)+" Serial Number: "+serialNumber;
	}
	
	

	//validation type
	public static boolean validateType(String type) {
		String brands[] = {"Fridge", "Air Conditioner", "Washer", "Dryer", "Freezer", "Stove", "Dishwasher","Water Heaters", "Microwave"};
		boolean validType = false;
		for(int i= 0; i < brands.length; i++) {
			if(brands[i].equals(type)) {
				validType = true;
			}
		}
		return validType;
	}
	//validation price
	public static boolean validatePrice(double price) {
		return price >= 1.0;
	}
}
