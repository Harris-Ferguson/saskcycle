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

@Route(value = "clickedPost", layout = ClickedPostLayout.class)
public class ClickedPostView extends VerticalLayout implements HasUrlParameter<String>, AfterNavigationObserver {

    private H1 title;

    private String text;
    private String id;
    private Post post;

    private Paragraph paragraph;

    @Autowired
    SearchController SC;
    @Autowired
    CurrentUserDAOInterface account;

//    @Autowired
//    UserDAOInterface userDAD;

    public ClickedPostView() {

        title = new H1("Placeholder post title");
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
        H4 location = new H4("S0K 3A0");
        H4 postTime = new H4("Posted on March 22, 2021 at 5:05 p.m.");
        H4 contact= new H4("For more information, contact test_email@email.com");

        sidePanel.add(wishlistButton, location, postTime, contact);

        add(title, new HorizontalLayout(paragraph, sidePanel));
    }

    // Methods that may work eventually in creating a custom post based on what's clicked
    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        post = SC.getPostByID(id);
        text = post.title;
        title.setText(text);
        paragraph.setText(post.description);

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        id = s;

    }
}
