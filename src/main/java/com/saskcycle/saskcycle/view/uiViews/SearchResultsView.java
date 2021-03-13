package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.SearchResultsLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;


@Route(value = "results", layout = SearchResultsLayout.class)
@PageTitle("SaskCycle | Results")
public class SearchResultsView  extends VerticalLayout {

    @Autowired
    private SearchController SC;

    Grid<Post> grid;

    private H1 heading;

    String[] tagArr = new String[]{"Appliances", "Clothing", "Electronics", "Furniture"};

    private List<Post> posts;

    private Select<String> sortSelect;
    private Select<String> useSelect;
    private Select<String> postChoice;

    private CheckboxGroup<String> includeGroup;
    private CheckboxGroup<String> excludeGroup;

    /**
     * Constructs the view that displays the listings
     */
    @PostConstruct
    public void init(){

        heading = new H1("All listings");

        posts = SC.getAllPosts();

        VerticalLayout filterGroup = FilterComponent();

        grid = new Grid<>();
        grid.setItems(posts);
        grid.setHeight("1000px");
        grid.addComponentColumn(PostComponent::new);

        HorizontalLayout resultsGroup = new HorizontalLayout();
        resultsGroup.setAlignItems(Alignment.START);
        resultsGroup.setWidth("100%");
        resultsGroup.add(filterGroup, grid);

        add(heading, resultsGroup);
    }

    /**
     * Constructs the panel which contains all visual options for filtering posts
     * @return vertical panel containing checkboxes and combo boxes
     */
    private VerticalLayout FilterComponent() {
        VerticalLayout filterGroup = new VerticalLayout();

        filterGroup.setWidth("200px");

        useSelect = new Select<>();
        useSelect.setItems("Select", "Get", "Give");
        useSelect.setLabel("What do you want to do?");
        useSelect.setValue("Select");

        useSelect.addValueChangeListener(event -> {
            sortByFunction(event.getValue());

        });

        sortSelect = new Select<>();
        sortSelect.setItems("Select", "Alphabetically (A-Z)", "Closest to me");
        sortSelect.setLabel("Sort by");
        sortSelect.setValue("Select");

        sortSelect.addValueChangeListener(event -> {
            sortPosts(event.getValue());
        });


        includeGroup = new CheckboxGroup<>();
        includeGroup.setLabel("Show results for");
        includeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
        includeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        includeGroup.addValueChangeListener(event -> {
            if (event.getValue() == null || event.getValue().isEmpty()) {
                heading.setText("All listings");
                resetPosts();
            }
            else {
                StringBuilder strTags = new StringBuilder();
                for (String s : event.getValue()) { strTags.append(s).append(", ");}

                heading.setText("Show listings for " + strTags.substring(0, strTags.length()-2));
                filterPosts(event.getValue());
            }
        });

        excludeGroup = new CheckboxGroup<>();
        excludeGroup.setLabel("Hide results for");
        excludeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
        excludeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        excludeGroup.addValueChangeListener(event -> {
            if (event.getValue() == null || event.getValue().isEmpty()) {
                heading.setText("All listings");
                resetPosts();
            }
            else {
                StringBuilder strTags = new StringBuilder();
                for (String s : event.getValue()) { strTags.append(s).append(", ");}
                heading.setText("Hide listings for " + strTags.substring(0, strTags.length()-2));
                excludePosts(event.getValue());
            }
        });

        postChoice = new Select<>();
        postChoice.setLabel("Hide posts from");
        postChoice.setItems("Select", "Users", "Organizations");
        postChoice.setValue("Select");


        Button resetButton = new Button("Reset filters");
        resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetButton.addClickListener(event -> {
            includeGroup.clear();
            excludeGroup.clear();
            sortSelect.setValue("Select");
            useSelect.setValue("Select");
            postChoice.setValue("Select");

            resetPosts();
        });

        filterGroup.add(useSelect, sortSelect, postChoice, includeGroup, excludeGroup, resetButton);
        return filterGroup;
    }


    /**
     * Gets the either the give or get posts depending what's chosen by the user
     * @param value "get" or "give"
     * @postcond modifies the grid displaying the posts as well as the posts list
     */
    private void sortByFunction(String value) {

        posts = SC.getSpecifiedPosts(value, posts);

        grid.setItems(posts);
    }

    /**
     * Sorts posts by the given specification
     * @param value the characteristic by which the code is sorted
     * @postcond modifies the grid displaying the posts as well as the posts list
     */
    private void sortPosts(String value) {

        SC.getSortedPosts(value, posts);

        grid.setItems(posts);
    }

    /**
     * Hides the posts that are tagged with the specified tag(s)
     * @param value tag values(s) associated with posts
     * @postcond modifies the grid displaying the posts as well as the posts list
     */
    private void excludePosts(Set<String> value) {

        for (String t : value) {
            posts = SC.ExcludeListingsByTag(t, posts);
        }
        grid.setItems(posts);

    }

    /**
     * Gets all posts in the database; removes any filtering/excluding/sorting
     * @postcond modifies the grid displaying the posts as well as the posts list
     */
    private void resetPosts() {
        posts.clear();
        posts = SC.getAllPosts();
        grid.setItems(posts);
    }

    /**
     * Shows all and only the posts that are associated with specified tag(s)
     * @param value tag values(s) associated with posts
     * @postcond modifies the grid displaying the posts as well as the posts list
     */
    private void filterPosts(Set<String> value) {

        for (String t : value) {
            posts = SC.getAllListingsByTag(t, posts);
        }
        grid.setItems(posts);
    }

}
