package test.exercise.sales.taxes.model;

import test.exercise.sales.taxes.util.DBC;

public class Category {

    private final String name;

    public Category(String name) {
        DBC.notBlank(name, "name should not be blank");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return name.equals(category.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
