package com.codecool.queststore.DAO;

import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.artifact.ArtifactCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
