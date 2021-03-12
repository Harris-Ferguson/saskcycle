package com.saskcycle.saskcycle.view;

import com.saskcycle.DAO.AccountDAO;
import com.saskcycle.DAO.CurrentUserSettingsDAOInterface;
import com.saskcycle.DAO.PostsDAOInterface;
import com.saskcycle.model.Account;
import com.saskcycle.model.Post;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Global;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;


@Route(value = "Create-Posts", layout = PostCreateLayout.class)
@PageTitle("SaskCycle | Post Create")
@Secured("ROLE_USER")
public class PostCreateView extends VerticalLayout {

    @Autowired
    private CurrentUserSettingsDAOInterface currentAccount;

    @Autowired
    private PostsDAOInterface postRepo;


    public PostCreateView(){
        //Post type select (give away, need)
        Select<String> postType = new Select<>();
        postType.setItems("giving away","looking for");
        postType.setLabel("Why are you posting?");

        //Title Field
        TextField title = new TextField();
        title.setLabel("Post Title");
        title.setPlaceholder("Type here ...");
        title.setMinWidth("600px");

        //Description Field
        TextArea description = new TextArea();
        description.setLabel("Description");
        description.setPlaceholder("Type here ...");
        description.setMinWidth("600px");
        description.setMinHeight("200px");

        //Location Field
        TextField location = new TextField();
        location.setLabel("Location");
        location.setPlaceholder("Type your location ...");
        location.setMinWidth("600px");

        // Privacy and email/phone check boxes
        Select<String> privacy = new Select<>();
        privacy.setItems("Public","Accounts");
        privacy.setLabel("Post Privacy");
        privacy.setMaxWidth("150px");
        VerticalLayout contactBox = new VerticalLayout(email(),phone());
        HorizontalLayout contactPanel = new HorizontalLayout(privacy,contactBox);
        contactPanel.setAlignItems(Alignment.CENTER);

        //Tags list
        MultiSelectListBox<String> tags = new MultiSelectListBox<>();
        tags.setItems("Appliances", "Clothing", "Electronics", "Furniture","Art");
        Iterator<String> iter = tags.getSelectedItems().iterator();
        ArrayList<String> tagList = new ArrayList<>();
        while (iter.hasNext()){
            tagList.add(iter.next());
        }

        //Left side of post creation
        VerticalLayout LeftInfoPanel = new VerticalLayout(title,description,location,contactPanel);

        //Right side of post creation
        VerticalLayout RightInfoPanel = new VerticalLayout(new H1("Tags:"),tags);

        //Body of post creation
        HorizontalLayout InfoPanel = new HorizontalLayout(LeftInfoPanel,RightInfoPanel);

        //Header of post creation
        HorizontalLayout Header = new HorizontalLayout(new H1("Create Post"),postType);
        Header.setAlignItems(Alignment.CENTER);

        Button createPostButton = new Button("Create Post!",new Icon(VaadinIcon.THUMBS_UP));
        createPostButton.addClickListener(e -> publishPost(title.getValue(),description.getValue(),location.getValue(), tagList));

        add(Header,InfoPanel,createPostButton);
    }


    private void publishPost(String title, String description, String location, ArrayList<String> tags){

        //Account curAccount = currentAccount.returnAccount();
        if(title.trim().isEmpty()){
            Notification.show("Enter a Title");
        }
        if(description.trim().isEmpty()){
            Notification.show("Enter a Description");
        }
        if(location.trim().isEmpty()){
            Notification.show("Enter a Location");
        }
        else{
            Post newPost = new Post(title,description,"5",currentAccount.getCurrentAccount(),location,tags);
            postRepo.addPost(newPost);
        }
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
