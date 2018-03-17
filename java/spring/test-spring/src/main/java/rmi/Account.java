package rmi;

import java.io.Serializable;

public class Account implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{name='" + name + '\'' + '}';
    }
}
