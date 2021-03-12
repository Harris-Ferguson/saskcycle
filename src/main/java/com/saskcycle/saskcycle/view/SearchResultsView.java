package com.saskcycle.saskcycle.view;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.FilterComponent;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
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

    //VerticalLayout content;

    private List<Post> posts;

    @PostConstruct
    public void init(){

        posts = SC.getAllPosts();

        VerticalLayout filterGroup = FilterComponent();

        grid = new Grid<>();
        grid.setItems(posts);

        grid.addComponentColumn(PostComponent::new).setHeader("Posts");

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

        Select<String> sortSelect = new Select<>();
        sortSelect.setItems("Most recent", "Least recent", "Closest to me");
        sortSelect.setLabel("Sort by");

        CheckboxGroup<String> postChoice = new CheckboxGroup<>();
        postChoice.setLabel("Show posts from");
        postChoice.setItems("Users", "Organizations");
        postChoice.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        CheckboxGroup<String> includeGroup = new CheckboxGroup<>();
        includeGroup.setLabel("Show results for");
        includeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
        includeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        includeGroup.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                posts = SC.getAllPosts();
            }
            else {
                updatePosts();
                //content = new PostComponent(posts);
            }
        });

        CheckboxGroup<String> excludeGroup = new CheckboxGroup<>();
        excludeGroup.setLabel("Hide results for");
        excludeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
        excludeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        filterGroup.add(useSelect, sortSelect, postChoice, includeGroup, excludeGroup, new H4(posts.get(0).title));
        return filterGroup;
    }


    private void updatePosts() {

        posts.clear();
        for (String t : new String[]{"Furniture"}) {
            posts.addAll(SC.getAllListingsByTag(t));
        }
        grid.setItems(posts);
    }

}
