package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.model.Tags;
import com.saskcycle.saskcycle.view.components.PostComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.saskcycle.saskcycle.view.components.PostalCodeComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


@Route(value = "results", layout = MainLayout.class)
@PageTitle("SaskCycle | Results")
public class SearchResultsView extends VerticalLayout {

  @Autowired
  SearchController SC;

  private Grid<Post> grid;

  private H1 heading;
  private String location;
  private Select<String> sortSelect;
  private Select<String> useSelect;
  private Select<String> postChoice;
  private NumberField   numberField;
  private CheckboxGroup<String> includeGroup;
  private CheckboxGroup<String> excludeGroup;
  private PostalCodeComponent postalCodeBox;

    /** Constructs the view that displays the listings */
  @PostConstruct
  public void SearchResultsView() {

    heading = new H1("All listings");

    //sets up searchController list to have all listings populated (currently can't do it in constructor or app breaks)
    SC.resetPosts();

    VerticalLayout filterGroup = FilterComponent();

    grid = initGrid();

    //grid.setSelectionMode(Grid.SelectionMode.NONE);


    // Constructing a post view based on what's clicked is still under construction
      grid.addItemClickListener(event -> {
          //System.out.println(event.getItem().title);
          getUI().ifPresent(ui -> ui.navigate(ClickedPostView.class,event.getItem().id));
          UI.getCurrent().getPage().reload();
      });

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

     useSelect = new Select<>();
     useSelect.setItems("Select", "Get", "Give");
     useSelect.setLabel("What do you want to do?");
     useSelect.setValue("Select");

     useSelect.addValueChangeListener(

             event -> {
                 this.updatePosts();
                 grid.setItems(SC.getPageOfPosts(numberField.getValue()));
             });

    postalCodeBox = new PostalCodeComponent();

    // Dropdown menu user to select sorting
    sortSelect = new Select<>();
    sortSelect.setItems("Select", "Alphabetically (A-Z)", "Closest to me");
    sortSelect.setLabel("Sort by");
    sortSelect.setValue("Select");

    sortSelect.addValueChangeListener(
        event -> {
            if (sortSelect.getValue().equals("Closest to me")){
                Dialog dialog = new Dialog();
                Button submit = new Button("Submit", e -> {
                    if(postalCodeBox.postalCodeIsValid()) {
                        dialog.close();
                        location = postalCodeBox.getPostalCode();
                        this.updatePosts();
                        grid.setItems(SC.getPageOfPosts(numberField.getValue()));
                    }
                    else{
                        Notification errorNotif = new  Notification("Enter a valid postal code",
                                                            500,
                                                                    Notification.Position.MIDDLE);
                        errorNotif.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        errorNotif.open();
                    }
                });
                dialog.add(postalCodeBox, submit);
                dialog.open();
            }
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

    filterGroup.add(useSelect, sortSelect, includeGroup, excludeGroup, postChoice, numberField);
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
                sortSelect.getValue(),
                location);


    numberField.setMax(SC.amountOfPages());
    numberField.setValue(1d);
  }


}
