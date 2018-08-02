package com.codecool.queststore.dao.interfaces;

import com.codecool.queststore.model.shop.artifact.Artifact;

import java.sql.SQLException;
import java.util.List;

public interface ArtifactDAOInterface {
    List<Artifact> getArtifacts();
    List<Artifact> getUsersNotUsedArtifactsById(int id);
    void createArtifact(Artifact artifact) throws SQLException;
    void updateArtifact(Artifact artifact) throws SQLException;
    void buyArtifact(int idUser, int artifact);
}
