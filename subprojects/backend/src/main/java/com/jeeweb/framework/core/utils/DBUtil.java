package com.jeeweb.framework.core.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DBUtil.class);

    public static void close(ResultSet rst, Statement stm, Connection con) {
        if (rst != null) {
            try {
                rst.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
