package com.saskcycle.saskcycle.view.components;

import com.saskcycle.model.Post;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class PostComponent extends VerticalLayout {

    public PostComponent(List<Post> posts){

        setAlignItems(FlexComponent.Alignment.STRETCH);
        setWidth("100%");
        setSpacing(false);

        for (int i = 0; i < posts.size(); i++) {
            VerticalLayout component = new VerticalLayout();
            component.add(new H3(posts.get(i).title));

            H5 dist = new H5(posts.get(i).location + " away" ); // | " + posts.get(i).datePosted);
            component.add(dist);
            Paragraph desc = new Paragraph(posts.get(i).description);
            desc.addClassName("para");
            component.add(desc);
            component.add(formatTags(posts, i));


            add(component);
            component.getStyle().set("border", "1px solid #eeeeee");
        }
    }

    private HorizontalLayout formatTags(List<Post> posts, int index){

        HorizontalLayout tagGroup = new HorizontalLayout();

        for (int i = 0; i < posts.get(index).tags.size(); i++) {
            Button component = new Button(posts.get(index).tags.get(i));
            tagGroup.add(component);
        }
        return tagGroup;

    }
}
