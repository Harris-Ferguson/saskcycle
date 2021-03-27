package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.model.Tags;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("SaskCycle")
public class MainView extends VerticalLayout {

  /** Construct a view to show an account */
  public MainView() {

    // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
    // addClassName("centered-content");

    H1 heading = new H1("SaskCycle makes it easy to do the right thing");
    Text sub = new Text("Reduce. Reuse. Recycle. We learned this concept in elementary school, but following the three Rs is hard when we factor in transportation and education. SaskCycle aims to help people reuse and recycle, so that green options are available to everyone.");
    VerticalLayout subheader = new VerticalLayout(sub);
    subheader.addClassName("sub");

    Button eventButton = new Button("Browse events");
    eventButton.addClassName("reset-button");
    eventButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    eventButton.addClickListener(e -> eventButton.getUI().ifPresent(ui -> ui.navigate("events")));

    Button postButton = new Button("Create a post");
    postButton.addClassName("reset-button");
    postButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    postButton.addClickListener(e -> eventButton.getUI().ifPresent(ui -> ui.navigate("Create-Posts")));


    Button listingsButton = new Button("Search all listings");
    listingsButton.addClassName("reset-button");
    listingsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    listingsButton.addClickListener(e -> listingsButton.getUI().ifPresent(ui -> ui.navigate("results")));

    HorizontalLayout buttonPanel = new HorizontalLayout(eventButton, postButton, listingsButton);
    //buttonPanel.setAlignItems(Alignment.CENTER);

    add(heading, subheader, buttonPanel, showTags());
    setAlignItems(Alignment.CENTER);
  }

  private VerticalLayout showTags() {


    H2 availableTags = new H2("Items in our community");

    HorizontalLayout layout = new HorizontalLayout();


    for (String tag : Tags.getTagNames()) {
     Span t = new Span(tag);
      layout.add(t);
    }


    VerticalLayout display = new VerticalLayout(availableTags, layout);
    display.setAlignItems(Alignment.CENTER);
    display.addClassName("tags");

    return display;
  }
}
