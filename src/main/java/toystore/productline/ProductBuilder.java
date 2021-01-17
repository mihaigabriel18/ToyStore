package toystore.productline;

import toystore.financial.Discount;

/**
 * Builder for {@link Product}, using the <em>Builder</em> design pattern for initializing only the
 * field we want from {@link Product}.
 * <p>
 *     Each time a product is initialized, we use this class's methods instead of a constructor in
 *     {@link Product}. All methods for initializing fields start with <em>"with"</em> (each method returns
 *     this so we can chain them as much as we want) and after initializing all fields that we need
 *     {@link #build()} is called to return the private instance of {@link Product} stored within the class.
 * </p>
 */

public class ProductBuilder {

    /**
     * Private instance of {@link Product}, we will return this at the end, after initializing the fields
     */
    private final Product product;

    /**
     * Declare new {@link Product} object
     */
    public ProductBuilder() {
        product = new Product();
    }

    /**
     * Initialize unique id field of {@link Product}
     * @param uniqueId unique identifier of {@link Product}
     * @return instance of {@link ProductBuilder}
     */
    public ProductBuilder withUniqueId(String uniqueId) {
        product.setUniqueId(uniqueId);
        return this;
    }

    /**
     * Initialize name field of {@link Product}
     * @param name name of {@link Product}
     * @return instance of {@link ProductBuilder}
     */
    public ProductBuilder withName(String name) {
        product.setName(name);
        return this;
    }

    /**
     * Initialize manufacturer field of {@link Product}
     * @param manufacturer name of manufacturer associated with {@link Product}
     * @return instance of {@link ProductBuilder}
     */
    public ProductBuilder withManufacturer(Manufacturer manufacturer) {
        product.setManufacturer(manufacturer);
        return this;
    }

    /**
     * Initialize price field of {@link Product}
     * @param price price of {@link Product}
     * @return instance of {@link ProductBuilder}
     */
    public ProductBuilder withPrice(double price) {
        product.setPrice(price);
        return this;
    }

    /**
     * Initialize quantity field of {@link Product}
     * @param quantity quantity of {@link Product}
     * @return instance of {@link ProductBuilder}
     */
    public ProductBuilder withQuantity(int quantity) {
        product.setQuantity(quantity);
        return this;
    }

    /**
     * Initialize discount field of {@link Product}
     * @param discount discount of {@link Product}
     * @return instance of {@link ProductBuilder}
     */
    public ProductBuilder withDiscount(Discount discount) {
        product.setDiscount(discount);
        return this;
    }

    /**
     * Final called method in the chain of building a {@link Product}
     * @return the private instance of {@link Product}
     */
    public Product build() {
        return product;
    }
}
