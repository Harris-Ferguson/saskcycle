package com.saskcycle.saskcycle.view;

import com.saskcycle.DAO.UserDAOInterface;
import com.saskcycle.model.Account;
import com.saskcycle.model.UserNotificationSettings;
import com.saskcycle.saskcycle.security.SecurityUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

@Route(value = "settings", layout = MainLayout.class)
@PageTitle("SaskCycle | Settings")
@Secured("ROLE_USER")
public class SettingsView extends Composite {

    private static final String emailString = "Email me";
    private static final String textString = "Text me";

    @Autowired
    private UserDAOInterface userDAO;

    @Override
    protected Component initContent() {
        CheckboxGroup<String> postCheckbox = new CheckboxGroup<>();
        postCheckbox.setLabel("For posts:");
        postCheckbox.setItems(emailString, textString);
        postCheckbox.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        return new VerticalLayout (new H1("Settings"),
                postCheckbox,
                new Button("Submit", event -> changeSettings(
                        postCheckbox
                ))
        );
    }

    private void changeSettings(CheckboxGroup<String> settings) {
        boolean wantsEmail = settings.isSelected(emailString);
        boolean wantsText = settings.isSelected(textString);
        userDAO.updateSettings(wantsEmail, wantsText);
        Notification.show("Saved!");
    }
}
