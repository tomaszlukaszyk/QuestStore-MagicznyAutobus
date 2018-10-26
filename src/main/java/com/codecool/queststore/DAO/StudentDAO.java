package com.codecool.queststore.DAO;

import com.codecool.queststore.dao.interfaces.StudentDAOInterface;
import com.codecool.queststore.model.Login;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements StudentDAOInterface, Connectable{


    @Override
    public boolean createStudent(User user, Login login, int classId) throws SQLException {
        boolean ifExist = false;
        Connection conn = cp.getConnection();
        PreparedStatement st = conn.prepareStatement("SELECT * FROM ifUserExists(?, ?)");
        st.setString(1, login.getLOGIN());
        st.setString(2, user.getEMAIL());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            ifExist = rs.getBoolean(1);
        }
        if (!ifExist) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM createStudent(?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, user.getSURNAME());
            stmt.setString(2, user.getNAME());
            stmt.setString(3, user.getEMAIL());
            stmt.setString(4, login.getLOGIN());
            stmt.setString(5, login.getPASSWORD());
            stmt.setString(6, user.getADDRESS());
            stmt.setInt(7, classId);

            stmt.executeQuery();
            stmt.close();
            conn.close();
            return true;
        }
        return false;
    }

    @Override
    public List<User> getStudents() throws SQLException {
        List<User> result = new ArrayList<>();
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM getStudents()");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result.add(new User(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getInt(5), Role.STUDENT));
        }
        rs.close();
        conn.close();
        return result;
    }
}
