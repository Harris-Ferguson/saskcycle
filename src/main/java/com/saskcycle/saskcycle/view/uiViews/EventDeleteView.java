package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.controller.EventController;
import com.saskcycle.model.Event;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.DeleteEventPreviewComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createButton.addClassName("reset-button");
        createButton.addClickListener(
                e -> createButton.getUI().ifPresent(ui -> ui.navigate("create-event")));

        Grid<Event> newGrid = initGrid();
    newGrid.addItemClickListener(
        event -> {
          System.out.println(event.getItem().getTitle());
          Event e = EC.getEventByID(event.getItem().id);
          Dialog dialog = new DeleteEventPreviewComponent(e);
          dialog.open();
        });

        add(new H1("Your Events"), createButton, newGrid);
    }

    private Grid<Event> initGrid() {

        Grid<Event> newGrid = new Grid<>();
        List<Event> posts = new ArrayList<>();
        for (String eventId : currentAccount.getCurrentAccount().getEventIds())
        {
            posts.add(EC.getEventByID(eventId));
        }
        newGrid.setItems(posts);
        newGrid.addComponentColumn(this::displayEvent);

        return newGrid;

    }

    private Div displayEvent(Post event) {

        Div d = new Div();
        H3 title = new H3(event.title);

        //H3 datePosted = new H3(new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(event.datePosted));

        Paragraph desc = new Paragraph(event.description);

        title.addClassName("posts");
        desc.addClassName("posts");

        Button deleteEventButton = new Button("Delete this event", new Icon(VaadinIcon.TRASH));
        deleteEventButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteEventButton.addClassName("reset-button");
        deleteEventButton.addClickListener(e -> {
            Dialog confirmDialog = confirmationDialog(event);
            confirmDialog.open();
        });

        VerticalLayout infoPanel = new VerticalLayout(title, desc);
        infoPanel.setWidth("75%");

        HorizontalLayout layout = new HorizontalLayout(infoPanel, deleteEventButton);
        layout.setAlignItems(Alignment.CENTER);

        d. add(layout);

        return d;
    }

    private Dialog confirmationDialog(Post event) {
        Dialog confirmationDialog = new Dialog();

        H2 question = new H2("Are you sure you want to delete this event?");
        Button delButton = new Button("Delete");
        delButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delButton.addClassName("reset-button");
        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.addClassName("cancel-button");

        delButton.addClickListener(e -> {
            EC.deleteEvent((Event) event);
            currentAccount.deleteEvent((Event) event);
            UI.getCurrent().getPage().reload();
            new Notification("Event successfully deleted", 3000).open();
        });

        cancelButton.addClickListener(e -> confirmationDialog.close());

        HorizontalLayout buttonPanel = new HorizontalLayout(cancelButton, delButton);
        buttonPanel.setWidth("100%");
        buttonPanel.setJustifyContentMode(JustifyContentMode.CENTER);

        confirmationDialog.add(question, buttonPanel);

        return confirmationDialog;
    }
}
