package toystore;

public class Currency {

    String name;
    String symbol;
    double parityToEur;

    void updateParity(double parityToEur) {
        this.parityToEur = parityToEur;
    }
}
