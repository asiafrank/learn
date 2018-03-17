package rmi;

import java.util.LinkedList;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    public void insertAccount(Account account) {
        System.out.println("insertAccount---" + account.toString());
    }

    public List<Account> getAccounts(String name) {
        System.out.println("getAccount---" + name);

        Account a1 = new Account();
        a1.setName("AAAA");
        Account a2 = new Account();
        a2.setName("BBBB");
        Account a3 = new Account();
        a3.setName("CCCC");
        Account a4 = new Account();
        a4.setName("DDDD");

        List<Account> accounts = new LinkedList<Account>();
        accounts.add(a1);
        accounts.add(a2);
        accounts.add(a3);
        accounts.add(a4);
        return accounts;
    }
}