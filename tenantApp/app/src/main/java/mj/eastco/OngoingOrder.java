package mj.eastco;

import java.util.List;

/**
 * Created by MaximaXL on 4/11/2017.
 */

public class OngoingOrder {
    private List<Order> transactions;

    public List<Order> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Order> transactions) {
        this.transactions = transactions;
    }
}
