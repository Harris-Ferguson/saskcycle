package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.controller.EventController;
import com.saskcycle.model.Event;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.DeleteEventPreviewComponent;
import com.saskcycle.saskcycle.view.components.EventComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
            Event e = EC.getEventByTitle(event.getItem().title);

            //Dialog dialog = showEventPreview(e);
            Dialog dialog = new DeleteEventPreviewComponent(e);
            dialog.open();
        });


        add(new H1("Your events"), createButton, newGrid);
    }

    private Grid<Post> initGrid() {

        Grid<Post> newGrid = new Grid<>();
        newGrid.setItems(currentAccount.getCurrentAccount().getPosts());
        newGrid.addComponentColumn(this::displayEvent);

        return newGrid;

    }

    private Dialog showEventPreview(Event saskcycleEvent) {
        Dialog d = new Dialog();

        Button deleteEventButton = new Button("Delete this event");
        deleteEventButton.addClickListener(event -> {
            EC.deleteEvent(saskcycleEvent);
            currentAccount.deleteEvent(saskcycleEvent);
            UI.getCurrent().getPage().reload();
        });
        d.add(deleteEventButton);


        return d;
    }

    private Div displayEvent(Post event) {

        Div d = new Div();
        H3 title = new H3(event.title);

        Paragraph desc = new Paragraph(event.description);

        title.addClassName("posts");
        desc.addClassName("posts");

        Button deleteEventButton = new Button("Delete this event");
        deleteEventButton.addClickListener(e -> {
            Dialog confirmDialog = confirmationDialog(event);
            confirmDialog.open();
//            EC.deleteEvent((Event) event);
//            currentAccount.deleteEvent((Event) event);
//            UI.getCurrent().getPage().reload();
//            System.out.println(event.title);
        });
        d.add(deleteEventButton);

        d. add(title, desc);

        return d;
    }

    private Dialog confirmationDialog(Post event) {
        Dialog confirmationDialog = new Dialog();

        H2 question = new H2("Are you sure you want to delete this event?");
        Button delButton = new Button("Delete");
        Button cancelButton = new Button("Cancel");

        delButton.addClickListener(e -> {
            EC.deleteEvent((Event) event);
            currentAccount.deleteEvent((Event) event);
            UI.getCurrent().getPage().reload();
        });

        cancelButton.addClickListener(e -> {
            confirmationDialog.close();
        });

        confirmationDialog.add(question, new HorizontalLayout(cancelButton, delButton));

        return confirmationDialog;
    }
}
