package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.layouts.ClickedPostLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route(value = "clickedPost", layout = ClickedPostLayout.class)
public class ClickedPostView extends VerticalLayout /*implements HasUrlParameter<String>, AfterNavigationObserver*/ {

    private H1 title;

    public ClickedPostView() {

        title = new H1("Placeholder post title");
        Paragraph paragraph = new Paragraph("Placeholder description.");

        Button wishlistButton = new Button("Add to wishlist", new Icon(VaadinIcon.STAR));
        wishlistButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        wishlistButton.addClassName("wishlist-button");
        //HorizontalLayout heading = new HorizontalLayout(title, wishlistButton);
        //heading.setAlignItems(Alignment.BASELINE);

        add(title, wishlistButton, paragraph);
    }

//    @Override
//    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
//        heading.setText(title);
//
//    }
//
//    @Override
//    public void setParameter(BeforeEvent beforeEvent, String s) {
//        title = s;
//    }
}
