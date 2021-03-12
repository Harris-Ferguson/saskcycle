package com.saskcycle.saskcycle.view;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
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

    H2 label;

    String[] tagArr = new String[]{"Appliances", "Clothing", "Electronics", "Furniture"};

    //VerticalLayout content;

    private List<Post> posts;

    @PostConstruct
    public void init(){

        posts = SC.getAllPosts();

        VerticalLayout filterGroup = FilterComponent();

        grid = new Grid<>();
        grid.setItems(posts);

        grid.addComponentColumn(PostComponent::new).setHeader(" ");

        HorizontalLayout resultsGroup = new HorizontalLayout();
        resultsGroup.setAlignItems(Alignment.START);
        resultsGroup.setWidth("100%");
        resultsGroup.add(filterGroup, grid);

        add(new H1("All listings"), resultsGroup);
    }

    public VerticalLayout FilterComponent() {
        VerticalLayout filterGroup = new VerticalLayout();

        filterGroup.setWidth("200px");

        Select<String> useSelect = new Select<>();
        useSelect.setItems("Get", "Give");
        useSelect.setLabel("What do you want to do?");

        useSelect.addValueChangeListener(event -> {
            posts.clear();
            posts = SC.getSpecifiedPosts(event.getValue());

            grid.setItems(posts);

        });

        Select<String> sortSelect = new Select<>();
        sortSelect.setItems("Most recent", "Least recent", "Closest to me");
        sortSelect.setLabel("Sort by");


        CheckboxGroup<String> includeGroup = new CheckboxGroup<>();
        includeGroup.setLabel("Show results for");
        includeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
        includeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        includeGroup.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                resetPosts();
            }
            else {
                filterPosts(event.getValue());
            }
        });

        CheckboxGroup<String> excludeGroup = new CheckboxGroup<>();
        excludeGroup.setLabel("Hide results for");
        excludeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
        excludeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        excludeGroup.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                resetPosts();
            }
            else {
                excludePosts(event.getValue());
            }
        });

        CheckboxGroup<String> postChoice = new CheckboxGroup<>();
        postChoice.setLabel("Hide posts from");
        postChoice.setItems("Users", "Organizations");
        postChoice.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        filterGroup.add(useSelect, sortSelect, includeGroup, excludeGroup, postChoice);
        return filterGroup;
    }

    private void excludePosts(Set<String> value) {
        posts.clear();

        for (String t : value) {
            posts.addAll(SC.ExcludeListingsByTag(t));
        }
        grid.setItems(posts);

    }

    private void resetPosts() {
        posts.clear();
        posts = SC.getAllPosts();
        grid.setItems(posts);
    }


    private void filterPosts(Set<String> value) {

        posts.clear();
        for (String t : value) {
            posts.addAll(SC.getAllListingsByTag(t));
        }
        grid.setItems(posts);
    }


    public static void main(String[] args) {

//        SearchResultsView searchResultsView = new SearchResultsView();
//
//        searchResultsView.filterPosts(event.getValue());


    }

}
