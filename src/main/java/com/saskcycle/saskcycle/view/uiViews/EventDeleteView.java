package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.controller.EventController;
import com.saskcycle.model.Event;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.DeleteEventPreviewComponent;
import com.saskcycle.saskcycle.view.components.EventInfoComponent;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.PostCreateLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.stefan.fullcalendar.Timezone;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.time.ZoneId;

@Route(value = "delete-event", layout = PostCreateLayout.class)
@PageTitle("SaskCycle | Event Create")
@Secured("ROLE_ORG")
public class EventDeleteView extends VerticalLayout {

    @Autowired
    private CurrentUserDAOInterface currentAccount;

    @Autowired
    private EventController EC;

    @PostConstruct
    public void EventDeleteView() {
        // addClassName("filter-view");
        Button createButton = new Button("Create Event", new Icon(VaadinIcon.PLUS));
        createButton.addClickListener(
                e -> createButton.getUI().ifPresent(ui -> ui.navigate("create-event")));

        Grid<Post> newGrid = new Grid<>();
        newGrid.setItems(currentAccount.getCurrentAccount().getPosts());
        newGrid.addComponentColumn(PostComponent::new);

        newGrid.addItemClickListener(event -> {
            Event e = EC.getEventByTitle(event.getItem().title);
            Dialog dialog = new DeleteEventPreviewComponent(e);
            dialog.open();
            //System.out.println(event.getItem().title);
        });


        add(new H1("Events"), createButton, newGrid);

    }
}
