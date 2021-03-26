package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
        newGrid.addComponentColumn(PostComponent::new);

        return newGrid;
    }
}
