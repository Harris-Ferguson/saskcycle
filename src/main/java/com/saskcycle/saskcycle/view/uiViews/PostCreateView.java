package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.abstractviews.AbstractPostForm;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "Create-Posts", layout = MainLayout.class)
@PageTitle("SaskCycle | Post Create")
@Secured("ROLE_USER")
public class PostCreateView extends AbstractPostForm {

  public void setInspectedPost(){
      postController.setCurrentInspectedPost(new Post());
  }

    /**
     * Build title are for post create
     * @return textfield title widget
     */
  protected void postTitle(){
      title.setLabel("Post Title");
      title.setPlaceholder("Type here ...");
      title.setMinWidth("600px");
      title.setRequiredIndicatorVisible(true);
  }

    /**
     * Build Description for post create
     * @return TextArea description widget
     */
  protected void postDescription(){
    description.setLabel("Description");
    description.setPlaceholder("Type here ...");
    description.setMinWidth("600px");
    description.setMinHeight("200px");
    description.setRequiredIndicatorVisible(true);
  }

  protected Boolean publish(){
      postController.setPostID();
      postController.setPostType(postTypeSelect.getValue());
      return postController.verifyAndPublish();
  }

    @Override
    protected void setSuccessMessage() {
        successMessage = "Successful Post!";
    }

    @Override
    protected void setFailureMessage() {
        failureMessage = "There was an error creating the post!";
    }

    @Override
    protected void setPageTitle(){ pageTitle = "Create Post";}

    @Override
    protected void updateUserPostList() {
        currentAccount.updateCreatedPostList(postController.getPostID());
    }
}
