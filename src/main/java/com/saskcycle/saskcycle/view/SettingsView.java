package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "settings", layout = MainLayout.class)
@Secured("ROLE_USER")
public class SettingsView extends VerticalLayout {

    public SettingsView() {
        add(new H1("Settings"));
    }
}
