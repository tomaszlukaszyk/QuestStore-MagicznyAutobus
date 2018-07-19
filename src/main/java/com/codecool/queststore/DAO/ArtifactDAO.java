package com.codecool.queststore.DAO;

import com.codecool.queststore.model.shop.artifact.Artifact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtifactDAO implements Connectable {

    public void createArtifact(String name, String description, int cost, boolean isGroup, String firstImage, String secondImage) throws SQLException {
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT createArtifact(?, ?, ?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, description);
        stmt.setInt(3, cost);
        stmt.setBoolean(4, isGroup);
        stmt.setString(5, firstImage);
        stmt.setString(6, secondImage);
        stmt.executeQuery();
        stmt.close();
        conn.close();
        cp.printDbStatus();
    }

    public void updateArtifact(Artifact artifact) throws SQLException {
        Connection conn = cp.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT updateArtifact(?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, artifact.getId());
        stmt.setString(2, artifact.getName());
        stmt.setString(3, artifact.getDescription());
        stmt.setInt(4, artifact.getCost());
        stmt.setString(5, artifact.getFirstImage());
        stmt.setString(6, artifact.getSecondImage());
        stmt.close();
        conn.close();
        cp.printDbStatus();
    }

}
