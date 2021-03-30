package com.saskcycle.saskcycle.view.abstractviews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.controller.PostController;
import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Tags;
import com.saskcycle.saskcycle.view.components.PostalCodeComponent;
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
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractPostForm extends VerticalLayout {
    protected String successMessage;
    protected String failureMessage;
    protected String pageTitle;
    protected final TextField title = new TextField();
    protected final TextArea description = new TextArea();
    protected final MultiSelectListBox<String> tags = new MultiSelectListBox<>();
    protected final PostalCodeComponent postalCodeField = new PostalCodeComponent();

    protected final Binder<PostController> binder = new Binder<>(PostController.class);

    @Autowired
    protected PostController postController;
    @Autowired
    protected SearchController searchController;
    @Autowired
    protected CurrentUserDAOInterface currentAccount;
    protected Select<String> privacySelect = new Select<>();
    protected Select<String> postTypeSelect = new Select<>();

    @PostConstruct
    public void AbstractPostForm() {
        setSuccessMessage();
        setFailureMessage();
        setPageTitle();
        setInspectedPost();

        Button returnButton = createReturnButton();

        Label infoLabel = new Label();

        // Post type select (give away, looking for)
        Div postType = new Div();
        selectPostType();
        postTypeSelect.addValueChangeListener(e ->
                postType.setText(postTypeSelect.getValue()));

        postTitle();
        postDescription();

        // Privacy and email/phone check boxes
        Div postPrivacy = new Div();
        postPrivacy();
        privacySelect.addValueChangeListener(e ->
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
        HorizontalLayout emailBox = new HorizontalLayout(email, curEmail);
        emailBox.setAlignItems(Alignment.BASELINE);
        HorizontalLayout contactPanel = new HorizontalLayout(privacySelect, emailBox);
        contactPanel.setAlignItems(Alignment.CENTER);
        contactPanel.setHeight("75px");
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
        initializeBinder(postType, postPrivacy);

        // Left side of post creation
        VerticalLayout LeftInfoPanel = new VerticalLayout(title, description, postalCodeField, contactPanel);

        // Right side of post creation
        VerticalLayout RightInfoPanel = new VerticalLayout(new H1("Tags:"), tags);

        // Body of post creation
        HorizontalLayout InfoPanel = new HorizontalLayout(LeftInfoPanel, RightInfoPanel);

        // Header of post creation
        HorizontalLayout Header = new HorizontalLayout(returnButton, new H1(pageTitle), postTypeSelect);
        Header.setAlignItems(Alignment.CENTER);

        /**
         * post create button declaration and event handling
         * button will perform various checks on fields in view to ensure info is filled out
         * if all is good, then publish post will be called
         */
        Button createPostButton = new Button("Create Post!", new Icon(VaadinIcon.THUMBS_UP));
        createPostButton.addClickListener(event -> {
            createPostEvent(infoLabel, email, tags, tagList);
        });
        add(Header, InfoPanel, createPostButton);
    }

    private void createPostEvent(Label infoLabel, Checkbox email, MultiSelectListBox<String> tags, ArrayList<String> tagList) {
        // all fields are filled
        if (binder.writeBeanIfValid(postController) && !tags.isEmpty()) {
            infoLabel.setText("Saved bean values: " + postController);
            // Postal code format check
            if (!postalCodeField.postalCodeIsValid()) {
                Notification postalNotification = new Notification("Invalid Postal Code", 3000, Notification.Position.MIDDLE);
                postalNotification.open();
            } else {
                publish(title.getValue(), description.getValue(), postalCodeField.getTextField().getValue(), tagList, privacySelect.getValue(), email.getValue());
            }

        } else {
            // empty tag list check
            if (tagList.isEmpty()) {
                Notification tagsNotification = new Notification("Please add Some tags", 3000, Notification.Position.BOTTOM_CENTER);
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
    }

    private Button createReturnButton() {
        Button returnButton = new Button("Cancel", new Icon(VaadinIcon.ARROW_BACKWARD));
        returnButton.addClickListener(e -> returnButton.getUI().ifPresent(ui -> ui.navigate("posts")));
        return returnButton;
    }

    private void initializeBinder(Div postType, Div postPrivacy) {
        SerializablePredicate<String> typePredicates = value -> !postType.getText().trim().isEmpty();
        SerializablePredicate<String> titlePredicates = value -> !title.getValue().trim().isEmpty();
        SerializablePredicate<String> descriptionPredicates = value -> !description.getValue().trim().isEmpty();
        SerializablePredicate<String> postalPredicates = value -> !postalCodeField.getTextField().getValue().trim().isEmpty();
        SerializablePredicate<String> privacyPredicates = value -> !postPrivacy.getText().trim().isEmpty();

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

        Binder.Binding<PostController, String> postalBinding = binder.forField(postalCodeField.getTextField())
                .withNullRepresentation("")
                .withValidator(postalPredicates, "Please specify a postal code")
                .bind(PostController::getPostalCode, PostController::setPostPostalCode);

        Binder.Binding<PostController, String> privacyBinding = binder.forField(privacySelect)
                .withNullRepresentation("")
                .withValidator(privacyPredicates, "Please specify your post's privacy")
                .bind(PostController::getPostPrivacy, PostController::setPostPrivacy);
    }

    protected abstract void setInspectedPost();

    protected abstract void postTitle();

    protected abstract void postDescription();

    protected abstract Boolean publish();

    protected abstract void setSuccessMessage();

    protected abstract void setFailureMessage();

    protected abstract void setPageTitle();

    protected abstract void updateUserPostList();

    protected Dialog postDialogBox(Boolean success) {
        Dialog dialog = new Dialog();
        dialog.setModal(false);
        Button returnButton = new Button("Return Home", new Icon(VaadinIcon.HOME));
        returnButton.addClickListener(
                e -> {
                    returnButton.getUI().ifPresent(ui -> ui.navigate(""));
                    dialog.close();
                });
        if (success) {
            dialog.add(new H1(successMessage), returnButton);
        } else {
            dialog.add(new H1(failureMessage), returnButton);
        }
        return dialog;
    }

    private void publish(String postTitle,
                         String postDesc,
                         String postalCode,
                         ArrayList<String> postTagsApplied,
                         String postPrivacy, boolean includeEmail) {
        postController.setPostTitle(postTitle);
        postController.setPostDescription(postDesc);
        postController.setPostPostalCode(postalCode);
        postController.setPostTags(postTagsApplied);
        postController.setPostPrivacy(postPrivacy);
        if (includeEmail) {
            postController.setPostContactEmail(currentAccount.getEmail());
        } else {
            postController.setPostContactEmail(null);
        }
        Boolean postSuccess = publish();
        if (postSuccess) {
            updateUserPostList();
        }
        Dialog confirmPosted = postDialogBox(postSuccess);
        confirmPosted.setOpened(true);
    }

    /**
     * Build select for the type of post
     *
     * @return select widget
     */
    protected void selectPostType() {
        postTypeSelect = new Select<>();
        postTypeSelect.setItems("giving away", "looking for");
        postTypeSelect.setPlaceholder("giving or looking");
        postTypeSelect.setLabel("Why are you posting?");
        postTypeSelect.setRequiredIndicatorVisible(true);
    }

    protected void postPrivacy() {
        privacySelect.setItems("Public", "Accounts");
        privacySelect.setPlaceholder("privacy");
        privacySelect.setLabel("Post Privacy");
        privacySelect.setMaxWidth("150px");
        privacySelect.setRequiredIndicatorVisible(true);
    }

}
