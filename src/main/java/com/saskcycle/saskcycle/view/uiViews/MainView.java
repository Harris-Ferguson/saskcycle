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
    Text sub = new Text("Reduce. Reuse. Recycle. We learned this concept in elementary school, but following the three Rs is hard when we factor in transportation and education. SaskCycle aims to help people reuse and recycle, so that green options available to everyone.");
    VerticalLayout subheader = new VerticalLayout(sub);
    subheader.addClassName("sub");

    Text eventDetail = new Text("Browse local reuse and repair events in Saskatoon");
    Icon eIcon = new Icon(VaadinIcon.CALENDAR);
    Button eventButton = new Button("Browse events");
    eventButton.addClassName("reset-button");
    eventButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    eventButton.addClickListener(e -> eventButton.getUI().ifPresent(ui -> ui.navigate("events")));
    VerticalLayout events = new VerticalLayout(eIcon, eventDetail, eventButton);
   // events.setWidth("30%");
    events.setAlignItems(Alignment.CENTER);
    events.addClassName("display-box");

    Text postDetail = new Text("Let people know if you need something or have something to give away");
    Icon pIcon = new Icon(VaadinIcon.PENCIL);
    Button postButton = new Button("Create a post");
    postButton.addClassName("reset-button");
    postButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    postButton.addClickListener(e -> eventButton.getUI().ifPresent(ui -> ui.navigate("Create-Posts")));
    VerticalLayout posts = new VerticalLayout(pIcon, postDetail, postButton);
    //posts.setWidth("30%");
    posts.setAlignItems(Alignment.CENTER);
    posts.addClassName("display-box");

    Text routeDetail = new Text("Plan the easiest route to get what you need or give your resuables a second life");
    Icon rIcon = new Icon(VaadinIcon.MAP_MARKER);
    Button routeButton = new Button("Search all listings");
    routeButton.addClassName("reset-button");
    routeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    routeButton.addClickListener(e -> routeButton.getUI().ifPresent(ui -> ui.navigate("results")));
    VerticalLayout route = new VerticalLayout(rIcon, routeDetail, routeButton);
    //route.setWidth("30%");
    route.setAlignItems(Alignment.CENTER);
    route.addClassName("display-box");

    HorizontalLayout buttonPanel = new HorizontalLayout(eventButton, postButton, routeButton);
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
