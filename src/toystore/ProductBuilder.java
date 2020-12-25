package toystore;

public class ProductBuilder {

    private final Product product;

    public ProductBuilder() {
        product = new Product();
    }

    public ProductBuilder withUniqueld(String uniqueld) {
        product.setUniqueld(uniqueld);
        return this;
    }

    public ProductBuilder withName(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder withManufacturer(Manufacturer manufacturer) {
        product.setManufacturer(manufacturer);
        return this;
    }

    public ProductBuilder withPrice(double price) {
        product.setPrice(price);
        return this;
    }

    public ProductBuilder withQuantity(int quantity) {
        product.setQuantity(quantity);
        return this;
    }

    public ProductBuilder withDiscount(Discount discount) {
        product.setDiscount(discount);
        return this;
    }
}
