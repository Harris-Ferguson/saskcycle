package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("register")
public class RegisterView extends Composite {

    @Override
    protected Component initContent() {
        TextField username = new TextField("Username");
        TextField email = new TextField("Email");
        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Confirm Password");
        return new VerticalLayout(
                new H2("Register"),
                username,
                email,
                password1,
                password2,
                new Button("Register", event -> register(
                        username.getValue(),
                        email.getValue(),
                        password1.getValue(),
                        password2.getValue()
                ))
        );
    }

    private void register(String username, String email, String password1, String password2){

    }
}
