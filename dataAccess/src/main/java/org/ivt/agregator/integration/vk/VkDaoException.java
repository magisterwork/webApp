package org.ivt.agregator.integration.vk;

import org.ivt.agregator.dao.exception.DaoException;

public class VkDaoException extends DaoException {
    public VkDaoException() {
    }

    public VkDaoException(String s) {
        super(s);
    }

    public VkDaoException(Exception e) {
        super(e);
    }

    public VkDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public VkDaoException(Throwable cause) {
        super(cause);
    }

}
