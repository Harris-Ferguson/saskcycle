package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.layouts.PostCreateLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "delete-event", layout = PostCreateLayout.class)
@PageTitle("SaskCycle | Event Create")
@Secured("ROLE_ORG")
public class EventDeleteView extends VerticalLayout {

    public EventDeleteView() {
        // addClassName("filter-view");
        Button createButton = new Button("Create Event", new Icon(VaadinIcon.PLUS));
        createButton.addClickListener(
                e -> createButton.getUI().ifPresent(ui -> ui.navigate("create-event")));
        add(new H1("Events"), createButton);
    }
}
