package org.example;

import org.example.boot.MySpringApplication;
import org.example.boot.MySpringBootApplication;

@MySpringBootApplication
public class MySpringBootMain {

    public static void main(String[] args) {
        MySpringApplication.run(MySpringBootMain.class, args);
    }
}
