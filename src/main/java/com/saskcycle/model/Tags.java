package com.saskcycle.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum Tags {

    /* --------- Tag Titles ------------ */
    SmallAppliances("Small Appliances"),
    HomeIndoor("Home - Indoor"),
    HomeOutdoor("Home - Outdoor"),
    Clothing("Clothing/Wearables"),
    Cookware("Cookware"),
    Recyclables("Recycleables"),
    Electronics("Electronics"),
    Furniture("Furniture"),
    LargeAppliances("Large Appliances"),
    Paper("Paper"),
    Toys("Toys"),
    BuildingSupplies("Building Supplies"),
    SportingGoods("Sporting Goods"),
    Transportation("Transportation");

    /* ----------- Methods ------------- */

    public final String label;

    private Tags(String s) {
        this.label = s;
    }

    /**
     * Gets the current systemwide tags currenlty implemented by the application
     *
     * @return an Arrary of Strings representing the usaeable tags in the system
     */
    public static String[] getTagNames() {
        String[] tags = new String[Tags.values().length];
        int index = 0;
        for (Tags value : Tags.values()) {
            tags[index] = value.label;
            index += 1;

        }
        Arrays.sort(tags);
        return tags;
    }
    public static ArrayList<String> getFrontPageTags() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Home");
        tags.add("Appliances");
        tags.add(Clothing.name());
        tags.add(Cookware.name());
        tags.add(Recyclables.name());
        tags.add(Furniture.name());
        tags.add(Toys.name());
        tags.add(BuildingSupplies.name());
        tags.add(Electronics.name());
        Collections.sort(tags);
        return tags;
    }
}


