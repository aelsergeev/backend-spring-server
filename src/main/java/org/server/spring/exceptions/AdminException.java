package org.server.spring.exceptions;

import org.server.spring.services.AdminService;

import java.util.logging.Logger;

public class AdminException extends RuntimeException {

    private static final Logger logger = Logger.getLogger(AdminService.class.getName());

    public AdminException(String s) {
        super(s);
        logger.severe(s);
    }

}

