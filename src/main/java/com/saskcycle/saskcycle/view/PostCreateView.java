package com.saskcycle.saskcycle.view;

import com.saskcycle.DAO.AccountDAO;
import com.saskcycle.DAO.CurrentUserSettingsDAOInterface;
import com.saskcycle.model.Account;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;


@Route(value = "Create-Posts", layout = PostCreateLayout.class)
@PageTitle("SaskCycle | Post Create")
@Secured("ROLE_USER")
public class PostCreateView extends VerticalLayout {

    @Autowired
    private CurrentUserSettingsDAOInterface currentAccount;

    public PostCreateView(){

        TextField title = new TextField();
        title.setLabel("Post Title");
        title.setPlaceholder("Type here ...");
        title.setMinWidth("600px");

        TextArea description = new TextArea();
        description.setLabel("Description");
        description.setPlaceholder("Type here ...");
        description.setMinWidth("600px");
        description.setMinHeight("200px");

        TextField location = new TextField();
        location.setLabel("Location");
        location.setPlaceholder("Type your location ...");
        location.setMinWidth("600px");

        Select<String> privacy = new Select<>();
        privacy.setItems("Public","Accounts");
        privacy.setLabel("Post Privacy");
        privacy.setMaxWidth("150px");

        HorizontalLayout hbox1 = new HorizontalLayout();

        VerticalLayout vbox1 = new VerticalLayout();
        vbox1.add(title,description,location);
        HorizontalLayout hbox2 = new HorizontalLayout();
        VerticalLayout contactBox = new VerticalLayout(email(),phone());
        hbox2.add(privacy,contactBox);
        hbox2.setAlignItems(Alignment.CENTER);
        vbox1.add(hbox2);

        hbox1.add(vbox1);

        add(new H1("Create Post"),hbox1);
    }

    private HorizontalLayout email(){
        Checkbox email = new Checkbox();
        Div value = new Div();
        value.setText("Email: ");
        email.addValueChangeListener(event ->{
            if( event.getValue()){
                value.setText("Email: "+ currentAccount.getEmail());
            }
            else {
                value.setText("Email: ");
            }
        });
        return new HorizontalLayout(email,value);
    }
    private HorizontalLayout phone(){
        Checkbox phone = new Checkbox();
        Div value = new Div();
        value.setText("phone: ");
        phone.addValueChangeListener(event ->{
            if( event.getValue()){
                value.setText("phone: ");
            }
            else {
                value.setText("phone: ");
            }
        });
        return new HorizontalLayout(phone,value);
    }


}
