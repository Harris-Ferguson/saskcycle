package com.saskcycle.saskcycle.view;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;


@Route(value = "results", layout = SearchResultsLayout.class)
@PageTitle("SaskCycle | Results")
public class SearchResultsView  extends VerticalLayout {

    @Autowired
    private SearchController SC;

    private List<Post> posts;

    @PostConstruct
    public void init(){

        VerticalLayout filterGroup = new VerticalLayout();
        filterGroup.setWidth("200px");

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

        posts = SC.getAllGivingAway();

        Scroller scroller = new Scroller();
        scroller.setHeight("200px");
        scroller.setWidth("100%");

        VerticalLayout content= new VerticalLayout();

        content.setAlignItems(Alignment.STRETCH);
        content.setWidth("100%");
        content.setSpacing(false);

        for (int i=0; i < posts.size(); i++) {
            VerticalLayout component = new VerticalLayout();
            component.add(new H3(posts.get(i).title));
            component.add(new H5(posts.get(i).location + " away"));
            Paragraph desc = new Paragraph(posts.get(i).description);
            desc.addClassName("para");
            component.add(desc);
            component.add(formatTags(i));


            content.add(component);
            component.getStyle().set("border", "1px solid #eeeeee");
        }

        //scroller.setContent(content);


        filterGroup.add(useSelect, sortSelect, postChoice, includeGroup, excludeGroup);

        HorizontalLayout resultsGroup = new HorizontalLayout();
        resultsGroup.setAlignItems(Alignment.START);
        resultsGroup.setWidth("100%");
        resultsGroup.setSpacing(false);
        resultsGroup.add(filterGroup, content);


        add(new H1("All listings"), resultsGroup);
    }

    private HorizontalLayout formatTags(int index){

        HorizontalLayout tagGroup = new HorizontalLayout();


        for (int i = 0; i < posts.get(index).tags.size(); i++){

            Button component = new Button(posts.get(index).tags.get(i));

            tagGroup.add(component);
            
        }


        return tagGroup;

    }


}
