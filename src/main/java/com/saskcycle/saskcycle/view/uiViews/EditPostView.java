package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.abstractviews.AbstractPostForm;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.router.*;

@Route(value = "editPost", layout = MainLayout.class)
public class EditPostView extends AbstractPostForm implements HasUrlParameter<String>, AfterNavigationObserver {
    // Post identifiers
    private String id;
    private Post post;

    /**
     * fills in fields with edited post fields fields
     */
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        post = searchController.getPostByID(id);
        setInspectedPost();

        title.setPlaceholder(postController.getPostTitle());
        title.setValue(postController.getPostTitle());

        description.setPlaceholder(postController.getPostDescription());
        description.setValue(postController.getPostDescription());

        postalCodeField.setPostalCode(postController.getPostalCode());
    }

    /**
     * Sets the post that is currently being edited
     */
    protected void setInspectedPost() {
        postController.setCurrentInspectedPost(post);
    }

    @Override
    protected void postTitle() {
        title.setLabel("Post Title");
        title.setMinWidth("600px");
        title.setRequiredIndicatorVisible(true);
    }

    @Override
    protected void postDescription() {
        description.setLabel("Description");
        description.setMinWidth("600px");
        description.setMinHeight("200px");
        description.setRequiredIndicatorVisible(true);

    }

    @Override
    protected Boolean publish() {
        return postController.verifyAndUpdatePost();
    }

    /**
     * Sets the ID of the post that was clicked
     *
     * @param beforeEvent
     * @param postId      clicked post's id number
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, String postId) {
        id = postId;
    }

    @Override
    protected void setSuccessMessage() {
        successMessage = "Successful Edit!";
    }

    @Override
    protected void setFailureMessage() {
        failureMessage = "There was an error editing the post!";
    }

    @Override
    protected void setPageTitle() {
        pageTitle = "Edit Post";
    }

    @Override
    protected void updateUserPostList() {

    }
}
