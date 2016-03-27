package test.exercise.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DBCTest {

    @Rule
    public ExpectedException expectedException  = ExpectedException.none();

    @Test
    public void notNullReturnsSuccessfullyWhenObjectIsNotNull(){
        DBC.notNull("", "some nice message");
    }

    @Test
    public void notNullThrowsExceptionWhenObjectIsNull(){
        final String exceptionMessage = "some nice message";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(exceptionMessage);

        DBC.notNull(null, exceptionMessage);
    }

    @Test
    public void notNullThrowsExceptionWhenMessageIsNullAndObjectIsNull(){

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("message should not be null");

        DBC.notNull(null, null);
    }

    @Test
    public void notNullThrowsExceptionWhenMessageIsNullAndObjectIsNotNull(){

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("message should not be null");

        DBC.notNull("", null);
    }

    @Test
    public void preconditionReturnsSuccessfullyWhenPrecoditionIsTrue(){
        DBC.precondition(true, "some nice message");
    }

    @Test
    public void preconditionThrowsExceptionWhenPreconditionIsFalse(){
        final String exceptionMessage = "some nice message";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(exceptionMessage);

        DBC.precondition(false, exceptionMessage);
    }

    @Test
    public void preconditionThrowsExceptionWhenMessageIsNullAndPreconditionIsFalse(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("message should not be null");

        DBC.precondition(false, null);
    }

    @Test
    public void preconditionThrowsExceptionWhenMessageIsNullAndPreconditionIsTrue(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("message should not be null");

        DBC.precondition(true, null);
    }

    @Test
    public void notBlankReturnsSuccessfullyWhenStringIsNotBlank(){
        DBC.notBlank("  a ", "some message");
    }

    @Test
    public void notBlankThrowsExceptionWhenStringIsBlank(){
        final String exceptionMessage = "some nice message";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(exceptionMessage);

        DBC.notBlank("   ", exceptionMessage);
    }

    @Test
    public void notBlankThrowsExceptionWhenStringIsNull(){
        final String exceptionMessage = "some nice message";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(exceptionMessage);

        DBC.notBlank(null, exceptionMessage);
    }

}