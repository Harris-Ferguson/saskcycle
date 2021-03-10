package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

//@Route("Create Post")
@Route(value = "Create Posts", layout = PostCreateLayout.class)
public class PostCreateView extends VerticalLayout{

    public PostCreateView(){

        add(new H1("Create Post"));
    }
}
