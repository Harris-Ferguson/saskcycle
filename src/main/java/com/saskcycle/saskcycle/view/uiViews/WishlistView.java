package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;

@Route(value = "savedPosts", layout = MainLayout.class)
@PageTitle("SaskCycle | Saved Posts")
@Secured("ROLE_USER")
public class WishlistView extends VerticalLayout {

    @Autowired
    SearchController SC;

    private Grid<Post> grid;

    private H1 heading;

    @PostConstruct
    public void SearchResultsView() {
        heading = new H1("Saved Posts");
        grid = initGrid();

        // Constructing a post view based on what's clicked is still under construction
        grid.addItemClickListener(event -> {
            //System.out.println(event.getItem().title);
            getUI().ifPresent(ui -> ui.navigate(ClickedPostView.class, event.getItem().id));
            UI.getCurrent().getPage().reload();

        });
        add(heading, grid);
    }

    private Grid<Post> initGrid() {
        Grid<Post> newGrid = new Grid<>();
        newGrid.setItems(SC.getSavedPosts());
        newGrid.setHeight("1000px");
        newGrid.addComponentColumn(PostComponent::new);
        newGrid.addComponentColumn(item -> createDeleteButton(grid, item)).setWidth("100px");
        return newGrid;
    }

    private Button createDeleteButton(Grid<Post> grid, Post post) {
        Post savedPost = post;
        Button deleteButton = new Button("Remove from savedPosts", new Icon(VaadinIcon.TRASH));
        deleteButton.addClickListener(event -> {
            //Delete confirmation box
            Dialog confirmDelete = new Dialog();
            confirmDelete.setModal(false);
            //confirm delete button
            Button deleteButtonConfirm = new Button("Yes, remove post", new Icon(VaadinIcon.CHECK));
            deleteButtonConfirm.addClickListener(
                    e -> {
                        ListDataProvider<Post> dataProvider = (ListDataProvider<Post>) grid.getDataProvider();
                        dataProvider.getItems().remove(post);
                        dataProvider.refreshAll();
                        SC.removeSavedPost(savedPost.id);
                        confirmDelete.close();
                    });
            //cancel delete
            Button deleteButtonCancel = new Button("No, don't remove", new Icon(VaadinIcon.CLOSE));
            deleteButtonCancel.addClickListener(
                    e -> {
                        confirmDelete.close();
                    });
            VerticalLayout vbox = new VerticalLayout();
            vbox.add(new H1("Are you sure you want to remove this post?"), deleteButtonConfirm, deleteButtonCancel);
            vbox.setAlignItems(Alignment.CENTER);
            confirmDelete.add(vbox);
            confirmDelete.setOpened(true);
        });
        return deleteButton;
    }
}
