package toystore;

public class Product {

    private String uniqueId;
    private String name;
    private Manufacturer manufacturer;
    private double price;
    private int quantity;
    private Discount discount;

    public String getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }
}
