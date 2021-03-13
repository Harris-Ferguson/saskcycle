package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

import java.util.Collections;

@Route(value = "settings", layout = MainLayout.class)
@PageTitle("SaskCycle | Settings")
@Secured("ROLE_USER")
public class SettingsView extends VerticalLayout {

    public SettingsView() {

        CheckboxGroup<String> postCheckbox = new CheckboxGroup<>();
        postCheckbox.setLabel("For posts:");
        postCheckbox.setItems("Email me", "Text me");
        postCheckbox.setValue(Collections.singleton("Email me"));
        postCheckbox.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        CheckboxGroup<String> eventCheckbox = new CheckboxGroup<>();
        eventCheckbox.setLabel("For events:");
        eventCheckbox.setItems("Email me", "Text me");
        eventCheckbox.setValue(Collections.singleton("Email me"));
        eventCheckbox.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        ComboBox<String> timeSelector = new ComboBox<>();
        timeSelector.setLabel("Notify me about events");
        timeSelector.setItems("7 days in advance", "2 days in advance", "24 hours in advance", "12 hours in advance",
                "1 hour in advance");

        add(new H1("Settings"), postCheckbox, eventCheckbox, timeSelector);
    }
}
