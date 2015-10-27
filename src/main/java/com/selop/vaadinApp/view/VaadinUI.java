package com.selop.vaadinApp.view;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Main UI class for the basic user interface.
 * We use a SpringViewProvider in combination with a vaadin Navigationbar.
 *
 * @author selop
 */
@Theme("valo")
@Title("Vaadin/Spring Boot Playground")
@SpringUI
public class VaadinUI extends UI {

    private static final String START_VIEW = "Start";
    private static final String TABLE_VIEW = "Table View";
    private static final String GRAPH_VIEW = "Graph View";

    // We can use either constructor autowiring or field autowiring
    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.MENUBAR_SMALL);
        navigationBar.addComponent(createNavigationButton(START_VIEW, DefaultView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton(TABLE_VIEW, TableView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton(GRAPH_VIEW, GraphView.VIEW_NAME));
        root.addComponent(navigationBar);

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
    }

    public Button createNavigationButton(String s, String viewName) {
        Button button = new Button(s);

        switch (s) {
            case START_VIEW:
                button.setIcon(FontAwesome.HOME);
                break;
            case TABLE_VIEW:
                button.setIcon(FontAwesome.TABLE);
                break;
            case GRAPH_VIEW:
                button.setIcon(FontAwesome.BAR_CHART_O);
                break;

            default:
                button.setIcon(FontAwesome.FILE);
                break;
        }
        button.addStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }
}
