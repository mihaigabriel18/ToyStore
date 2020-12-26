package toystore;

public class Store {

    private String name;
    private Currency currency;
    private Product[] products;
    private Manufacturer[] manufacturers;
    private static Store INSTANCE;

    private Store() {
    }

    public static Store getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Store();
        return INSTANCE;
    }

}
