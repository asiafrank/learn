package rmi;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleObject {
    private AccountService accountService;

    private AtomicInteger count = new AtomicInteger(0);

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void insert(Account account) {
        accountService.insertAccount(account);
        System.out.println("client insert" + count + ": " + account);
        count.incrementAndGet();
    }

    public void getAccounts() {
        List<Account> accounts = accountService.getAccounts("AAA" + count);
        for (Account a : accounts) {
            System.out.println(count + "-" + a);
        }
        System.out.println("client getAccounts");
    }
}
