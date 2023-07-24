package org.jflores.apiservlet.webapp.headers.services;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface LoginService {
    Optional<String> getUsername(HttpServletRequest request);
}
