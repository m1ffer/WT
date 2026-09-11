package com.rpam.rd.autotasks;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CatchEmAll {

    //You may set another exception in this field;
    static Exception exception = new FileNotFoundException();

    public static void riskyMethod() throws Exception {
        throw exception;
    }

    public static void main(String[] args) throws Exception {
        try {
            riskyMethod();
        }
        catch(FileNotFoundException e){
            throw new IllegalArgumentException("Resource is missing", e);
        }
        catch(IOException e){
            throw new IllegalArgumentException("Resource error", e);
        }
        catch(ArithmeticException e){
            System.err.println("ar");
        }
        catch(NumberFormatException e){
            System.err.println("nfe");
        }
    }
}
