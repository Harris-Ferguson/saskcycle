package com.saskcycle.saskcycle.view.components;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostalCodeComponent extends Div {
    private TextField postalCodeField;
    private final Pattern postalCodePattern = Pattern.compile("[a-zA-Z][0-9][a-zA-Z][0-9][a-zA-Z][0-9]");
    private Matcher postalMatcher;
    AtomicBoolean postalMatch;

    public PostalCodeComponent() {
        buildPostalCodeField();
    }

    private void buildPostalCodeField(){
        postalCodeField = new TextField();
        postalCodeField.setLabel("Postal Code");
        postalCodeField.setPlaceholder("form: K1A0B1");
        postalCodeField.setMinWidth("150px");
        postalCodeField.setPreventInvalidInput(true);
        postalCodeField.setMaxLength(6);
        postalCodeField.setRequiredIndicatorVisible(true);
        addRegexListener();
        add(postalCodeField);
    }

    private void addRegexListener(){
        postalMatcher = postalCodePattern.matcher(postalCodeField.getValue());
        postalMatch = new AtomicBoolean(postalMatcher.find());
        postalCodeField.addValueChangeListener(e -> {
            postalMatcher.reset(e.getValue());
            postalMatch.set(postalMatcher.find());
        });
    }

    /* this really shouldn't be public but the validator in the PostCreateView
     * needs to use the actual text field component to verify the entire block of inputs
     * We don't have enough time to refactor the PostCreateView since this component itself is a last
     * minute refactor in itself
     */
    public TextField getTextField() {
        return postalCodeField;
    }

    public boolean postalCodeIsValid(){
        return postalMatch.get();
    }

    public String getPostalCode(){
        return postalCodeField.getValue();
    }

    public void setPostalCode(String postalCode){
        postalCodeField.setValue(postalCode);
    }
}
