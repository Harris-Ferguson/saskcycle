package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.PostController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

    grid.addItemClickListener(
        event -> {
          postController.setCurrentInspectedPost(event.getItem());
          getUI().ifPresent(ui -> ui.navigate(ClickedPostView.class, event.getItem().id));
          UI.getCurrent().getPage().reload();
        });

    Button createButton = new Button("Create New Post", new Icon(VaadinIcon.PLUS));
    createButton.addClassName("reset-button");
    createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    createButton.addClickListener(
        e -> createButton.getUI().ifPresent(ui -> ui.navigate("Create-Posts")));

    add(new H1("Your Posts"), createButton,grid);
  }

  private Grid<Post> initGrid() {
    Grid<Post> newGrid = new Grid<>();
    newGrid.setItems(postController.getUserCreatedPosts());
    newGrid.setHeight("1000px");
    newGrid.addComponentColumn(PostComponent::new).setWidth("75%");
    newGrid.addComponentColumn( item -> createEditButton(grid,item));
    newGrid.addComponentColumn( item -> createDeleteButton(grid,item));

    return newGrid;
  }

  private Button createEditButton(Grid<Post> grid, Post post){
    postController.setCurrentInspectedPost(post);
    Button button = new Button("Edit Post",new Icon(VaadinIcon.PENCIL));
    button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    button.addClassName("reset-button");
    button.addClickListener(event -> {
      getUI().ifPresent(ui -> ui.navigate(EditPostView.class,post.id));
    });
    return button;

    }

  private Button createDeleteButton(Grid<Post> grid, Post post){
    Button deleteButton = new Button("Delete Post",new Icon(VaadinIcon.TRASH));
    deleteButton.addClassName("reset-button");
    deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    deleteButton.addClickListener(event -> {
      //Delete confirmation box
      Dialog confirmDelete = new Dialog();
      confirmDelete.setModal(false);
      //confirm delete button
      Button deleteButtonConfirm = new Button("Delete");
      deleteButtonConfirm.addClassName("reset-button");
      deleteButtonConfirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
      deleteButtonConfirm.addClickListener(
              e -> {
                ListDataProvider<Post> dataProvider = (ListDataProvider<Post>) grid.getDataProvider();
                dataProvider.getItems().remove(post);
                postController.removePost(post);
                dataProvider.refreshAll();
                confirmDelete.close();

              });
      //cancel delete
      Button deleteButtonCancel = new Button("Cancel");
      deleteButtonCancel.setClassName("cancel-button");
      deleteButtonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
      deleteButtonCancel.addClickListener(
              e -> {
                confirmDelete.close();
              });
      VerticalLayout vbox = new VerticalLayout();
      HorizontalLayout buttonPanel = new HorizontalLayout(deleteButtonCancel, deleteButtonConfirm);
      buttonPanel.setJustifyContentMode(JustifyContentMode.CENTER);
      vbox.add(new H2("Are you sure you want to delete this post?"), buttonPanel);
      vbox.setAlignItems(Alignment.CENTER);
      confirmDelete.add(vbox);
      confirmDelete.setOpened(true);
    });
    return deleteButton;
  }



}
