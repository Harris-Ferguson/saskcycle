package com.saskcycle.saskcycle.view.components;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;

public class FilterComponent extends VerticalLayout {

//    public FilterComponent() {
//        setWidth("200px");
//
//        Select<String> useSelect = new Select<>();
//        useSelect.setItems("Get", "Give");
//        useSelect.setLabel("What do you want to do?");
//
//        Select<String> sortSelect = new Select<>();
//        sortSelect.setItems("Most recent", "Least recent", "Closest to me");
//        sortSelect.setLabel("Sort by");
//
//        CheckboxGroup<String> postChoice = new CheckboxGroup<>();
//        postChoice.setLabel("Show posts from");
//        postChoice.setItems("Users", "Organizations");
//        postChoice.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
//
//        CheckboxGroup<String> includeGroup = new CheckboxGroup<>();
//        includeGroup.setLabel("Show results for");
//        includeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
//        includeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
//
//        includeGroup.addValueChangeListener(event -> {
//            if (event.getValue() == null) {
//                System.out.println("Nothing selected");
//            }
//            else {
//                System.out.println(event.getValue());
//            }
//        });
//
//        CheckboxGroup<String> excludeGroup = new CheckboxGroup<>();
//        excludeGroup.setLabel("Hide results for");
//        excludeGroup.setItems("Appliances", "Clothing", "Electronics", "Furniture");
//        excludeGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
//
//        add(useSelect, sortSelect, postChoice, includeGroup, excludeGroup);
//    }
}
