package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.controller.EventController;
import com.saskcycle.model.Event;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.DeleteEventPreviewComponent;
import com.saskcycle.saskcycle.view.components.EventComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Dial;
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

import javax.annotation.PostConstruct;

@Route(value = "delete-event", layout = MainLayout.class)
@PageTitle("SaskCycle | Event Create")
@Secured("ROLE_ORG")
public class EventDeleteView extends VerticalLayout {

    @Autowired
    private CurrentUserDAOInterface currentAccount;

    @Autowired
    private EventController EC;


    @PostConstruct
    public void EventDeleteView() {
        Button createButton = new Button("Create Event", new Icon(VaadinIcon.PLUS));
        createButton.addClickListener(
                e -> createButton.getUI().ifPresent(ui -> ui.navigate("create-event")));

        Grid<Post> newGrid = initGrid();
        newGrid.addItemClickListener(event -> {
            System.out.println(event.getItem().title);
            Event e = EC.getEventByTitle(event.getItem().title);
            System.out.println(e.title);

            Dialog dialog = showEventPreview(e);
            dialog.open();
        });


        add(new H1("Your events"), createButton, newGrid);
    }

    private Grid<Post> initGrid() {

        Grid<Post> newGrid = new Grid<>();
        newGrid.setItems(currentAccount.getCurrentAccount().getPosts());
        newGrid.addComponentColumn(EventComponent::new);

        return newGrid;

    }

    private Dialog showEventPreview(Event saskcycleEvent) {
        Dialog d = new Dialog();

        Button deleteEventButton = new Button("Delete this event");
        deleteEventButton.addClickListener(event -> {
            EC.deleteEvent(saskcycleEvent);
            currentAccount.deleteEvent(saskcycleEvent);
        });
        d.add(deleteEventButton);


        return d;
    }
}
