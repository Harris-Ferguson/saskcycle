package com.saskcycle.saskcycle.view.components;

import com.saskcycle.model.Event;

public class EventInfoComponent extends ClickedInfo {

    /**
     * Constructs a dialog box to display info about an event in the calendar
     *
     * @param saskcycleEvent the event that was clicked in the calendar
     */
    public EventInfoComponent(Event saskcycleEvent) {
        super(saskcycleEvent);
    }
}
