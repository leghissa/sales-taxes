package test.exercise.util;

public final class DBC {

    private DBC(){}

    public static void notNull(Object o, String message){
        precondition(o != null ,message);
    }

    public static void notBlank(String s, String message){
        precondition(s != null && !s.trim().isEmpty() ,message);
    }

    public static void precondition(boolean precondition, String message){
        if(message == null){
            throw new IllegalArgumentException("message should not be null");
        }
        if(!precondition){
            throw new IllegalArgumentException(message);
        }
    }
}
