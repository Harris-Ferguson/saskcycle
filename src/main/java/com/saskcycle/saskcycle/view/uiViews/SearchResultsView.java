package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.model.Tags;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.SearchResultsLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


@Route(value = "results", layout = SearchResultsLayout.class)
@PageTitle("SaskCycle | Results")
public class SearchResultsView extends VerticalLayout {

  @Autowired
  SearchController SC;

  private Grid<Post> grid;

  private H1 heading;

  private Select<String> sortSelect;
  private Select<String> useSelect;
  private Select<String> postChoice;
  private NumberField   numberField;
  private CheckboxGroup<String> includeGroup;
  private CheckboxGroup<String> excludeGroup;


    /** Constructs the view that displays the listings */
  @PostConstruct
  public void SearchResultsView() {

    heading = new H1("All listings");

    //sets up searchController list to have all listings populated (currently can't do it in constructor or app breaks)
    SC.resetPosts();

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
    newGrid.setItems(SC.getPageOfPosts(numberField.getValue()));
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

//    /* Use case has not been fully implemented
     useSelect = new Select<>();
     useSelect.setItems("Select", "Get", "Give");
     useSelect.setLabel("What do you want to do?");
     useSelect.setValue("Select");

     useSelect.addValueChangeListener(
             event -> {
                 this.updatePosts();
                 grid.setItems(SC.getPageOfPosts(numberField.getValue()));
             });



    // Dropdown menu user to select sorting
    sortSelect = new Select<>();
    sortSelect.setItems("Select", "Alphabetically (A-Z)", "Closest to me");
    sortSelect.setLabel("Sort by");
    sortSelect.setValue("Select");

    sortSelect.addValueChangeListener(
        event -> {
            this.updatePosts();
          grid.setItems(SC.getPageOfPosts(numberField.getValue()));
        });

    // Checkbox to select tags that user wants to include
    includeGroup = new CheckboxGroup<>();
    includeGroup.setLabel("Show results for");
    includeGroup.setItems(Tags.getTagNames());
    includeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

    includeGroup.addValueChangeListener(
        event -> {
            this.updatePosts();
          grid.setItems(SC.getPageOfPosts(numberField.getValue()));

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
    excludeGroup.setItems(Tags.getTagNames());
    excludeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

    excludeGroup.addValueChangeListener(
        event -> {
            this.updatePosts();
          grid.setItems(SC.getPageOfPosts(numberField.getValue()));

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
            this.updatePosts();
            numberField.setMax(SC.amountOfPages());
          grid.setItems(SC.getPageOfPosts(numberField.getValue()));
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
          useSelect.setValue("Select");
          postChoice.setValue("Select");

          // "resets" searchController list
          SC.resetPosts();
          numberField.setMax(SC.amountOfPages());
          numberField.setValue(1d);
          grid.setItems(SC.getPageOfPosts(numberField.getValue()));
        });

      numberField = new NumberField();
      numberField.setLabel("Go to page:");
      numberField.setValue(1d);
      numberField.setHasControls(true);
      numberField.setMin(1);
      numberField.setMax(SC.amountOfPages());
      numberField.addValueChangeListener(
              event -> {
                  grid.setItems(SC.getPageOfPosts(numberField.getValue()));
              });

      add(numberField);
    filterGroup.add(sortSelect, postChoice, useSelect, includeGroup, excludeGroup, resetButton,numberField);


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

    /**
     * This method notifies the controlled that the user has changed a filtering option,
     * and the searchController adjusts its list of current posts accordingly as well as
     * ensures the amount of post pages is correct
     */
  public void updatePosts()
  {
      SC.filterService(includeGroup.getValue(),
                excludeGroup.getValue(),
                postChoice.getValue(),
                useSelect.getValue(),
                sortSelect.getValue());


    numberField.setMax(SC.amountOfPages());
    numberField.setValue(1d);
  }


}
