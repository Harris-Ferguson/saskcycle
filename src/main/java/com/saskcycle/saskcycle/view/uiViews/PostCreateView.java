package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.controller.PostController;
import com.saskcycle.model.Post;
import com.saskcycle.model.Tags;
import com.saskcycle.saskcycle.view.layouts.PostCreateLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Route(value = "Create-Posts", layout = PostCreateLayout.class)
@PageTitle("SaskCycle | Post Create")
@Secured("ROLE_USER")
public class PostCreateView extends VerticalLayout {

  @Autowired private CurrentUserDAOInterface currentAccount;

  Binder<PostController> binder = new Binder<>(PostController.class);

  Post postBeingMade = new Post();
  @Autowired
  PostController postController;

  @PostConstruct
  public void PostCreateView() {

    postController.setCurrentInspectedPost(postBeingMade);

    // cancel button
    Button returnButton = new Button("Return", new Icon(VaadinIcon.ARROW_BACKWARD));
    returnButton.addClickListener(e -> returnButton.getUI().ifPresent(ui -> ui.navigate("posts")));

    //info label
    Label infoLabel = new Label();

    // Post type select (give away, looking for)
    Div postType = new Div();
    Select<String> postTypeSelect = selectPostType();
    postTypeSelect.addValueChangeListener( e ->
            postType.setText(postTypeSelect.getValue()));



    // Title Field
    TextField title = postTitle();

    // Description Field
    TextArea description = postDescription();

    // Postal Field
    Pattern postalRegex = Pattern.compile("[a-zA-Z][0-9][a-zA-Z][0-9][a-zA-Z][0-9]");
    TextField postalCodeField = new TextField();
    postalCodeField.setLabel("Postal Code");
    postalCodeField.setPlaceholder("form: K1A0B1");
    postalCodeField.setMinWidth("150px");
    // Postal layout check
    Matcher postalMatcher = postalRegex.matcher(postalCodeField.getValue());
    postalCodeField.setPreventInvalidInput(true);
    postalCodeField.setMaxLength(6);
    postalCodeField.setRequiredIndicatorVisible(true);
    AtomicBoolean postalMatch = new AtomicBoolean(postalMatcher.find());
    postalCodeField.addValueChangeListener(e -> {
            postalMatcher.reset(e.getValue());
            postalMatch.set(postalMatcher.find()); });

    // Privacy and email/phone check boxes
    Div postPrivacy = new Div();
    Select<String> privacySelect = postPrivacy();
    privacySelect.addValueChangeListener( e ->
            postPrivacy.setText(privacySelect.getValue()));

    // email check box
    Checkbox email = new Checkbox();
    Div curEmail = new Div();
    curEmail.setText("Email: ");
    email.addValueChangeListener(
        event -> {
          if (event.getValue()) {
            curEmail.setText("Email: " + currentAccount.getEmail());
          } else {
            curEmail.setText("Email: ");
          }
    });

    // adding privacy, email and phone checks to component
    VerticalLayout contactBox = new VerticalLayout(new HorizontalLayout(email, curEmail), phone());
    HorizontalLayout contactPanel = new HorizontalLayout(privacySelect, contactBox);

    // Tags list
    MultiSelectListBox<String> tags = new MultiSelectListBox<>();
    tags.setItems(Tags.getTagNames());
    ArrayList<String> tagList = new ArrayList<>();
    TextField tagField = new TextField();
    tags.addValueChangeListener(
        e -> {
          tagList.clear();
          tagList.addAll(e.getValue());
          tagField.clear();
          tagField.setValue(e.toString());
    });

    // Set up Binder bindings for certain components that require verification
    SerializablePredicate<String> typePredicates = value -> !postType.getText().trim().isEmpty();
    SerializablePredicate<String> titlePredicates = value -> !title.getValue().trim().isEmpty();
    SerializablePredicate<String> descriptionPredicates = value -> !description.getValue().trim().isEmpty();
    SerializablePredicate<String> postalPredicates = value -> !postalCodeField.getValue().trim().isEmpty();
    SerializablePredicate<String> privacyPredicates = value ->!postPrivacy.getText().trim().isEmpty();

    Binder.Binding<PostController, String> typeBinding = binder.forField(postTypeSelect)
            .withNullRepresentation("")
            .withValidator(typePredicates, "Please specify why your posting")
            .bind(PostController::getPostType, PostController::setPostType);

    Binder.Binding<PostController, String> titleBinding = binder.forField(title)
            .withNullRepresentation("")
            .withValidator(titlePredicates, "Please specify your title")
            .bind(PostController::getPostTitle, PostController::setPostTitle);

    Binder.Binding<PostController, String> descriptionBinding = binder.forField(description)
            .withNullRepresentation("")
            .withValidator(descriptionPredicates, "Please specify your description")
            .bind(PostController::getPostDescription, PostController::setPostDescription);

    Binder.Binding<PostController, String> postalBinding = binder.forField(postalCodeField)
            .withNullRepresentation("")
            .withValidator(postalPredicates, "Please specify a postal code")
            .bind(PostController::getPostalCode, PostController::setPostPostalCode);

    Binder.Binding<PostController, String> privacyBinding = binder.forField(privacySelect)
            .withNullRepresentation("")
            .withValidator(privacyPredicates, "Please specify your post's privacy")
            .bind(PostController::getPostPrivacy, PostController::setPostPrivacy);

    // Left side of post creation
    VerticalLayout LeftInfoPanel = new VerticalLayout(title, description, postalCodeField, contactPanel);

    // Right side of post creation
    VerticalLayout RightInfoPanel = new VerticalLayout(new H1("Tags:"), tags);

    // Body of post creation
    HorizontalLayout InfoPanel = new HorizontalLayout(LeftInfoPanel, RightInfoPanel);

    // Header of post creation
    HorizontalLayout Header = new HorizontalLayout(returnButton, new H1("Create Post"), postTypeSelect);
    Header.setAlignItems(Alignment.CENTER);

    /**
    * post create button declaration and event handeling
    * button will perform various checks on fields in view to ensure info is filled out
    * if all is good, then publish post will be called
    */
    Button createPostButton = new Button("Create Post!", new Icon(VaadinIcon.THUMBS_UP));
    createPostButton.addClickListener(event -> {
      // all fields are filled
      if (binder.writeBeanIfValid(postController) && !tags.isEmpty()) {
        infoLabel.setText("Saved bean values: " + postController);
        // Postal code format check
        if(!postalMatch.get()){
            Notification postalNotification = new Notification("Invalid Postal Code",3000, Notification.Position.MIDDLE);
            postalNotification.open();
        }
        else {
            publishPost(postTypeSelect.getValue(),title.getValue(),description.getValue(),postalCodeField.getValue(),tagList,privacySelect.getValue(),email.getValue());
        }

      }
      else {
          // empty tag list check
          if(tagList.isEmpty()){
              Notification tagsNotification = new Notification("Please add Some tags",3000, Notification.Position.BOTTOM_CENTER);
              tagsNotification.open();
          }
        //Missing value checks using Binder
        BinderValidationStatus<PostController> validate = binder.validate();
        String errorText = validate.getFieldValidationStatuses()
                .stream().filter(BindingValidationStatus::isError)
                .map(BindingValidationStatus::getMessage)
                .map(Optional::get).distinct()
                .collect(Collectors.joining(", "));
        infoLabel.setText("There are errors: " + errorText);
      }
    });

    add(Header, InfoPanel, createPostButton);
  }


    /* ----------- Widget setup Methods ------------- */

  /** publishPost method
   * method uses controller to set all post fields
   * if so, then a new post is made using all the provided info from user
   */
  private void publishPost(String postType, String postTitle, String postDesc, String postalCode, ArrayList<String> postTagsApplied,String postPrivacy,boolean includeEmail){
      postController.setPostType(postType);
      postController.setPostTitle(postTitle);
      postController.setPostDescription(postDesc);
      postController.setPostPostalCode(postalCode);
      postController.setPostTags(postTagsApplied);
      postController.setPostPrivacy(postPrivacy);
      if(includeEmail){
          postController.setPostContactEmail(currentAccount.getEmail());
      }
      postController.setPostID();
      Boolean publishSuccess = postController.verifyAndPublish();
      if(publishSuccess){
          currentAccount.updateCreatedPostList(postController.getPostID().toString());
          // Confirmation Dialog Box
          Dialog confirmPosted = postDialogBox(publishSuccess);
          confirmPosted.setOpened(true);
      }
      else {
          // error Dialog Box
          Dialog failedPosted = postDialogBox(publishSuccess);
          failedPosted.setOpened(true);
      }

  }

    /**
     * post dialog box creation
     * @param success if post was successful or not
     * @return dialog box
     */
  private Dialog postDialogBox(Boolean success){
      Dialog dialog = new Dialog();
      dialog.setModal(false);
      Button returnButton = new Button("Return Home", new Icon(VaadinIcon.HOME));
      returnButton.addClickListener(
              e -> {
                  returnButton.getUI().ifPresent(ui -> ui.navigate(""));
                  dialog.close();
              });
      if(success){
          dialog.add(new H1("Successful Post!"), returnButton);
      }
      else {
          dialog.add(new H1("Something went wrong while posting"), returnButton);
      }
      return dialog;
  }

    /**
     * Build select for the type of post
     * @return select widget
     */
  private Select<String> selectPostType(){
    Select<String> postTypeSelect = new Select<>();
    postTypeSelect.setItems("giving away", "looking for");
    postTypeSelect.setPlaceholder("giving or looking");
    postTypeSelect.setLabel("Why are you posting?");
    postTypeSelect.setRequiredIndicatorVisible(true);
    return postTypeSelect;
  }

    /**
     * Build title are for post create
     * @return textfield title widget
     */
  private TextField postTitle(){
      TextField title = new TextField();
      title.setLabel("Post Title");
      title.setPlaceholder("Type here ...");
      title.setMinWidth("600px");
      title.setRequiredIndicatorVisible(true);
      return title;
  }

    /**
     * Build Description for post create
     * @return TextArea description widget
     */
  private TextArea postDescription(){
    TextArea description = new TextArea();
    description.setLabel("Description");
    description.setPlaceholder("Type here ...");
    description.setMinWidth("600px");
    description.setMinHeight("200px");
    description.setRequiredIndicatorVisible(true);
    return description;
  }

  private Select<String> postPrivacy(){
      Select<String> privacySelect = new Select<>();
      privacySelect.setItems("Public", "Accounts");
      privacySelect.setPlaceholder("privacy");
      privacySelect.setLabel("Post Privacy");
      privacySelect.setMaxWidth("150px");
      privacySelect.setRequiredIndicatorVisible(true);
      return privacySelect;
  }

  // Placeholder phone widget method
  private HorizontalLayout phone() {
    Checkbox phone = new Checkbox();
    Div value = new Div();
    value.setText("phone: ");
    phone.addValueChangeListener(
        event -> {
          if (event.getValue()) {
            value.setText("phone: ");
          } else {
            value.setText("phone: ");
          }
        });
    return new HorizontalLayout(phone, value);
  }
}