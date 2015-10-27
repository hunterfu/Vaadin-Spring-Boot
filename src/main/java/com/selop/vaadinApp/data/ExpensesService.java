package com.selop.vaadinApp.data;

import com.selop.vaadinApp.domain.Expense;
import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adapted from Vaadin addressbook example.
 * Fake backend generator.
 *
 * @author selop
 */
public class ExpensesService {

    // Create dummy data by randomly combining first and last names
    static String[] expense = { "Burger", "Pizza", "Tablet", "Phone", "Game",
            "Cat", "Dog", "Puzzle", "Groceries", "Shoes", "Cup Of Coffee" };
    static String[] category = { "Eat & Drink", "Animal Stuff", "Condo", "Gaming",
            "Other", "Public Transport", "Donations"};

    private static ExpensesService instance;

    public static ExpensesService getInstance() {
        if(instance != null)
            return instance;
        else
            return createDemoService();
    }

    public static ExpensesService createDemoService() {
        if (instance == null) {

            final ExpensesService service = new ExpensesService();

            Random r = new Random(0);
            Calendar cal = Calendar.getInstance();
            for (int i = 0; i < 100; i++) {
                Expense expense = new Expense();
                expense.setExpenseName(ExpensesService.expense[r.nextInt(ExpensesService.expense.length)]);
                expense.setCategory(category[r.nextInt(category.length)]);
                expense.setExpenseValue(r.nextInt(100));
                expense.setBuyDate(cal.getTime());
                service.save(expense);
            }
            instance = service;
        }

        return instance;
    }

    private HashMap<Long, Expense> expenses = new HashMap<>();
    private long nextId = 0;

    public synchronized List<Expense> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        for (Expense expense : expenses.values()) {
            try {
                boolean passesFilter = ((stringFilter == null) || stringFilter.isEmpty())
                        || expense.toString().toLowerCase()
                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(expense.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ExpensesService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Expense>() {
            @Override
            public int compare(Expense o1, Expense o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized long count() {
        return expenses.size();
    }

    public synchronized void delete(Expense value) {
        expenses.remove(value.getId());
    }

    public synchronized void save(Expense entry) {

        if (entry.getId() == null)
            entry.setId(nextId++);

        try {
            entry = ((Expense) BeanUtils.cloneBean(entry));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        expenses.put(entry.getId(), entry);
    }

}
