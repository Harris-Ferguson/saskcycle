package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.PostController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;

@Route(value = "posts", layout = MainLayout.class)
@PageTitle("SaskCycle | Post")
@Secured("ROLE_USER")
public class PostView extends VerticalLayout {

  @Autowired
  PostController postController;

  private Grid<Post> grid;

  @PostConstruct
  public void PostView() {

    grid = initGrid();

    grid.addItemClickListener(event ->{
      getUI().ifPresent(ui -> ui.navigate(ClickedPostView.class,event.getItem().id));
    });

    // addClassName("filter-view");
    Button createButton = new Button("Create New Post", new Icon(VaadinIcon.PLUS));
    createButton.addClickListener(
        e -> createButton.getUI().ifPresent(ui -> ui.navigate("Create-Posts")));

    add(new H1("Posts"), createButton,new H2("Your current posts:"),grid);
  }

  private Grid<Post> initGrid() {
    Grid<Post> newGrid = new Grid<>();
    newGrid.setItems(postController.getUserCreatedPosts());
    newGrid.setHeight("1000px");
    newGrid.addComponentColumn(PostComponent::new);

    return newGrid;
  }

}
