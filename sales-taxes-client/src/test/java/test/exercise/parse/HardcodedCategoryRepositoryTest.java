package test.exercise.parse;

import org.junit.Test;
import test.exercise.model.Category;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HardcodedCategoryRepositoryTest {

    private final CategoryRepository categoryRepository = new HardcodedCategoryRepository();

    @Test
    public void canGetCategoryForKnownProduct(){
        final Category category = categoryRepository.findByProductName("box of chocolates");

        assertThat(category.getName()).isEqualTo("food");
    }

    @Test(expected = NoSuchElementException.class)
    public void cannotGetCategoryForUnknownProduct(){
        categoryRepository.findByProductName("boat");
    }

    @Test(expected = NoSuchElementException.class)
    public void cannotGetCategoryForNullProduct(){
        categoryRepository.findByProductName(null);
    }

}