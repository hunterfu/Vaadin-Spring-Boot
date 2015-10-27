package com.selop.vaadinApp.view;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

/**
 * Simple class to display a description in the GraphView.class using the Spring Annotations.
 */
@SpringComponent
@ViewScope
public class GraphGreeter {
    public String sayHello() {
        return "Chart visualization of the data set using the High Chart API.";
    }
}