package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.PostController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
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
    newGrid.addComponentColumn(PostComponent::new).setWidth("500px");
    newGrid.addComponentColumn( item -> createEditButton(grid,item)).setWidth("100px");
    newGrid.addComponentColumn( item -> createDeleteButton(grid,item)).setWidth("100px");


    return newGrid;
  }

  private Button createEditButton(Grid<Post> grid, Post post){
    postController.setCurrentInspectedPost(post);
    Button button = new Button("Edit Post",new Icon(VaadinIcon.PENCIL));
    button.addClickListener(event -> {
      getUI().ifPresent(ui -> ui.navigate(EditPostView.class,post.id));
    });
    return button;

  }

  private Button createDeleteButton(Grid<Post> grid, Post post){
    postController.setCurrentInspectedPost(post);
    Button deleteButton = new Button("Delete Post",new Icon(VaadinIcon.TRASH));
    deleteButton.addClickListener(event -> {
      //Delete confirmation box
      Dialog confirmDelete = new Dialog();
      confirmDelete.setModal(false);
      //confirm delete button
      Button deleteButtonConfirm = new Button("Yes, delete post", new Icon(VaadinIcon.CHECK));
      deleteButtonConfirm.addClickListener(
              e -> {
                ListDataProvider<Post> dataProvider = (ListDataProvider<Post>) grid.getDataProvider();
                dataProvider.getItems().remove(post);
                dataProvider.refreshAll();
                postController.removePost();
                confirmDelete.close();
              });
      //cancel delete
      Button deleteButtonCancel = new Button("No, don't delete", new Icon(VaadinIcon.CLOSE));
      deleteButtonCancel.addClickListener(
              e -> {
                confirmDelete.close();
              });
      VerticalLayout vbox = new VerticalLayout();
      vbox.add(new H1("Are you sure you want to delete this post?"), deleteButtonConfirm,deleteButtonCancel);
      vbox.setAlignItems(Alignment.CENTER);
      confirmDelete.add(vbox);
      confirmDelete.setOpened(true);
    });
    return deleteButton;
  }



}
