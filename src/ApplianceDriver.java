/**
 * This is my driver for the Appliance Class
 * @author graemekillick
 * @version 1.0
 *
 */

//need keyboard input
import java.util.Scanner;

public class ApplianceDriver {

	public static void main(String[] args) {

		// initialize variables
		String PASS = "c249";
		int passAttempt = 0;
		int choice;
		Scanner keyboard = new Scanner(System.in);
		Appliance[] inventory;
		int capacity;

		// welcome user and ask size of inventory
		System.out.println("Welcome to your store Inventory Application!");
		System.out.println("Please enter how items can you fit in your store :");
		capacity = keyboard.nextInt();
		inventory = new Appliance[capacity];

		// display menu 
		while (true) {
			displayMenu();
			choice = keyboard.nextInt();

			switch (choice) {

			case 1:
				// prompt for password
				if (verifyPassword(keyboard, passAttempt, PASS)) {
					// reset password attempts after success
					passAttempt = 0;

					// initialize variables for space left
					int remaining = 0;
					int toAdd = 1;
					while (toAdd > remaining) {
						// ask how many appliances
						remaining = checkCapacity(inventory);
						System.out.println("Please enter how many appliances you would like to add: ");
						toAdd = keyboard.nextInt();
						if (remaining == 0) {
							System.out.println("Sorry there is no more space in your inventory!");
							// no space so break out of loop and send to menu
							break;
						} else if (toAdd > remaining) {
							System.out.println(
									"There is only space for " + remaining + " item" + (remaining != 1 ? "s" : ""));

						}
					}
					if (remaining != 0 && toAdd <= remaining) {
						// loop through and add all items
						for (int i = 0; i < toAdd; i++) {
							System.out.println("Please enter the brand of the appliance" + (i + 1) + ":");
							String brand = keyboard.next();
							String type = null;
							boolean validType = false;
							// make sure the name is a valid type
							while (!validType) {
								System.out.println("Please enter the type of appliance: ");
								type = keyboard.nextLine();
								validType = Appliance.validateType(type);
								if (!validType) {
									System.out.println(
											"The type must be one of the following: Fridge, Air Conditioner, Washer, Dryer, Freezer, Stove, Dishwasher, Water Heaters, or Microwave ");
								}
							}
							// get and validate price
							double price = 0.0;
							boolean validPrice = false;
							while (!validPrice) {
								System.out.println("Please enter a price for the appliance: ");
								price = keyboard.nextDouble();
								validPrice = Appliance.validatePrice(price);
								if (!validPrice) {
									System.out.println("Price can't be less than $1.00");
								}
							}
							// now create Appliance object and place in array free location
							for (int j = 0; j < inventory.length; j++) {
								if (inventory[j] == null) {
									inventory[j] = new Appliance(brand, type, price);
									break;
								}
							}

						}
						// Successfully added items
						System.out.println("The items were successfully added, current inventory: ");
						for (int i = 0; i < inventory.length; i++) {
							if (inventory[i] != null) {
								System.out.println(inventory[i]);
							}
						}
					}

				} else {
					// increment passAttempt and send back to menu by setting choice to 0
					passAttempt += 3;
				}

				break;

			case 2:
				if (verifyPassword(keyboard, passAttempt, PASS)) {
					// reset password attempts after success
					passAttempt = 0;

					// serial number input
					int serialNumber;
					Appliance appliance = null;
					while (appliance == null) {
						System.out
								.println("Please Enter the serial number of the appliance you would like to update: ");
						serialNumber = keyboard.nextInt();
						// look up if appliance is in inventory
						appliance = checkSerial(inventory, serialNumber);

						if (appliance != null) {
							System.out.println(appliance);
							int choice2 = 0;
							while (choice2 > 4 || choice2 < 1) {
								System.out.println("What information would you like to change?");
								System.out.println("\t1.\tbrand");
								System.out.println("\t2.\ttype");
								System.out.println("\t3.\tprice");
								System.out.println("\t4.\tQuit");
								System.out.println("Enter your choice> ");
								choice2 = keyboard.nextInt();

								// switch based on choice of change
								switch (choice2) {
								case 1:
									System.out.println("Please enter a the new brand name: ");
									String brand = keyboard.next();
									appliance.setBrand(brand);
									System.out.println("Succesfully updated!\n" + appliance);
									choice2 = 0;
									break;
								case 2:
									String type;
									boolean validType = false;
									// make sure the name is a valid type
									while (!validType) {
										System.out.println("Please enter the new type of appliance: ");
										type = keyboard.nextLine();
										validType = Appliance.validateType(type);
										if (!validType) {
											System.out.println(
													"The type must be one of the following: Fridge, Air Conditioner, Washer, Dryer, Freezer, Stove, Dishwasher, Water Heaters, or Microwave ");
										} else {
											appliance.setType(type);
											System.out.println("Succesfully updated!\n" + appliance);
										}

									}
									break;
								case 3:
									// get and validate price
									double price = 0.0;
									boolean validPrice = false;
									while (!validPrice) {
										System.out.println("Please enter a price for the appliance: ");
										price = keyboard.nextDouble();
										validPrice = Appliance.validatePrice(price);
										if (!validPrice) {
											System.out.println("Price can't be less than $1.00");
										} else {
											appliance.setPrice(price);
											System.out.println("Succesfully updated!\n" + appliance);
											// send back to edit menu
											choice2 = 0;
										}
									}
								case 4:
									break;

								default:
									break;
								}

							}

						} else {
							System.out.println("hi");
						}

					} // serial number loop
				}
				break;

			case 3: // Find appliances by brand option
				System.out.println("Please enter the brand name: ");
				String brand = keyboard.next();
				Appliance[] appliances = findAppliancesByBrand(inventory, brand);
				if (appliances.length > 0) {
					System.out.println("The following appliances are in inventory under brand " + brand + ":\n");
					for (int i = 0; i < appliances.length; i++) {
						System.out.println(appliances[i]);
					}
				} else {
					System.out.println("Sorry there are no appliances under that brand name.\n");
				}
				// send back to main menu
				break;

			case 4: // find appliances under a certain price
				System.out.println("Please enter price to find items less than: ");
				double price = keyboard.nextDouble();
				Appliance[] appliancesByPrice = findCheaperThan(inventory, price);
				if (appliancesByPrice.length > 0) {
					System.out.println(
							"The following appliances were found under $" + String.format("%.2f", price) + ":\n");
					for (int i = 0; i < appliancesByPrice.length; i++) {
						System.out.println(appliancesByPrice[i]);
					}
				} else {
					System.out.println("Sorry there are no appliances under $" + String.format("%.2f", price) + "\n");
				}
				// send back to main menu

				break;

			case 5:
				// exit application
				System.out.println("Thankyou for using the Inventory App!");
				// close Scanner
				keyboard.close();
				System.exit(0);
				break;

			default:
				System.out.println("Invalid option selected");
				break;

			}

		}

	}

	// method for displaying menu
	public static void displayMenu() {
		System.out.println("What do you want to do?");
		System.out.println("\t 1.\tEnter new appliances (password required)");
		System.out.println("\t 2.\tChange information of an appliance (password required)");
		System.out.println("\t 3.\tDisplay all appliances by a specific brand");
		System.out.println("\t 4.\tDisplay all appliances under a certain price.");
		System.out.println("\t 5.\tQuit");
		System.out.print("Please enter your choice > ");
	}

	// method for checking inventory capacity;
	public static int checkCapacity(Appliance[] appliances) {
		int count = 0;
		for (int i = 0; i < appliances.length; i++) {
			if (appliances[i] == null) {
				count++;
			}
		}
		return count;
	}

	// method for checking for serial number
	public static Appliance checkSerial(Appliance[] inventory, int serialNumber) {
		Appliance appliance = null;
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				if (inventory[i].getSerialNumber() == serialNumber) {
					appliance = inventory[i];
					break;
				}
			}
		}
		return appliance;
	}

	// method for finding appliances by brand
	public static Appliance[] findAppliancesByBrand(Appliance[] inventory, String brand) {
		// create array with maximum size
		Appliance[] appliances = new Appliance[inventory.length];
		int count = 0;

		for (int i = 0; i < inventory.length; i++) {
			// check for empty slots in array to avoid error
			if (inventory[i] != null) {
				if (inventory[i].getBrand().contentEquals(brand)) {
					appliances[count] = inventory[i];
					count++;
				}
			}
		}
		// create new array with only relevant appliances
		Appliance[] appliancesReturn = new Appliance[count];
		for (int i = 0; i < count; i++) {
			appliancesReturn[i] = appliances[i];
		}
		return appliancesReturn;
	}

	// method for finding appliances under certain price
	public static Appliance[] findCheaperThan(Appliance[] inventory, double price) {
		// create array with maximum size
		Appliance[] appliances = new Appliance[inventory.length];
		int count = 0;

		for (int i = 0; i < inventory.length; i++) {
			// check for empty slots in array to avoid error
			if (inventory[i] != null) {
				if (inventory[i].getPrice() < price) {
					appliances[count] = inventory[i];
					count++;
				}
			}
		}
		// create new array with only relevant appliances
		Appliance[] appliancesReturn = new Appliance[count];
		for (int i = 0; i < count; i++) {
			appliancesReturn[i] = appliances[i];
		}
		return appliancesReturn;
	}

	// method for verifying password
	public static boolean verifyPassword(Scanner keyboard, int passAttempt, String pass) {
		boolean passwordCorrect = false;

		for (int i = 0; i < 3; i++) {
			System.out.print("\nPlease enter your password: ");
			if (keyboard.next().contentEquals(pass)) {
				System.out.println("correct!");
				passwordCorrect = true;
				break;
			} else {
				System.out.println("Incorrect Password");
				// increment passAttempt
				passAttempt++;

				// quit program if we have reached 12 attempts
				if (passAttempt == 12) {
					System.out.println("Program has detected suspicious activities and will terminate immediately!");
					System.exit(0);
				}

			}
		}
		return passwordCorrect;
	}

}
