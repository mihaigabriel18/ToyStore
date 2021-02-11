package testing;

/**
 * Enum of all possible inputs, from the end-user, a command that is not listed below
 * will not be deemed correct, and will result in an exception
 */

public enum AllCommands {

    addcurrency,
    getstorecurrency,
    listcurrencies,
    setstorecurrency,
    updateparity,
    adddiscount,
    applydiscount,
    calculatetotal,
    listdiscounts,
    loadcsv,
    loadstore,
    savestore,
    savecsv,
    listmanufacturers,
    listproducts,
    listproductsbymanufacturer,
    showproduct,
    exit,
    quit
}
