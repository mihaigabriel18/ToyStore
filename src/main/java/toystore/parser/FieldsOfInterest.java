package toystore.parser;

/**
 * Enumeration of all fields that we need to keep from the sample csv given as input.
 * Some fields from the sample don't matter and we only need to keep the ones listed
 * below. The reasoning behind implementing this enumeration is that future updates
 * may require different fields to be kept from the csv file.
 * <p>
 * There is also a specific order in which those fields are listed, because the parser
 * is going to abide by this order and organize the columns as seen below
 */

public enum FieldsOfInterest {

    /**
     * Unique id of product
     */
    uniq_id,

    /**
     * Name of product
     */
    product_name,

    /**
     * Name of manufacturer for this product
     */
    manufacturer,

    /**
     * Price value for this product
     */
    price,

    /**
     * Number of remaining products in stock
     */
    number_available_in_stock
}
