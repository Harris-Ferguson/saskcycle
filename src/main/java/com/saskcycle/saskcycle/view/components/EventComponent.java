package com.saskcycle.saskcycle.view.components;

import com.saskcycle.model.Event;
import com.saskcycle.model.Post;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;

public class EventComponent extends Div {

    public EventComponent(Post event) {
        setEvent(event);



    }

    /**
     * Formats the post's components into a readable and uniform display
     *
     */
    private void setEvent(Post event) {

        H3 title = new H3(event.title);

        H5 dist = new H5(event.location + " away"); // | " + posts.get(i).datePosted);
        Paragraph desc = new Paragraph(event.description);

        title.addClassName("posts");
        desc.addClassName("posts");

        add(title, dist, desc);
    }
}
