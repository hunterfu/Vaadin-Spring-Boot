package com.selop.vaadinApp.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

/**
 * Created by selop on 21/10/15.
 */
@SpringComponent
@UIScope
public class Greeter {
    public String sayHello(){
        return "Hello from bean: " + toString();
    }
}
