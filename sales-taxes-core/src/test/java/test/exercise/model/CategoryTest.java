package test.exercise.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    @Test
    public void canCreateNewCategory(){
        final String name = "category";
        final Category category = new Category(name);

        assertThat(category.getName()).isEqualTo(name);
    }

    @Test
    public void nameShouldNotBeNull(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name should not be blank");

        new Category(null);
    }

    @Test
    public void nameShouldNotBeBlank(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("name should not be blank");

        new Category("  ");
    }

}