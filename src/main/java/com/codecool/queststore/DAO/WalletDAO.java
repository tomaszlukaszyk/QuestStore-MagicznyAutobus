package com.codecool.queststore.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletDAO implements Connectable {

    public Integer getWalletExceptGroupArtifacts(int idUser) throws SQLException {
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT showwallet((SELECT idstudent from student where iduser = ?))");
        stmt.setInt(1, idUser);
        ResultSet rs = stmt.executeQuery();
        Integer wallet = 0;

        while (rs.next()) {
            wallet = rs.getInt(1);
        }
        stmt.close();
        conn.close();
        return wallet;
    }
}

