package com.saskcycle.model.authorities;

import com.saskcycle.model.Post;

public class PostDistancePair implements Comparable<PostDistancePair> {
    public Post post;
    public Double distance;

    public PostDistancePair(Post post, Double distance)
    {
        this.post = post;
        this.distance = distance;
    }

    public int compareTo(PostDistancePair o) {
        return -this.distance.compareTo(o.distance);
    }
}
