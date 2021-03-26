package com.saskcycle.saskcycle.view.components;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.controller.EventController;
import com.saskcycle.model.Event;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.fullcalendar.Timezone;

import java.time.ZoneId;

public class DeleteEventPreviewComponent extends ClickedInfo {

    @Autowired
    private CurrentUserDAOInterface currentAccount;

    @Autowired
    private EventController EC;

    Timezone saskCycleTimezone;

    Event saskcycleEvent;

    public DeleteEventPreviewComponent(Event saskcycleEvent){

        super(saskcycleEvent);

        this.saskcycleEvent = saskcycleEvent;
        saskCycleTimezone = new Timezone(ZoneId.of("Canada/Saskatchewan"));

        Button deleteEventButton = new Button("Delete this event");
        deleteEventButton.addClickListener(event -> {
            System.out.println(EC.getAllEvents());
        });
        add(deleteEventButton);

    }

}
