package com.selop.vaadinApp.form;

import com.selop.vaadinApp.data.ExpensesService;
import com.selop.vaadinApp.domain.Expense;
import com.selop.vaadinApp.view.TableView;
import com.selop.vaadinApp.view.VaadinUI;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to bind data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class ExpenseForm extends FormLayout {

    Button save = new Button("Save", this::save);
    Button cancel = new Button("Cancel", this::cancel);

    TextField expenseField = new TextField("Expense:");
    TextField valueField = new TextField("Value:");
    TextField categoryField = new TextField("Category:");

    DateField buyDate = new DateField("Bought:");

    Expense expense;

    TableView view;

    // Easily bind forms to beans and manage validation and buffering
    BeanFieldGroup<Expense> formFieldBindings;

    public ExpenseForm(TableView view) {
        this.view = view;
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        /* Highlight primary actions.
         *
         * With Vaadin built-in styles you can highlight the primary save button
         * and give it a keyboard shortcut for a better UX.
         */
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setSpacing(true);

        addComponents(actions, expenseField, valueField, categoryField, buyDate);
    }

    public void save(Button.ClickEvent event) {
        try {
            // Commit the fields from UI to DAO
            formFieldBindings.commit();

            // Save DAO to backend with direct synchronous service API
            ExpensesService.getInstance().save(expense);

            String msg = String.format("Saved '%s %s'.", expense.getExpenseName(), expense.getExpenseValue());
            Notification.show(msg, Notification.Type.TRAY_NOTIFICATION);

            view.refreshData();
        } catch (FieldGroup.CommitException e) {}
    }

    public void cancel(Button.ClickEvent event) {
        // Place to call business logic.
        Notification.show("Cancelled", Notification.Type.TRAY_NOTIFICATION);
        view.getContactList().select(null);
    }

    public void edit(Expense contact) {
        this.expense = contact;
        if(contact != null) {
            // Bind the properties of the expense POJO to fields in this form
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(contact, this);
            expenseField.focus();
        }
        setVisible(contact != null);
    }

    @Override
    public VaadinUI getUI() {
        return (VaadinUI) super.getUI();
    }

}
