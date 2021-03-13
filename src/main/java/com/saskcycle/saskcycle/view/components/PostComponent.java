package com.saskcycle.saskcycle.view.components;

import com.saskcycle.model.Post;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

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

    H5 dist = new H5(post.location + " away"); // | " + posts.get(i).datePosted);
    Paragraph desc = new Paragraph(post.description);

    title.addClassName("posts");
    desc.addClassName("posts");

    add(title, dist, desc, formatTags(post));
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
      tagGroup.add(component);
    }
    return tagGroup;
  }
}
