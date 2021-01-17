package toystore.productline;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import toystore.Store;
import toystore.financial.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Used to describe a product's characteristics, with an unique identifier, name, manufacturer associated
 * with it, price, quantity in stock and discount. The singleton {@link Store} keeps a list of products as
 * field, it is heavily used from reading and writing to csv files.
 * <p>
 *     The class also implements {@link Serializable}, since {@link Store} will serialize and de-serialize
 *     every product it has.
 * </p>
 *
 */

public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * An unique identifier for the product
     */
    private String uniqueId;

    /**
     * A name for the product
     */
    private String name;

    /**
     * A manufacturer associated with the product, it's created
     */
    private Manufacturer manufacturer;

    /**
     * How much the product is worth
     */
    private double price;

    /**
     * How many copies of this product there are in the {@link Store}
     */
    private int quantity;

    /**
     * The discount applied to the product, or <em>null</em> otherwise
     */
    private Discount discount;

    /**
     * @return the unique identifier
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @return name of product
     */
    public String getName() {
        return name;
    }

    /**
     * @return manufacturer of product
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * @return price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return number of products in stock
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return discount applied to product
     */
    public Discount getDiscount() {
        return discount;
    }

    /**
     * @param uniqueId the unique identifier
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @param name name of product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param manufacturer manufacturer of product
     */
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @param price price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param quantity number of products in stock
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @param discount discount applied to product
     */
    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return new EqualsBuilder()
                .append(uniqueId, product.uniqueId)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uniqueId)
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Store store = Store.getInstance();
        return uniqueId + "," +
                name + "," +
                manufacturer + "," +
                store.getCurrency().getSymbol() + String.format("%,.3f", price) + "," +
                quantity;
    }
}