package toystore.productline;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * Used to describe a manufacturer's characteristics, with a name and a number of products associated
 * with it. It is associated with a(or multiple) product/s from the singleton {@link toystore.Store}
 * and keeps track of the number of each product it has manufactured.
 * <p>
 *     It implements {@linkplain Serializable} since {@link toystore.Store} want to serialize every manufacturer.
 * </p>
 * <p>
 *     Class also implements {@linkplain Comparable} since {@link command.product.ListManufacturers} requires the
 *     manufacturers to be sorted before printing them.
 * </p>
 */

public class Manufacturer implements Serializable, Comparable<Manufacturer> {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Name of manufacturer
     */
    private final String name;

    /**
     * Number of products created by this manufacturer
     */
    private int countProducts;

    /**
     * Initialize the name of the manufacturer
     * @param name manufacturer's name
     */
    public Manufacturer(String name) {
        this.name = name;
        // on object creation, there are no products associated with this manufacturer
        this.countProducts = 0;
    }

    /**
     * @return name of the manufacturer
     */
    public String getName() {
        return name;
    }

    /**
     * @return number of products associated with the manufacturer
     */
    public int getCountProducts() {
        return countProducts;
    }

    /**
     * Add a number of products to this manufacturer catalogue
     * @param numProducts number of products newly created
     */
    public void appendProducts(int numProducts) {
        countProducts += numProducts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Manufacturer that = (Manufacturer) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Manufacturer manufacturer) {
        return name.compareTo(manufacturer.name);
    }
}
