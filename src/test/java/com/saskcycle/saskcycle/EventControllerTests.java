package com.saskcycle.saskcycle;

import com.saskcycle.controller.AccountController;
import com.saskcycle.controller.EventController;
import com.saskcycle.model.Account;
import com.saskcycle.model.Event;
import com.saskcycle.model.Tags;
import com.saskcycle.repo.EventRepo;
import com.saskcycle.repo.UserAccountRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest(classes = SaskCycleApplication.class)
public class EventControllerTests {

    @Autowired
    EventController eventController;

    Event testEvent;

    @Autowired
    AccountController accountController;

    @Autowired
    UserAccountRepo userAccountRepo;

    @Autowired
    EventRepo eventsRepo;

    @Before
    public void createEvent(){
        accountController.registerOrg("RobinTheEpicOrgUser", "testorgemail@gmail.com", "password");
        Account org = userAccountRepo.findByUsername("RobinTheEpicOrgUser");
        ArrayList<String> tags = new ArrayList<>();
        tags.add(Tags.Electronics.toString());
        testEvent = new Event(new int[]{1,1,1,1}, new int[]{1,1,1,1}, "Test Title", org, tags, "Test Description", new ArrayList<>());
    }

    @After
    public void deleteUsedAccount(){
        userAccountRepo.deleteAccountByUsername("RobinTheEpicOrgUser");
    }

    @Test
    public void testEventPostingCreation(){
        createEvent();
        eventController.addEvent(testEvent);
        Assertions.assertTrue(eventsRepo.existsById(testEvent.id));
        eventController.deleteEvent(testEvent);
    }

    @Test
    public void testEventDeletion(){
        createEvent();
        eventController.addEvent(testEvent);
        Assertions.assertTrue(eventsRepo.existsById(testEvent.id));
        eventController.deleteEvent(testEvent);
        Assertions.assertFalse(eventsRepo.existsById(testEvent.id));
    }

    @Test
    public void testGetEventByTitle(){
        createEvent();
        eventController.addEvent(testEvent);
        Event get = eventController.getEventByTitle("Test Title");
        Assertions.assertNotNull(get);
        eventController.deleteEvent(testEvent);
    }
}
