package com.codecool.queststore.DAO;
import com.codecool.queststore.model.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO  implements Connectable {

    private final static int USER_ID = 1;

    public int validation(Login login) throws SQLException {
        int result = 0;
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM loginValidation(?, ?)");
        stmt.setString(1, login.getLOGIN());
        stmt.setString(2, login.getPASSWORD());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result = rs.getInt(USER_ID);
        }
        rs.close();
        stmt.close();
        conn.close();

        return result;
    }
}

