package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.SearchResultsLayout;
import com.saskcycle.services.FilterService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@Route(value = "results", layout = SearchResultsLayout.class)
@PageTitle("SaskCycle | Results")
public class SearchResultsView extends VerticalLayout {

  @Autowired FilterService filterService;

  @Autowired
  SearchController SC;

  private Grid<Post> grid;

  private H1 heading;

  private List<Post> posts;

  private Select<String> sortSelect;
  // private Select<String> useSelect;
  private Select<String> postChoice;

  private CheckboxGroup<String> includeGroup;
  private CheckboxGroup<String> excludeGroup;

  /** Constructs the view that displays the listings */
  @PostConstruct
  public void SearchResultsView() {

    heading = new H1("All listings");

    posts = filterService.getPosts();

    VerticalLayout filterGroup = FilterComponent();

    grid = initGrid();

    HorizontalLayout resultsGroup = new HorizontalLayout();
    resultsGroup.setAlignItems(Alignment.START);
    resultsGroup.setWidth("100%");
    resultsGroup.add(filterGroup, grid);

    add(heading, resultsGroup);
  }

  /**
   * Initializes the setup of grid and populates with posts
   *
   * @return grid component
   */
  private Grid<Post> initGrid() {
    Grid<Post> newGrid = new Grid<>();
    newGrid.setItems(posts);
    newGrid.setHeight("1000px");
    newGrid.addComponentColumn(PostComponent::new);

    return newGrid;
  }

  /**
   * Constructs the panel which contains all visual options for filtering posts
   *
   * @return vertical panel containing checkboxes and combo boxes
   */
  private VerticalLayout FilterComponent() {
    VerticalLayout filterGroup = new VerticalLayout();

    filterGroup.setWidth("200px");

    /* Use case has not been fully implemented
     useSelect = new Select<>();
     useSelect.setItems("Select", "Get", "Give");
     useSelect.setLabel("What do you want to do?");
     useSelect.setValue("Select");

     useSelect.addValueChangeListener(
             event -> {
                 posts = filterService.sortByFunction(event.getValue());
                 grid.setItems(posts);
             });    useSelect = new Select<>();

    */
    // Dropdown menu user to select sorting
    sortSelect = new Select<>();
    sortSelect.setItems("Select", "Alphabetically (A-Z)", "Closest to me");
    sortSelect.setLabel("Sort by");
    sortSelect.setValue("Select");

    sortSelect.addValueChangeListener(
        event -> {
          //          posts = filterService.sortPosts(event.getValue());
          posts =
              filterService.checkOtherFilters(
                  includeGroup.getValue(),
                  excludeGroup.getValue(),
                  postChoice.getValue(),
                  //                  useSelect.getValue(),
                  sortSelect.getValue());
          grid.setItems(posts);
        });

    // Checkbox to select tags that user wants to include
    includeGroup = new CheckboxGroup<>();
    includeGroup.setLabel("Show results for");
    includeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
    includeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

    includeGroup.addValueChangeListener(
        event -> {
          posts =
              filterService.checkOtherFilters(
                  includeGroup.getValue(),
                  excludeGroup.getValue(),
                  postChoice.getValue(),
                  //                  useSelect.getValue(),
                  sortSelect.getValue());
          grid.setItems(posts);

          excludeGroup.setItemEnabledProvider(item -> !event.getValue().contains(item));
          if (event.getValue() == null || event.getValue().isEmpty()) {
            heading.setText("All listings");
            if (!excludeGroup.getValue().isEmpty()) {
              heading.setText("Hide listings for " + stringTags(excludeGroup));
            }
          } else {
            heading.setText("Show listings for " + stringTags(includeGroup));
          }
        });

    // Checkbox to select tags that user wants to exclude
    excludeGroup = new CheckboxGroup<>();
    excludeGroup.setLabel("Hide results for");
    excludeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
    excludeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

    excludeGroup.addValueChangeListener(
        event -> {
          posts =
              filterService.checkOtherFilters(
                  includeGroup.getValue(),
                  excludeGroup.getValue(),
                  postChoice.getValue(),
                  //                  useSelect.getValue(),
                  sortSelect.getValue());
          grid.setItems(posts);

          includeGroup.setItemEnabledProvider(item -> !event.getValue().contains(item));
          if (event.getValue() == null || event.getValue().isEmpty()) {
            heading.setText("All listings");
            if (!includeGroup.isEmpty()) {
              heading.setText("Show listings for " + stringTags(includeGroup));
            }
          } else {
            heading.setText("Hide listings for " + stringTags(excludeGroup));
          }
        });

    // Show dropdown menu to select tbe author of the posts the user wants to see
    postChoice = new Select<>();
    postChoice.setLabel("Only show posts from");
    postChoice.setItems("Select", "Users", "Organizations");
    postChoice.setValue("Select");

    postChoice.addValueChangeListener(
        event -> {
          posts =
              filterService.checkOtherFilters(
                  includeGroup.getValue(),
                  excludeGroup.getValue(),
                  postChoice.getValue(),
                  //                  useSelect.getValue(),
                  sortSelect.getValue());
          grid.setItems(posts);
        });
    // Reset listings
    Button resetButton = new Button("Reset filters");
    resetButton.addClassName("reset-button");
    resetButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    resetButton.addClickListener(
        event -> {
          includeGroup.clear();
          excludeGroup.clear();
          sortSelect.setValue("Select");
          //          useSelect.setValue("Select");
          postChoice.setValue("Select");

          posts = filterService.resetPosts();
          grid.setItems(posts);
        });

    filterGroup.add(sortSelect, postChoice, includeGroup, excludeGroup, resetButton);
    return filterGroup;
  }

  /**
   * Creates a string representation of all of the tags being applied
   *
   * @param group contains the user filter choices
   * @return string of the selected tags
   */
  private String stringTags(CheckboxGroup<String> group) {
    StringBuilder strTags = new StringBuilder();
    for (String s : group.getValue()) {
      strTags.append(s).append(", ");
    }

    return strTags.substring(0, strTags.length() - 2);
  }

  public void updatePosts()
  {

  }
}
