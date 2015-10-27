package com.selop.vaadinApp.domain;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by selop on 21/10/15.
 */
public class Expense implements Serializable, Cloneable {
    private Long id;

    private String expenseName = "";
    private String category = "";
    private Integer expenseValue = 0;
    private Date buyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(Integer expenseValue) {
        this.expenseValue = expenseValue;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    @Override
    public Expense clone() throws CloneNotSupportedException {
        try {
            return ((Expense) BeanUtils.cloneBean(this));
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "Expense{" + "id=" + id +
                ", expenseName=" + expenseName +
                ", category=" + category +
                ", expenseValue=" + expenseValue +
                ", buyDate=" + buyDate + '}';
    }
}
