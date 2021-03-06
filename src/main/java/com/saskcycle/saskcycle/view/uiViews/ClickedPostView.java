package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.AccountController;
import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.security.SecurityUtils;
import com.saskcycle.saskcycle.view.components.MapComponent;

import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

@Route(value = "clickedPost", layout = MainLayout.class)
@PageTitle("SaskCycle | Clicked Post")
public class ClickedPostView extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    private H5 postType;

    private H1 title;

    private String id;
    private Post post;
    private Double latitude, longitude;
    private VerticalLayout mapHolder;

    private MapComponent map;

    private H4 postTime;
    private H4 email;

    private Paragraph paragraph;

    @Autowired
    SearchController SC;
    @Autowired
    AccountController account;

    /**
     * Shows the full information for a post that was clicked in the results feed
     */
    public ClickedPostView() {

        postType = new H5();
        title = new H1();
        paragraph = new Paragraph();

        Button wishlistButton = new Button("Add to wishlist", new Icon(VaadinIcon.STAR));
        wishlistButton.addClickListener(e -> {
            if (SecurityUtils.isUserLoggedIn()) {
                if (account.getCurrentAccount().getWishlist().contains(post.id)) {
                    Notification notification = new Notification("Item is on your wishlist already!", 3000);
                    notification.open();
                } else {
                    account.updateWishlist(post.id);
                    Notification notification = new Notification("Item added to wishlist!", 3000);
                    notification.open();
                }

            } else {
                wishlistButton.getUI().ifPresent(ui -> ui.navigate("login"));
            }
        });
        wishlistButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        wishlistButton.addClassName("wishlist-button");

        // Contains additional info about the post
        VerticalLayout sidePanel = new VerticalLayout();
        sidePanel.setAlignItems(Alignment.CENTER);

        postTime = new H4();
        email = new H4();
        sidePanel.getStyle().set("border", "1px solid #eeeeee");

        // Takes user to the page where they can plan their route to the post's location
        Button goToRouteButton = new Button("Get Route Plan", new Icon(VaadinIcon.MAP_MARKER));
        goToRouteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        goToRouteButton.addClassName("reset-button");

        // Constructing a post view based on what's clicked is still under construction
        goToRouteButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(MapView.class, post.getPostalCode()));
            UI.getCurrent().getPage().reload();
        });

        setMapHolder();
        sidePanel.add(wishlistButton, mapHolder, goToRouteButton, postTime, postTime, email);
        sidePanel.setWidth("400px");

        VerticalLayout mainPanel = new VerticalLayout(postType, title, paragraph);
        mainPanel.setWidth("75%");
        HorizontalLayout fullLayout = new HorizontalLayout(mainPanel, sidePanel);
        fullLayout.setWidth("100%");
        add(fullLayout);

    }

    /**
     * Creates a component for the map display
     */
    private void setMapHolder() {

        mapHolder = new VerticalLayout();
        mapHolder.setHeight("400px");
        mapHolder.setWidth("400px");
    }

    /**
     * Sets the email information
     * @param post the post for which the email will be displayed
     */
    public void emailPrivate(Post post) {
        email.setText("For more information, contact:" + post.getContactEmail());
    }

    /**
     * Sets the ID of the post that was clicked
     * @param beforeEvent the event preceding afterNavition time (Vaadin Construct)
     * @param postId      clicked post's id number
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, String postId) {
        id = postId;
    }

    /**
     * Updates the UI based on what post was selected by the user
     * @param beforeEnterEvent the event preceding afterNavition time (Vaadin Construct)
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Assign all attributes from posts to view
        post = SC.getPostByID(id);
        if(post.getPostType()){
            postType.setText("Giving away");
        }
        else {
            postType.setText("Looking for");
        }

        title.setText(post.title);
        paragraph.setText(post.description);
        latitude = post.latitude;
        longitude = post.longitude;
        title.setText(post.getTitle());
        paragraph.setText(post.getDescription());
        postTime.setText("Posted at "
                + new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(post.datePosted));
        map = new MapComponent(latitude, longitude, "Label");
        mapHolder.add(map);
        post = SC.getPostByID(id);
        title.setText(post.title);
        paragraph.setText(post.description);
        postTime.setText("Posted at " + new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(post.datePosted));
        if (post.isContactEmailPresent()) {
            if (post.isPublic()) {
                email.setText("For more information, contact:" + post.getContactEmail());
            }
            // TODO: Check current user role to make sure only accounts can see email if post is marked as such
            if (SecurityUtils.isUserLoggedIn()) {
                emailPrivate(post);
            }
        }
    }
}
