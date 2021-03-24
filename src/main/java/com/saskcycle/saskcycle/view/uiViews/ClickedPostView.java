package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAO;
import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.DAO.UserDAOInterface;
import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.security.SecurityUtils;
import com.saskcycle.saskcycle.view.components.MapComponent;
import com.saskcycle.saskcycle.view.layouts.ClickedPostLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

@Route(value = "clickedPost", layout = ClickedPostLayout.class)
public class ClickedPostView extends VerticalLayout implements HasUrlParameter<String>, AfterNavigationObserver {

    private H1 title;
    private H1 title2;

    private String text;
    private String id;
    private Post post;
    private Double latitude, longitude;

    private MapComponent map;

    H4 location;
    H4 postTime;

    private Paragraph paragraph;

    @Autowired
    SearchController SC;
    @Autowired
    CurrentUserDAOInterface account;

//    @Autowired
//    UserDAOInterface userDAD;

    public ClickedPostView() {

        title = new H1();
        title2 = new H1();
        paragraph = new Paragraph();

        Button wishlistButton = new Button("Add to wishlist", new Icon(VaadinIcon.STAR));
        wishlistButton.addClickListener(e -> {
                if (SecurityUtils.isUserLoggedIn()){
                    if(account.getCurrentAccount().getWishlist().contains(post.id))
                    {
                        Notification notification = new Notification("Item is on your wishlist already!",3000);
                        notification.open();
                    }
                    else
                    {
                        account.updateWishlist(post.id);
                        Notification notification = new Notification("Item added to wishlist!",3000);
                        notification.open();
                    }

                }

                else
                    {
                        wishlistButton.getUI().ifPresent(ui -> ui.navigate("login"));
                    }
                });
        wishlistButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        wishlistButton.addClassName("wishlist-button");
        HorizontalLayout heading = new HorizontalLayout(title, wishlistButton);
        heading.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        // Contains additional info about the post
        VerticalLayout sidePanel = new VerticalLayout();
        sidePanel.setAlignItems(Alignment.CENTER);

        sidePanel.setWidth("400px");
        //location = new H4();
        postTime = new H4();
        //sidePanel.getStyle().set("border", "1px solid #eeeeee");
        Button mapButton = new Button("Show the map", new Icon(VaadinIcon.MAP_MARKER));
        mapButton.addClickListener(e -> {
//            sidePanel.add(showMap());
            map.addMarker(latitude,longitude,text);
            UI.getCurrent().getPage().reload();
        });
        mapButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        mapButton.addClassName("map-button");
        HorizontalLayout heading2 = new HorizontalLayout(title2, mapButton);
        heading.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        // TODO: Matthew's story
        H4 contact = new H4("For more information, contact test_email@email.com");

        VerticalLayout desc = new VerticalLayout();
        desc.add(paragraph);
        desc.setWidth("600px");

        sidePanel.add(wishlistButton,mapButton,showMap(), postTime, contact);

        add(new HorizontalLayout(new VerticalLayout(title, desc), sidePanel));
    }

    private VerticalLayout showMap() {

        VerticalLayout mapContainer = new VerticalLayout();

        mapContainer.setHeight("400px");
        mapContainer.setWidth("400px");
        map = new MapComponent();

//        latitude = post.latitude;
//        longitude = post.longitude;
//        map.addMarker(latitude, longitude, text);
        mapContainer.add(map);
        return mapContainer;

    }

    /**
     * Resets the UI depending on what post was clicked
     * @param afterNavigationEvent
     */
    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

        post = SC.getPostByID(id);
        title.setText(post.title);
        paragraph.setText(post.description);
        latitude = post.latitude;
        longitude = post.longitude;

        //location.setText(post.location);
        title.setText(post.getTitle());
        paragraph.setText(post.getDescription());
//        location.setText(post.getPostalCode());
        postTime.setText("Posted at " + new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(post.datePosted));

        System.out.println(latitude + " " + longitude);
//        map.addMarker(latitude, longitude, text);
    }

    /**
     * Sets the ID of the post that was clicked
     * @param beforeEvent
     * @param postId clicked post's id number
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, String postId) {
        id = postId;
    }
}
