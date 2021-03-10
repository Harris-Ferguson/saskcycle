package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "results", layout = SearchResultsLayout.class)
@PageTitle("SaskCycle | Results")
public class SearchResultsView  extends VerticalLayout {

    public SearchResultsView() {

        VerticalLayout filterGroup = new VerticalLayout();

        Select<String> useSelect = new Select<>();
        useSelect.setItems("Searching", "Giving");
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

        CheckboxGroup<String> excludeGroup = new CheckboxGroup<>();
        excludeGroup.setLabel("Hide results for");
        excludeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
        excludeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);


        filterGroup.add(useSelect, sortSelect, postChoice, includeGroup, excludeGroup);

        HorizontalLayout resultsGroup = new HorizontalLayout();
        resultsGroup.add(filterGroup);



        add(new H1("All listings"), resultsGroup);
    }
}
