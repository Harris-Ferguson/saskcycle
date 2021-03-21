package com.saskcycle.saskcycle.view.components;

import com.saskcycle.model.Post;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;


public class FullPostComponent implements HasUrlParameter<Post> {

    public FullPostComponent() {
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Post post) {
        System.out.println(post.title);
    }
}
