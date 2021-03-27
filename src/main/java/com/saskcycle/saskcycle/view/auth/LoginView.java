package com.saskcycle.saskcycle.view.auth;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route(value = "login")
@PageTitle("SaskCycle | Login")
@CssImport(value = "./styles/LoginViewStyles.css", themeFor = "vaadin-login-form-wrapper")
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

    add(new H1("Sask Cycle Login"), form, new RouterLink("Register", RegisterView.class));
  }

  @Override
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
      form.setError(true);
    }
  }
}
