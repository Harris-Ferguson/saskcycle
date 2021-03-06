package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("SaskCycle")
public class MainView extends VerticalLayout {

    /**
     * Construct a view to show an account
     */
    public MainView() {


        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        //addClassName("centered-content");

        NativeButton button = new NativeButton("Events");

        button.addClickListener(e -> button.getUI().ifPresent(ui -> ui.navigate("events")));

        add(new H1("SaskCycle"), button);
    }
}
