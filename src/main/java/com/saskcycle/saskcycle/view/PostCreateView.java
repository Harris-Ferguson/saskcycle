package com.saskcycle.saskcycle.view;

import com.saskcycle.DAO.CurrentUserSettingsDAOInterface;
import com.saskcycle.DAO.PostsDAO;
import com.saskcycle.DAO.PostsDAOInterface;
import com.saskcycle.model.Post;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;


@Route(value = "Create-Posts", layout = PostCreateLayout.class)
@PageTitle("SaskCycle | Post Create")
@Secured("ROLE_USER")
public class PostCreateView extends VerticalLayout {

    @Autowired
    private CurrentUserSettingsDAOInterface currentAccount;

    @Autowired
    private PostsDAOInterface postRepo;

    @Autowired
    private PostsDAO pr;



    public PostCreateView(){
        AtomicBoolean give = new AtomicBoolean(true);

        AtomicBoolean isPostPublic = new AtomicBoolean(true);

        Binder<Post> binder = new Binder<>();

        Label infoLabel = new Label();

        //cancel button
        Button returnButton = new Button("Return",new Icon(VaadinIcon.ARROW_BACKWARD));
        returnButton.addClickListener(e -> returnButton.getUI().ifPresent(ui -> ui.navigate("posts")));

        //Post type select (give away, need)
        Select<String> postType = new Select<>();
        postType.setItems("giving away","looking for");
        postType.setPlaceholder("giving away");
        postType.setLabel("Why are you posting?");
        postType.addValueChangeListener(e -> {
            if(e.getValue().trim() == "giving away"){
                give.set(true);
            }
            else {
                give.set(false);
            }
        });

        //Title Field
        TextField title = new TextField();
        title.setLabel("Post Title");
        title.setPlaceholder("Type here ...");
        title.setMinWidth("600px");
        title.setRequiredIndicatorVisible(true);
        /*binder.forField(title)
                .withValidator(new StringLengthValidator(
                        "Please add a Post Title",1,null))
                .bind(Post::getTitle,Post::setTitle);*/


        //Description Field
        TextArea description = new TextArea();
        description.setLabel("Description");
        description.setPlaceholder("Type here ...");
        description.setMinWidth("600px");
        description.setMinHeight("200px");
        description.setRequiredIndicatorVisible(true);
        /*binder.forField(description)
                .withValidator(new StringLengthValidator(
                        "Please add a Post Description",1,null));*/


        //Location Field
        TextField location = new TextField();
        location.setLabel("Location");
        location.setPlaceholder("Type your location ...");
        location.setMinWidth("600px");
        location.setRequiredIndicatorVisible(true);
        /*binder.forField(location)
                .withValidator(new StringLengthValidator(
                        "Please add a location to your post",1,null));*/


        // Privacy and email/phone check boxes
        Select<String> privacy = new Select<>();
        privacy.setItems("Public","Accounts");
        privacy.setLabel("Post Privacy");
        privacy.setPlaceholder("Public");
        privacy.setMaxWidth("150px");
        privacy.addValueChangeListener(e -> {
            if(e.getValue() == "Public"){
                isPostPublic.set(true);
            }
            else {
                isPostPublic.set(false);
            }
        });

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

        VerticalLayout contactBox = new VerticalLayout(new HorizontalLayout(email,value),phone());
        HorizontalLayout contactPanel = new HorizontalLayout(privacy,contactBox);
        contactPanel.setAlignItems(Alignment.CENTER);


        //Tags list
        MultiSelectListBox<String> tags = new MultiSelectListBox<>();
        tags.setItems("Appliances", "Clothing", "Electronics", "Furniture","Art");
        ArrayList<String> tagList = new ArrayList<>();
        tags.addValueChangeListener(e -> {
            tagList.clear();
            tagList.addAll(e.getValue());
        });

        //Left side of post creation
        VerticalLayout LeftInfoPanel = new VerticalLayout(title,description,location,contactPanel);

        //Right side of post creation
        VerticalLayout RightInfoPanel = new VerticalLayout(new H1("Tags:"),tags);

        //Body of post creation
        HorizontalLayout InfoPanel = new HorizontalLayout(LeftInfoPanel,RightInfoPanel);

        //Header of post creation
        HorizontalLayout Header = new HorizontalLayout(returnButton,new H1("Create Post"),postType);
        Header.setAlignItems(Alignment.CENTER);

        Button createPostButton = new Button("Create Post!",new Icon(VaadinIcon.THUMBS_UP));
        createPostButton.addClickListener(e -> {
            //if(binder.isValid()){
                publishPost(give.get(),title.getValue(),description.getValue(),location.getValue(), tagList,isPostPublic.get(), email.getValue());
            //}
            /*else {
                BinderValidationStatus<Post> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                infoLabel.setText("There are errors: "+ errorText);
            }*/

        });

        add(Header,InfoPanel,createPostButton);
    }


    private void publishPost(boolean givePost,String title, String description, String location, ArrayList<String> tags,  boolean isPostPublic, boolean includeEmail){

        if(title.trim().isEmpty()){
            Notification.show("Enter a Title");
        }
        else if(description.trim().isEmpty()){
            Notification.show("Enter a Description");
        }
        else if(location.trim().isEmpty()){
            Notification.show("Enter a Location");
        }
        else if(tags.isEmpty()){
            Notification.show("Please add some tags");
        }
        else{
            Post newPost = new Post(title,description,String.valueOf(pr.AllPosts().size() + 1),currentAccount.getCurrentAccount(),location,tags);
            newPost.datePosted = new Date();
            newPost.give = givePost;
            newPost.privacy = isPostPublic;
            if(includeEmail){
                newPost.contactEmail = currentAccount.getEmail();
            }
            postRepo.addPost(newPost);


            //Confirmation Dialog Box
            Dialog confirmPosted = new Dialog();
            confirmPosted.setModal(false);
            Button returnButton = new Button("Return Home",new Icon(VaadinIcon.HOME));
            returnButton.addClickListener(e -> {
                returnButton.getUI().ifPresent(ui -> ui.navigate(""));
                confirmPosted.close();
            });
            confirmPosted.add(new H1("Successful Post!"),returnButton);
            confirmPosted.setOpened(true);



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
