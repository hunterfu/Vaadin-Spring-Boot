package com.selop.vaadinApp.view;

import com.selop.vaadinApp.data.ExpensesService;
import com.selop.vaadinApp.domain.Expense;
import com.selop.vaadinApp.form.ExpenseForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;

/**
 * TableView to display the dummy data provided by (@link ExpensesService)
 *
 * @author selop
 */
@UIScope
@SpringView(name = TableView.VIEW_NAME)
public class TableView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "table";

    ExpensesService service = ExpensesService.createDemoService();

    TextField filter = new TextField();
    Grid contactList = new Grid();
    Button newContact = new Button("New entry");

    // ContactForm is an example of a custom component class
    ExpenseForm expenseForm = new ExpenseForm(this);

    @PostConstruct
    void init() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        filter.setInputPrompt("Filter expenses...");
        filter.addTextChangeListener(e -> refreshData(e.getText()));

        newContact.addClickListener(e -> expenseForm.edit(new Expense()));

        contactList.setContainerDataSource(new BeanItemContainer<>(Expense.class));
        contactList.setColumnOrder("expenseName", "expenseValue", "category", "buyDate");
        contactList.removeColumn("id");
        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
        contactList.addSelectionListener(e -> expenseForm.edit((Expense) contactList.getSelectedRow()));

        refreshData();
    }

    public void refreshData() {
        refreshData(filter.getValue());
    }

    private void refreshData(String stringFilter) {
        contactList.setContainerDataSource(new BeanItemContainer<>(Expense.class, service.findAll(stringFilter)));
    }

    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(filter, newContact);
        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);

        VerticalLayout left = new VerticalLayout(actions, contactList);
        left.setSizeFull();
        contactList.setSizeFull();
        left.setExpandRatio(contactList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, expenseForm);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);

        addComponent(mainLayout);
    }

    public Grid getContactList(){
        return this.contactList;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }

    public VaadinUI getUI() {
        return (VaadinUI) super.getUI();
    }
}