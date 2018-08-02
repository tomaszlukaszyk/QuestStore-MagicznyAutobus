package com.codecool.queststore.DAO;

import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.artifact.ArtifactCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAO implements Connectable {

    public void createArtifact(Artifact artifact) throws SQLException {
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT createArtifact(?, ?, ?, ?, ?, ?)");
        stmt.setString(1, artifact.getNAME());
        stmt.setString(2, artifact.getDESCRIPTION());
        stmt.setInt(3, artifact.getCOST());
        stmt.setBoolean(4, artifact.getCATEGORY().equals(ArtifactCategory.GROUP));
        stmt.setString(5, artifact.getIMAGE_FILENAME());
        stmt.setString(6, artifact.getIMAGE_MARKED_FILENAME());
        stmt.executeQuery();
        stmt.close();
        conn.close();
        cp.printDbStatus();
    }

    public void updateArtifact(Artifact artifact) throws SQLException {
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT updateArtifact(?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, artifact.getID());
        stmt.setString(2, artifact.getNAME());
        stmt.setString(3, artifact.getDESCRIPTION());
        stmt.setInt(4, artifact.getCOST());
        stmt.setString(5, artifact.getIMAGE_FILENAME());
        stmt.setString(6, artifact.getIMAGE_MARKED_FILENAME());
        stmt.close();
        conn.close();
        cp.printDbStatus();
    }

    public List<Artifact> getUsersNotUsedArtifactsById(int id) throws SQLException {

        String name = null;
        String description = null;

        Connection conn = cp.getConnection();
        try {
            PreparedStatement stmt;
            stmt= conn.prepareStatement(
                    " SELECT artifactName," +
                    " artifactDescription\n" +
                    " FROM artifact INNER JOIN personalArtifactHistory ON " +
                    "artifact.idArtifact = personalArtifactHistory.idArtifact" +
                    " WHERE idArtifactCategory = 1 AND isUsed = false " +
                    "AND idStudent = (select idStudent FROM student where idUser = ?);\n"
            );
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            List<Artifact> artifactList = new ArrayList<>();
            while (rs.next()) {
                name = rs.getString("artifactName");
                description = rs.getString("artifactDescription");
                artifactList.add(new Artifact(0, 0,
                name, description, 0, null, null,
                ArtifactCategory.PERSONAL, false));
            }
            rs.close();
            stmt.close();
            conn.close();
            cp.printDbStatus();
            return artifactList;

            } catch (SQLException e) {
                e.printStackTrace();
        }
        return null;
    }

}
