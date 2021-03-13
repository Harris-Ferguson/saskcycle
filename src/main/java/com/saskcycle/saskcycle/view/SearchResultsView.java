package com.saskcycle.saskcycle.view;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
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

    private VerticalLayout FilterComponent() {
        VerticalLayout filterGroup = new VerticalLayout();

        filterGroup.setWidth("200px");

        Select<String> useSelect = new Select<>();
        useSelect.setItems("Get", "Give");
        useSelect.setLabel("What do you want to do?");

        useSelect.addValueChangeListener(event -> {
            sortByFunction(event.getValue());

        });

        Select<String> sortSelect = new Select<>();
        sortSelect.setItems("Alphabetically (A-Z)", "Closest to me");
        sortSelect.setLabel("Sort by");

        sortSelect.addValueChangeListener(event -> {
            sortPosts(event.getValue());
        });


        CheckboxGroup<String> includeGroup = new CheckboxGroup<>();
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

        CheckboxGroup<String> excludeGroup = new CheckboxGroup<>();
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

        Select<String> postChoice = new Select<>();
        postChoice.setLabel("Hide posts from");
        postChoice.setItems("Users", "Organizations");

        Button resetButton = new Button("Reset filters");
        resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetButton.addClickListener(event -> {
            resetPosts();
        });

        filterGroup.add(useSelect, sortSelect, postChoice, includeGroup, excludeGroup, resetButton);
        return filterGroup;
    }

    private void sortByFunction(String value) {
        //posts.clear();
        posts = SC.getSpecifiedPosts(value, posts);

        grid.setItems(posts);
    }

    private void sortPosts(String value) {

        //posts.clear();
        SC.getSortedPosts(value, posts);

        grid.setItems(posts);
    }

    private void excludePosts(Set<String> value) {
        //posts.clear();

        for (String t : value) {
            posts = SC.ExcludeListingsByTag(t, posts);
        }
        grid.setItems(posts);

    }

    private void resetPosts() {
        posts.clear();
        posts = SC.getAllPosts();
        grid.setItems(posts);
    }


    private void filterPosts(Set<String> value) {

        //posts.clear();
        for (String t : value) {
            posts = SC.getAllListingsByTag(t, posts);
        }
        grid.setItems(posts);
    }

}
