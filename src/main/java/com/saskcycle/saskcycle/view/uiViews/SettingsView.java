package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@Route(value = "settings", layout = MainLayout.class)
@PageTitle("SaskCycle | Settings")
@Secured("ROLE_USER")
public class SettingsView extends Composite {

  private static final String emailString = "Email me";
  private static final String textString = "Text me";

  @Autowired private CurrentUserDAOInterface currentAuthDAO;

  @Override
  protected Component initContent() {
    CheckboxGroup<String> postCheckbox = new CheckboxGroup<>();
    postCheckbox.setItems(emailString, textString);
    postCheckbox.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

    // set the boxes based on user settings
    if (currentAuthDAO.getEmailSetting()) {
      postCheckbox.select(emailString);
    }
    if (currentAuthDAO.getTextSetting()) {
      postCheckbox.select(textString);
    }

    return new VerticalLayout(
        new H1("Settings"),
        postCheckbox,
        new Button("Save", event -> changeSettings(postCheckbox)));
  }

  private void changeSettings(CheckboxGroup<String> settings) {
    boolean wantsEmail = settings.isSelected(emailString);
    boolean wantsText = settings.isSelected(textString);
    currentAuthDAO.updateSettings(wantsEmail, wantsText);
    Notification.show("Saved!");
  }
}
