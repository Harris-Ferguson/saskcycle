package com.saskcycle.saskcycle.view;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.FilterComponent;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

        posts = SC.getAllGivingAway();

        VerticalLayout filterGroup = new FilterComponent();

        VerticalLayout content = new PostComponent(posts);

        HorizontalLayout resultsGroup = new HorizontalLayout();
        resultsGroup.setAlignItems(Alignment.START);
        resultsGroup.setWidth("100%");
        resultsGroup.add(filterGroup, content);

        add(new H1("All listings"), resultsGroup);
    }

}
