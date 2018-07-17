package com.codecool.queststore.DAO;


import com.codecool.queststore.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAO  implements ConnectionInterface {

    public void validation(String userName, String password) throws SQLException {
        Connection conn = cp.getConnection();

    }

}

