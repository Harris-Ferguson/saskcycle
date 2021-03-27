package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.controller.PostController;
import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.model.Tags;
import com.saskcycle.saskcycle.security.SecurityUtils;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Route(value = "editPost", layout = MainLayout.class)
public class EditPostView extends VerticalLayout implements HasUrlParameter<String>, AfterNavigationObserver {

    @Autowired
    PostController postController;
    @Autowired
    SearchController searchController;
    @Autowired
    CurrentUserDAOInterface currentAccount;

    // Post identifiers
    private String id;
    private Post post;

    //Post attributes that can be edited
    private TextField title = new TextField();
    private TextArea description = new TextArea();
    private MultiSelectListBox<String> tags = new MultiSelectListBox<>();
    private TextField postalCodeField = new TextField();

    Binder<PostController> binder = new Binder<>(PostController.class);

    @PostConstruct
    public void EditPostView() {

        postController.setCurrentInspectedPost(post);

        // cancel button
        Button returnButton = new Button("Cancel edits", new Icon(VaadinIcon.ARROW_BACKWARD));
        returnButton.addClickListener(e -> returnButton.getUI().ifPresent(ui -> ui.navigate("posts")));

        //info label
        Label infoLabel = new Label();


        // Title Field
        title.setLabel("Post Title");
        title.setMinWidth("600px");
        title.setRequiredIndicatorVisible(true);

        // Description Field
        description.setLabel("Description");
        description.setMinWidth("600px");
        description.setMinHeight("200px");
        description.setRequiredIndicatorVisible(true);

        // Postal Field
        Pattern postalRegex = Pattern.compile("[a-zA-Z][0-9][a-zA-Z][0-9][a-zA-Z][0-9]");
        postalCodeField.setLabel("Postal Code");
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
        Select<String> privacySelect = new Select<>();
        privacySelect.setItems("Public", "Accounts");
        privacySelect.setPlaceholder("privacy");
        privacySelect.setLabel("Post Privacy");
        privacySelect.setMaxWidth("150px");
        privacySelect.setRequiredIndicatorVisible(true);
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
        VerticalLayout contactBox = new VerticalLayout(new HorizontalLayout(email, curEmail));
        HorizontalLayout contactPanel = new HorizontalLayout(privacySelect, contactBox);

        // Tags list
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

        SerializablePredicate<String> titlePredicates = value -> !title.getValue().trim().isEmpty();
        SerializablePredicate<String> descriptionPredicates = value -> !description.getValue().trim().isEmpty();
        SerializablePredicate<String> postalPredicates = value -> !postalCodeField.getValue().trim().isEmpty();
        SerializablePredicate<String> privacyPredicates = value ->!postPrivacy.getText().trim().isEmpty();

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

        // Left side of post edit
        VerticalLayout LeftInfoPanel = new VerticalLayout(title, description, postalCodeField, contactPanel);

        // Right side of post edit
        VerticalLayout RightInfoPanel = new VerticalLayout(new H1("Tags:"), tags);

        // Body of post edit
        HorizontalLayout InfoPanel = new HorizontalLayout(LeftInfoPanel, RightInfoPanel);

        // Header of post edits
        HorizontalLayout Header = new HorizontalLayout(returnButton, new H1("Edit Post"));
        Header.setAlignItems(Alignment.CENTER);

        /**
         * post create button declaration and event handeling
         * button will perform various checks on fields in view to ensure info is filled out
         * if all is good, then publish post will be called
         */
        Button editPostButton = new Button("Update post", new Icon(VaadinIcon.THUMBS_UP));
        editPostButton.addClickListener(event -> {
            // all fields are filled
            if (binder.writeBeanIfValid(postController) && !tags.isEmpty()) {
                infoLabel.setText("Saved bean values: " + postController);
                // Postal code format check
                if(!postalMatch.get()){
                    Notification postalNotification = new Notification("Invalid Postal Code",3000, Notification.Position.MIDDLE);
                    postalNotification.open();
                }
                else {
                    updatePost(title.getValue(),description.getValue(),postalCodeField.getValue(),tagList,privacySelect.getValue(),email.getValue());
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

        add(Header, InfoPanel, editPostButton);
    }

    /**
     * fills in fields with edited post fields fields
     */
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {

        post = searchController.getPostByID(id);
        postController.setCurrentInspectedPost(post);

        title.setPlaceholder(postController.getPostTitle());
        title.setValue(postController.getPostTitle());

        description.setPlaceholder(postController.getPostDescription());
        description.setValue(postController.getPostDescription());

        postalCodeField.setPlaceholder(postController.getPostalCode());
        postalCodeField.setValue(postController.getPostalCode());

        //tags.setValue((Set<String>) postController.getPostTags());
    }


    /**
     * Sets the ID of the post that was clicked
     * @param beforeEvent
     * @param postId clicked post's id number
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, String postId) {
        id = postId;
    }

    /** publishPost method
     * method uses controller to set all post fields
     * if so, then a new post is made using all the provided info from user
     */
    private void updatePost(String postTitle, String postDesc, String postalCode, ArrayList<String> postTagsApplied,String postPrivacy,boolean includeEmail){
        postController.setPostTitle(postTitle);
        postController.setPostDescription(postDesc);
        postController.setPostPostalCode(postalCode);
        postController.setPostTags(postTagsApplied);
        postController.setPostPrivacy(postPrivacy);
        if(includeEmail){
            postController.setPostContactEmail(currentAccount.getEmail());
        }
        else {
            postController.setPostContactEmail(null);
        }
        Boolean editSuccess = postController.verifyAndUpdatePost();
        if(editSuccess){
            //currentAccount.updateCreatedPostList(postController.getPostID().toString());

            // Confirmation Dialog Box
            Dialog confirmPosted = new Dialog();
            confirmPosted.setModal(false);
            Button returnButton = new Button("Return Home", new Icon(VaadinIcon.HOME));
            returnButton.addClassName("reset-button");
            returnButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            returnButton.addClickListener(
                    e -> {
                        returnButton.getUI().ifPresent(ui -> ui.navigate(""));
                        confirmPosted.close();
                    });
            VerticalLayout vbox = new VerticalLayout(new H2("Successful Post!"), returnButton);
            vbox.setAlignItems(Alignment.CENTER);
            confirmPosted.add(vbox);
            confirmPosted.setOpened(true);
        }
        else {
            // Confirmation Dialog Box
            Dialog failedPosted = new Dialog();
            failedPosted.setModal(false);
            Button returnButton = new Button("Return Home", new Icon(VaadinIcon.HOME));
            returnButton.addClassName("reset-button");
            returnButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            returnButton.addClickListener(
                    e -> {
                        returnButton.getUI().ifPresent(ui -> ui.navigate(""));
                        failedPosted.close();
                    });
            VerticalLayout vbox = new VerticalLayout(new H2("Successful Post!"), returnButton);
            vbox.setAlignItems(Alignment.CENTER);
            failedPosted.add(vbox);
            failedPosted.setOpened(true);
        }

    }

}
