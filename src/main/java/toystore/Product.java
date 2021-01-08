package toystore;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;

public class Product implements Serializable, Cloneable {
    @Serial
    private static final long serialVersionUID = 42L;

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

    /**
     * Get the cost of the current product, also applying the discount associated
     * with it.
     * @return the cost of the this product.
     */
    public double getCost() {
        if (discount == null)  // no discount
            return price;
        // there is a discount to apply
        if (discount.getDiscountType() == DiscountType.FIXED_DISCOUNT) {
            return price - discount.getValue();
        }
        else {
            // cred si eu ca discount ul in percentage este intre 0 so 100
            // DELETE LATER!!!!
            return price * (100 - discount.getValue()) / 100;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return new EqualsBuilder()
                .append(uniqueId, product.uniqueId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uniqueId)
                .toHashCode();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        Store store = Store.getInstance();
        return uniqueId + "," +
                name + "," +
                manufacturer + "," +
                store.getCurrency().getSymbol() + price + "," +
                quantity;
    }
}
