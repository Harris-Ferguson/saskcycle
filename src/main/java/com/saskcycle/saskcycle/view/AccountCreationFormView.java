package com.saskcycle.saskcycle.view;

import com.saskcycle.DAO.UserDAOInterface;
import com.saskcycle.model.Account;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.springframework.beans.factory.annotation.Autowired;


public class AccountCreationFormView extends FormLayout implements BeforeEnterObserver {

    @Autowired
    private UserDAOInterface accountService;
    Binder<Account> binder = new BeanValidationBinder<>(Account.class);

    private TextField username = new TextField("Username");
    private TextField email = new TextField("email");
    private TextField password = new TextField("Password");
    private TextField confirmPassword = new TextField("Confirm Password");
    private Button submit = new Button("Submit");

    public AccountCreationFormView() {
        HorizontalLayout button = new HorizontalLayout(submit);
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(username, email, password, confirmPassword, button);
        submit.addClickListener(event -> createAccount());
        binder.bindInstanceFields(this);
    }

    private void createAccount() {
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

    }
}
