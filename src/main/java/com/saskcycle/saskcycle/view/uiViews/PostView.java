package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "posts", layout = MainLayout.class)
@PageTitle("SaskCycle | Post")
public class PostView extends VerticalLayout {

  public PostView() {

    // addClassName("filter-view");
    Button createButton = new Button("Create Post", new Icon(VaadinIcon.PLUS));
    createButton.addClickListener(
        e -> createButton.getUI().ifPresent(ui -> ui.navigate("Create-Posts")));
    add(new H1("Posts"), createButton);
  }
}
