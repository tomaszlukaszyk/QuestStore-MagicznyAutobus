package com.codecool.queststore.DAO;

import com.codecool.queststore.dao.interfaces.ArtifactDAOInterface;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.shop.artifact.ArtifactCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAO implements Connectable, ArtifactDAOInterface {

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
    }

    @Override
    public void buyArtifact(int idUser, int artifact) {

        try (Connection conn = cp.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select buypersonalartifact(?,?)");
            stmt.setInt(1, artifact);
            stmt.setInt(2, getIdStudent(idUser, conn));
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getIdStudent(int idUser, Connection conn) {
        String query = "SELECT idstudent FROM student WHERE iduser=" + idUser;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            } else return 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Artifact> getArtifacts() {
        List<Artifact> artifacts = new ArrayList<>();
        String query = "select idartifact, artifactname, artifactdescription, currentartifactcost, image, image_marked, artifactcategorydescription\n" +
                "from artifact join artifactcategory on artifact.idartifactcategory = artifactcategory.idartifactcategory;";

        try (Connection conn = cp.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String desc = rs.getString(3);
                int cost = rs.getInt(4);
                String image = rs.getString(5);
                String markedImage = rs.getString(6);
                String category = rs.getString(7);
                artifacts.add(new Artifact(id, 0, name, desc, cost, image, markedImage, ArtifactCategory.getCategoryByName(category), false));
            }
            return artifacts;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Artifact> getUsersNotUsedArtifactsById(int id) {

        String name;
        String description;

        try (Connection conn = cp.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(" SELECT artifactName" +
                    " artifactDescription" +
                    " FROM artifact INNER JOIN personalArtifactHistory ON " +
                    "artifact.idArtifact = personalArtifactHistory.idArtifact" +
                    " WHERE idArtifactCategory = 1 AND isUsed = false " +
                    "AND idStudent = (select idStudent FROM student where idUser = ?);");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            List<Artifact> artifactList = new ArrayList<>();
            while (rs.next()) {
                name = rs.getString(1);
                description = rs.getString("artifactDescription");
                artifactList.add(new Artifact(0, 0,
                name, description, 0, null, null,
                ArtifactCategory.PERSONAL, false));
            }
            rs.close();
            stmt.close();
            return artifactList;

            } catch (SQLException e) {
                e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ArtifactDAOInterface aDAO = new ArtifactDAO();
        aDAO.buyArtifact(3, 2);

//        for (Artifact a : artifacts) {
//            System.out.println(a.getNAME()+"("+ a.getCATEGORY() + "): " + a.getDESCRIPTION());
//        }
        cp.printDbStatus();
    }
}
