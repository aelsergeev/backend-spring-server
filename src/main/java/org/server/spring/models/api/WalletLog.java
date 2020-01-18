package org.server.spring.models.api;

import java.util.Set;

public class WalletLog {

    private String transaction;
    private Set<Integer> userIds;

    public WalletLog(String transaction, Set<Integer> userIds) {
        this.transaction = transaction;
        this.userIds = userIds;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}
