package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAO;
import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.DAO.UserDAOInterface;
import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.security.SecurityUtils;
import com.saskcycle.saskcycle.view.layouts.ClickedPostLayout;
import com.vaadin.flow.component.Text;
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

@Route(value = "clickedPost", layout = ClickedPostLayout.class)
public class ClickedPostView extends VerticalLayout implements HasUrlParameter<String>, AfterNavigationObserver {

    private H1 title;

    private String text;
    private String id;
    private Post post;

    H4 location;
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

        sidePanel.setWidth("250px");
        location = new H4();
        postTime = new H4();
        email = new H4();
        //sidePanel.getStyle().set("border", "1px solid #eeeeee");

        // TODO: Matthew's story
        H4 contact = new H4("For more information, contact:"+email);

        VerticalLayout desc = new VerticalLayout();
        desc.add(paragraph);
        desc.setWidth("750px");

        sidePanel.add(wishlistButton, location, postTime, email);

        add(title, new HorizontalLayout(desc, sidePanel));
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
        location.setText(post.location);
        postTime.setText("Posted at " + new SimpleDateFormat("EEEE, MMMM dd, yyyy hh:mm a").format(post.datePosted));
        if(post.isContactEmailPresent()) {
            if(post.isPublic()){
                email.setText("For more information, contact:" + post.getContactEmail());
                System.out.println(account.getCurrentAccount().getRole());
            }
            // TODO: Check current user role to make sure only accounts can see email if post is marked as such
            /*else if() {
                    emailPrivate(post);
            }*/
        }

    }

    public void emailPrivate(Post post){
        email.setText("For more information, contact:" + post.getContactEmail());
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
