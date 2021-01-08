package toystore;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;

public class Manufacturer implements Serializable, Comparable<Manufacturer> {
    @Serial
    private static final long serialVersionUID = 42L;

    private final String name;
    private int countProducts;

    public Manufacturer(String name) {
        this.name = name;
        // on object creation, there are no products associated with this manufacturer
        this.countProducts = 0;
    }

    public String getName() {
        return name;
    }

    public int getCountProducts() {
        return countProducts;
    }

    /**
     * Add a product to this manufacturer
     */
    public void appendProduct() {
        countProducts++;
    }

    public int countProducts() {
        return countProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Manufacturer that = (Manufacturer) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Manufacturer manufacturer) {
        return name.compareTo(manufacturer.name);
    }
}
