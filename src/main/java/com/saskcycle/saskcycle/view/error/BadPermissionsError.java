package com.saskcycle.saskcycle.view.error;

import com.saskcycle.saskcycle.view.uiViews.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("/error/bad-permission")
public class BadPermissionsError extends VerticalLayout {
    public BadPermissionsError() {
        H1 error = new H1("Uh Oh!");
        Paragraph message = new Paragraph("Looks like you don't have the right permissions to view this page");
        HorizontalLayout options = new HorizontalLayout();
        RouterLink homepage = new RouterLink("Go Home", MainView.class);
        options.add(homepage);

        add(error, message, options);
    }
}
