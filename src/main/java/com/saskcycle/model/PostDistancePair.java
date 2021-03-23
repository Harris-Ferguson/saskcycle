package com.saskcycle.model;

public class PostDistancePair implements Comparable<PostDistancePair> {
    public Post post;
    public Double distance;

    public PostDistancePair(Post post, Double distance)
    {
        this.post = post;
        this.distance = distance;
    }

    /**
     * CompareTo for the comparable interface
     * @param o other post distance pair
     * @return compare to normal values
     */
    public int compareTo(PostDistancePair o) {
        // THIS NEEDS TO BE NEGATIVE
        return -this.distance.compareTo(o.distance);
    }
}
