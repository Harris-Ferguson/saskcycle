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
    /**
     * Gets the current systemwide tags currenlty implemented by the application
     * @return an Arrary of Strings representing the usaeable tags in the system
     */
    public static String[] getTagNames()
    {
        String[] tags = new String[Tags.values().length];
        int index = 0;
        for (Tags value : Tags.values()) {
            tags[index] =  value.name();
            index += 1;

        }
        return tags;
    }
}


