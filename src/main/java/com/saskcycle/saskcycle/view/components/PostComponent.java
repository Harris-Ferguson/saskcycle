package com.saskcycle.saskcycle.view.components;

import com.saskcycle.model.Post;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.text.SimpleDateFormat;

public class PostComponent extends Div {

  /**
   * Constructs the visual set up of the post data
   *
   * @param post
   */
  public PostComponent(Post post) {
    setPost(post);
  }

  /**
   * Formats the post's components into a readable and uniform display
   *
   * @param post the post to be formatted
   */
  private void setPost(Post post) {

    H3 title = new H3(post.title);

    Paragraph desc = new Paragraph(post.description);
    H5 postal = new H5(post.getPostalCode() + " | " + new SimpleDateFormat("yyyy-MM-dd").format(post.datePosted));


    title.addClassName("posts");
    desc.addClassName("posts");
    postal.addClassName("postal");

    add(title, postal, desc, formatTags(post));
  }

  /**
   * Formats the post's tags into a visual representation
   *
   * @param post the post whose tags are to be formatted
   * @return a horizontal layout of the tags which are displayed in boxes
   */
  private HorizontalLayout formatTags(Post post) {

    HorizontalLayout tagGroup = new HorizontalLayout();

    for (int i = 0; i < post.tags.size(); i++) {
      Button component = new Button(post.tags.get(i));
      component.addClassName("reset-button");
      component.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
      tagGroup.add(component);
    }
    return tagGroup;
  }
}
