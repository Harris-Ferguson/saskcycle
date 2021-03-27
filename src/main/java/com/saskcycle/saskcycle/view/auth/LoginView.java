package com.saskcycle.saskcycle.view.auth;

import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route(value = "login", layout = MainLayout.class)
@PageTitle("SaskCycle | Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

  private final LoginForm form = new LoginForm();

  public LoginView() {
    createLoginForm();
  }

  private void createLoginForm() {
    addClassName("login-view");
    setSizeFull();
    setAlignItems(Alignment.CENTER);
    setJustifyContentMode(JustifyContentMode.CENTER);


    form.setAction("login");

    add(new H1("SaskCycle Login"), form, new RouterLink("Register", RegisterView.class));

  }

  @Override
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
      form.setError(true);
    }
  }
}
