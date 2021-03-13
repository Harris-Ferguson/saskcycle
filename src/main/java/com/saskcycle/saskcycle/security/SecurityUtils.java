package com.saskcycle.saskcycle.security;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/** Static class containing utility methods for Security */
public final class SecurityUtils {

  private SecurityUtils() {}

  /**
   * Checks if a user is allowed access to a given page
   *
   * @param securedClass the view to find the auth level for
   * @return true if the request is properly auth, false otherwise
   */
  public static boolean isAllowedAccess(Class<?> securedClass) {
    // allow access if the page has no access restrictions
    Secured secured = AnnotationUtils.findAnnotation(securedClass, Secured.class);
    if (secured == null) {
      return true;
    }
    // check if the user has one of the user roles defined on the view's security
    List<String> allowedRoles = Arrays.asList(secured.value());
    Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();
    return userAuthentication.getAuthorities().stream() //
        .map(GrantedAuthority::getAuthority)
        .anyMatch(allowedRoles::contains);
  }

  public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
    final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
    return parameterValue != null
        && Stream.of(ServletHelper.RequestType.values())
            .anyMatch(r -> r.getIdentifier().equals(parameterValue));
  }

  public static boolean isUserLoggedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null
        && !(authentication instanceof AnonymousAuthenticationToken)
        && authentication.isAuthenticated();
  }
}
