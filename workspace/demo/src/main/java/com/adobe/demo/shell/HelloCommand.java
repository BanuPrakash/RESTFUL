package com.adobe.demo.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class HelloCommand {

    @ShellMethod(key = "hello", value = "Will say Hello")
    public String sayHello() {
        return "Hello World";
    }

    @ShellMethod(key = "bye", value = "Will say Bye")
    public String sayBye() {
        return "Bye User!!!";
    }
}
