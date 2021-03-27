package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.security.SecurityUtils;
import com.saskcycle.saskcycle.view.components.MapComponent;

import com.saskcycle.saskcycle.view.layouts.MainLayout;
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
import org.springframework.security.access.annotation.Secured;

import java.text.SimpleDateFormat;

@Route(value = "clickedPost", layout = MainLayout.class)
public class ClickedPostView extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    private H1 title;
    private H1 title2;

    private String id;
    private Post post;
    private Double latitude, longitude;

    private MapComponent map;

    H4 postTime;
    H4 email;

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
        postTime = new H4();
        email = new H4();
        //sidePanel.getStyle().set("border", "1px solid #eeeeee");
        sidePanel.getStyle().set("border", "1px solid #eeeeee");
        heading.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        Button goToRouteButton = new Button("Get Route Plan", new Icon(VaadinIcon.MAP_MARKER));
        goToRouteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        goToRouteButton.addClassName("reset-button");

        // Constructing a post view based on what's clicked is still under construction
        goToRouteButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(MapView.class, post.getPostalCode()));
            UI.getCurrent().getPage().reload();
        });

        //H4 contact = new H4("For more information, contact: "+ email);

        VerticalLayout desc = new VerticalLayout();
        desc.add(paragraph);
        desc.setWidth("600px");

        //sidePanel.add(wishlistButton, postTime, email);
        sidePanel.add(wishlistButton, showMap(), goToRouteButton, postTime, postTime, email);

        add(new HorizontalLayout(new VerticalLayout(title, desc), sidePanel));
    }

    private VerticalLayout showMap() {

        VerticalLayout mapContainer = new VerticalLayout();
        mapContainer.setHeight("400px");
        mapContainer.setWidth("400px");
        map = new MapComponent();
        mapContainer.add(map);
        return mapContainer;

    }

    public void emailPrivate(Post post){
        email.setText("For more information, contact:" + post.getContactEmail());


    }

    /**
     * Sets the ID of the post that was clicked
     * @param beforeEvent the event preceding afterNavition time (Vaadin Construct)
     * @param postId clicked post's id number
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, String postId) {
        id = postId;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Assign all attributes from posts to view
        post = SC.getPostByID(id);
        title.setText(post.title);
        paragraph.setText(post.description);
        latitude = post.latitude;
        longitude = post.longitude;
        title.setText(post.getTitle());
        paragraph.setText(post.getDescription());
        postTime.setText("Posted at "
                + new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(post.datePosted));
        map = new MapComponent(latitude, longitude, "Label");
        add(map);
        post = SC.getPostByID(id);
        title.setText(post.title);
        paragraph.setText(post.description);
        postTime.setText("Posted at " + new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(post.datePosted));
        if(post.isContactEmailPresent()) {
            if(post.isPublic()){
                email.setText("For more information, contact:" + post.getContactEmail());
            }
            // TODO: Check current user role to make sure only accounts can see email if post is marked as such
            if(SecurityUtils.isUserLoggedIn()) {
                emailPrivate(post);
            }
        }
    }
}
