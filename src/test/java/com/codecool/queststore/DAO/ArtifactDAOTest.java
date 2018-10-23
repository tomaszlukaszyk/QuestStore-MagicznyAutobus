package com.codecool.queststore.DAO;

import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.artifact.ArtifactCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArtifactDAOTest {

    private ArtifactDAO artifactDAO;
    private Artifact artifact;

    @Mock
    private ConnectionPool cp;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void init() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when(cp.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(rs);

        artifactDAO = new ArtifactDAO(cp);
        artifact = new Artifact(1,
                0,
                "Some Quest",
                "Quest description",
                10,
                "quest1.png",
                "quest1_marked.png",
                ArtifactCategory.GROUP,
                false);

        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(artifact.getID());
        when(rs.getString("artifactname")).thenReturn(artifact.getNAME());
        when(rs.getString("artifactdescription")).thenReturn(artifact.getDESCRIPTION());
        when(rs.getInt("currentartifactcost")).thenReturn(artifact.getCOST());
        when(rs.getString("image")).thenReturn(artifact.getIMAGE_FILENAME());
        when(rs.getString("image_marked")).thenReturn(artifact.getIMAGE_MARKED_FILENAME());

    }

    @Test
    void testCreateArtifact() throws SQLException {
        artifactDAO.createArtifact(artifact);
        Mockito.verify(statement).executeQuery();
    }


    @Test
    void testCreateAndRetrieveArtifact() throws SQLException {
        artifactDAO.createArtifact(artifact);
        Artifact retrievedArtifact = artifactDAO.getArtifact(1);
        assertEquals(artifact, retrievedArtifact);
    }
}