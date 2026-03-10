package application;

public class Car {
	private String id, model, plate, type, price, status, customer;

    public Car(String id, String model, String plate, String type, String price, String status, String customer) {
        this.id = id;
        this.model = model;
        this.plate = plate;
        this.type = type;
        this.price = price;
        this.status = status;
        this.customer = customer;
    }

    public String getId() { return id; }
    public String getModel() { return model; }
    public String getPlate() { return plate; }
    public String getType() { return type; }
    public String getPrice() { return price; }
    public String getStatus() { return status; }
    public String getCustomer() { return customer; }
}
