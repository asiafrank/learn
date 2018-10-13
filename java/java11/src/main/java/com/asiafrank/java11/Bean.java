package com.asiafrank.java11;

import java.util.Objects;

public class Bean {
    private int id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bean bean = (Bean) o;
        return id == bean.id &&
                Objects.equals(name, bean.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
