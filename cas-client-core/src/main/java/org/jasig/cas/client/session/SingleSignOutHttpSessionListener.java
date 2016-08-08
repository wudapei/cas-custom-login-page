/*
 * Copyright 2007 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.ja-sig.org/products/cas/overview/license/index.html
 */
package org.jasig.cas.client.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Listener to detect when an HTTP session is destroyed and remove it from the map of
 * managed sessions.  Also allows for the programmatic removal of sessions.
 * <p>
 * Enables the CAS Single Sign out feature.
 *
 * Scott Battaglia
 * @version $Revision$ Date$
 * @since 3.1
 */
public final class SingleSignOutHttpSessionListener implements HttpSessionListener {

	private SessionMappingStorage sessionMappingStorage;
	
    public void sessionCreated(final HttpSessionEvent event) {
        // nothing to do at the moment
    }

    public void sessionDestroyed(final HttpSessionEvent event) {
    	if (sessionMappingStorage == null) {
    	    sessionMappingStorage = getSessionMappingStorage();
    	}
        final HttpSession session = event.getSession();
        sessionMappingStorage.removeBySessionById(session.getId());
    }

    /**
     * Obtains a {@link org.jasig.cas.client.session.SessionMappingStorage} object. Assumes this method will always return the same
     * instance of the object.  It assumes this because it generally lazily calls the method.
     * 
     * @return the SessionMappingStorage
     */
    protected static SessionMappingStorage getSessionMappingStorage() {
    	return SingleSignOutFilter.getSingleSignOutHandler().getSessionMappingStorage();
    }
}
