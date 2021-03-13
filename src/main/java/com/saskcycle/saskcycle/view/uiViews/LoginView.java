package com.saskcycle.saskcycle.view.uiViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="login")
@PageTitle("SaskCycle | Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm form = new LoginForm();

    public LoginView(){
        createLoginForm();
    }

    private void createLoginForm(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        form.setAction("login");

        add(new H1("Sask Cycle Login"), form);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
            .getQueryParameters()
            .getParameters()
            .containsKey("error")){
            form.setError(true);
        }
    }
}
