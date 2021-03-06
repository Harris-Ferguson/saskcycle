package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "posts", layout = MainLayout.class)
@PageTitle("SaskCycle | Posts")
public class PostView extends VerticalLayout {

    public PostView() {

        //addClassName("filter-view");
        add(new H1("Post View"));
    }
}
