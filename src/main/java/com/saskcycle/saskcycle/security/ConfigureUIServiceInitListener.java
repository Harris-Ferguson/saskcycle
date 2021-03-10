package com.saskcycle.saskcycle.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.saskcycle.saskcycle.view.auth.LoginView;
import org.springframework.stereotype.Component;

/**
 * This class connects Spring Security's Paths to Vaadins view resolver.
 */
@Component 
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> { 
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::authenticateNavigation);
		});
	}

	/**
	 * Reroutes users to the login page if they are trying to access a page which they need
	 * to be logged in for.
	 */
	private void authenticateNavigation(BeforeEnterEvent event) {
		if(!SecurityUtils.isAllowedAccess(event.getNavigationTarget())){
			if(SecurityUtils.isUserLoggedIn()){
				// user doesn't have the right permissions
				event.rerouteToError(NotFoundException.class);
			}
			else{
				// user isn't logged in
				event.rerouteTo(LoginView.class);
			}
		}
	}
}