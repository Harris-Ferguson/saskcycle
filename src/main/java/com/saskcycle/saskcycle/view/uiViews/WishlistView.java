package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

        heading = new H1("Your Wishlist");

        //sets up searchController list to have all listings populated (currently can't do it in constructor or app breaks)
//        SC.resetPosts();

//        VerticalLayout filterGroup = FilterComponent();

        grid = initGrid();

        //grid.setSelectionMode(Grid.SelectionMode.NONE);


        // Constructing a post view based on what's clicked is still under construction
        grid.addItemClickListener(event -> {
            //System.out.println(event.getItem().title);
            getUI().ifPresent(ui -> ui.navigate(ClickedPostView.class,event.getItem().id));
            UI.getCurrent().getPage().reload();

        });

//        HorizontalLayout resultsGroup = new HorizontalLayout();
//        resultsGroup.setAlignItems(Alignment.START);
//        resultsGroup.setWidth("100%");
//        resultsGroup.add(filterGroup, grid);
        add(heading,grid
//                resultsGroup
        );
    }

    private Grid<Post> initGrid() {
        Grid<Post> newGrid = new Grid<>();
        newGrid.setItems(SC.getSavedPosts());
        newGrid.setHeight("1000px");
        newGrid.addComponentColumn(PostComponent::new).setWidth("60%");
        newGrid.addComponentColumn( item -> createDeleteButton(grid,item)).setWidth("100px");
        return newGrid;
    }

    private Button createDeleteButton(Grid<Post> grid, Post post){
        Post savedPost = post;
        Button deleteButton = new Button("Remove from wishlist",new Icon(VaadinIcon.TRASH));
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
                        dataProvider.refreshAll();
                        SC.removeSavedPost(savedPost.id);
                        confirmDelete.close();
                    });
            //cancel delete
            Button deleteButtonCancel = new Button("Cancel");
            deleteButtonCancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            deleteButtonCancel.addClassName("cancel-button");
            deleteButtonCancel.addClickListener(
                    e -> {
                        confirmDelete.close();
                    });
            VerticalLayout vbox = new VerticalLayout();
            vbox.add(new H2("Are you sure you want to remove this post?"), new HorizontalLayout(deleteButtonCancel, deleteButtonConfirm));
            vbox.setAlignItems(Alignment.CENTER);
            confirmDelete.add(vbox);
            confirmDelete.setOpened(true);
        });
        return deleteButton;
    }
}
