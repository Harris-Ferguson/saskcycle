package com.saskcycle.model;

public enum Tags {
    Appliances,
    Art,
    Cans,
    Clothing,
    Cookware,
    Decorations,
    Electronics,
    Furniture,
    Metal,
    Paper,
    Toys,
    Wood;

    public static final Tags[] TAGS = new Tags[]{Tags.Appliances,Tags.Art,Tags.Cans,Tags.Clothing,
            Tags.Clothing,Tags.Cookware,Tags.Decorations,Tags.Electronics,Tags.Furniture,Tags.Metal,
            Tags.Paper,Tags.Toys,Tags.Wood};

    public static Tags[] getValues() {
        return (Tags[])TAGS.clone();

    }
}


