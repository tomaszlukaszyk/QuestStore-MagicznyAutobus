package com.codecool.queststore.dbcreation;

import com.codecool.queststore.DAO.ArtifactDAO;
import com.codecool.queststore.DAO.ConnectionPool;
import com.codecool.queststore.DAO.QuestDAO;
import com.codecool.queststore.dao.interfaces.QuestDAOInterface;
import com.codecool.queststore.model.shop.artifact.ArtifactCategory;
import com.codecool.queststore.model.shop.artifact.ArtifactFactory;

import java.sql.SQLException;

public class DataInserter {

    void start() {

        DbHandler db = new DbHandler();
        try {
            db.connect();

            insertData(db);
            new FunctionsCreator().start();

            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertData(DbHandler db) throws SQLException {

        db.executeUpdate(generateCreationStatementForLevelChart());
        db.executeUpdate(generateCreationStatementForRoles());
        db.executeUpdate(generateCreationStatementForAdmin());
        //db.executeUpdate(generateCreationStatementForMentor());
        db.executeUpdate(generateCreationStatementForClass());
        //db.executeUpdate(generateCreationStatementForStudent());
        db.executeUpdate(generateCreationStatementForArtifactsCategories());
        //db.executeUpdate(generateCreationStatementForArtifacts());
        new ArtifactDAO(ConnectionPool.getInstance()).createArtifact(new ArtifactFactory()
                .fromData(0, 0, "Summon Code Elemental", "Mentor joins a student team for one hour (all team members must buy this item)", 3,"elemental.png", "elemental-hover", ArtifactCategory.PERSONAL));
        new ArtifactDAO(ConnectionPool.getInstance()).createArtifact(new ArtifactFactory()
                .fromData(0, 0, "Teleport", "The whole room goes to an off-school program instead for a specified day (which is at least 2 weeks ahead))", 10,"portal.png", "portal-hover", com.codecool.queststore.model.shop.artifact.ArtifactCategory.GROUP));
        new ArtifactDAO(ConnectionPool.getInstance()).createArtifact(new ArtifactFactory()
                .fromData(0, 0, "Tipping the fanfare-guy", "The student can use jukebox exclusively for half day", 5,"town.png", "town-hover", com.codecool.queststore.model.shop.artifact.ArtifactCategory.PERSONAL));

        db.executeUpdate(generateCreationStatementForQuestCategories());
        //db.executeUpdate(generateCreationStatementForQuests());
        QuestDAOInterface questDAO = new QuestDAO();
        questDAO.createTemplate("Enter the arena", "Do a presentation", 15, false,
                "arena.png", "arena-hover");
        questDAO.createTemplate("Fruitful opportunism", "Helping mentor with workshops", 3, false,
                "opportunism.png", "opportunism-hover");
        questDAO.createTemplate("Making arrowheads", "Make Kahoot questions list", 3, true,
                "arrow.png", "arrow-hover");
        //db.executeUpdate(generateCreationStatementForFundingGroup());
        //db.executeUpdate(generateCreationStatementForPersonalArtifactHistory());

    }

    private String generateCreationStatementForRoles() {
        StringBuilder sb = new StringBuilder();
        sb      .append("insert into codecoolRole (roleDescription) VALUES ('admin'),('mentor'),('student');\n");
        return sb.toString();
    }

    private String generateCreationStatementForAdmin() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO users (userLogin, userPassword, userName, userSurname, userEmail, idCodecoolRole)\n")
                .append("    VALUES ('admin', '123', 'Jane', 'Doe', 'j@doe.com', ")
                .append("(SELECT idCodecoolRole from codecoolRole where roleDescription = 'admin' LIMIT 1));\n");
        return sb.toString();
    }

    private String generateCreationStatementForMentor() {
        StringBuilder sb = new StringBuilder();
        sb      .append("    INSERT INTO users (userLogin, userPassword, userName, userSurname, userEmail, userAddress, idCodecoolRole)\n")
                .append("    VALUES ('testMentor', '123', 'John', 'Doe', 'john@doe.com', 'cracow', ")
                .append("(SELECT idCodecoolRole from codecoolRole where roleDescription = 'mentor' LIMIT 1));\n")
                .append("\n");
        return sb.toString();
    }

    private String generateCreationStatementForClass(){
        StringBuilder sb = new StringBuilder();
        sb      .append("    INSERT INTO class (classDescription)\n")
                    .append("    VALUES ('javaOOP'),('python');");

        return sb.toString();
    }

    private String generateCreationStatementForStudent() {
        StringBuilder sb = new StringBuilder();

//student 1

        sb      .append("    INSERT INTO users (userLogin, userPassword, userName, userSurname, userEmail, userAddress, idCodecoolRole)\n")
                .append("    VALUES ('testStudent1', '123', 'Marry', 'sue', 'marry@sue.com', 'cracow', ")
                .append("(SELECT idCodecoolRole from codecoolRole where roleDescription = 'student' LIMIT 1));\n")
                .append("   INSERT INTO student (iduser, idclass)\n")
                .append("   VALUES (3, 1);")
                .append("\n");

//student2

        sb      .append("    INSERT INTO users (userLogin, userPassword, userName, userSurname, userEmail, userAddress, idCodecoolRole)\n")
                .append("    VALUES ('testStudent2', '123', 'Mark', 'Tfre', 'ma@tue.com', 'cracow', ")
                .append("(SELECT idCodecoolRole from codecoolRole where roleDescription = 'student' LIMIT 1));\n")
                .append("   INSERT INTO student (iduser, idclass)\n")
                .append("   VALUES (4, 2);")
                .append("\n");

                return sb.toString();
    }

    private String generateCreationStatementForArtifactsCategories() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO artifactCategory (artifactCategoryDescription, isGroupArtifact)\n")
                .append("    VALUES ('personal', FALSE), ('group', TRUE);");
        return sb.toString();
    }


    private String generateCreationStatementForArtifacts() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO artifact (artifactName, artifactDescription, currentArtifactCost, image, image_marked, idArtifactCategory)\n")
                .append("    VALUES ('test artifact personal', 'it does nothing', 1, 'image', 'image_m', ")
                    .append("(SELECT idArtifactCategory from artifactCategory where artifactCategoryDescription = 'personal' LIMIT 1));\n");
        sb
                .append("    INSERT INTO artifact (artifactName, artifactDescription, currentArtifactCost, image, image_marked, idArtifactCategory)\n")
                .append("    VALUES ('test artifact group', 'it does nothing', 10, 'image2', 'image2_m', ")
                .append("(SELECT idArtifactCategory from artifactCategory where artifactCategoryDescription = 'group' LIMIT 1));\n");
        return sb.toString();
    }

    private String generateCreationStatementForQuestCategories() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO questCategory (questCategoryDescription, isGroupQuest)\n")
                .append("    VALUES ('personal', FALSE), ('group', TRUE);");
        return sb.toString();
    }


    private String generateCreationStatementForQuests() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO quest (questName, questDescription, questValue, image, image_marked, idQuestCategory)\n")
                .append("    VALUES ('test quest personal', 'it does nothing', 1, 'image3','image3_marked',")
                .append("(SELECT idQuestCategory from questCategory where questCategoryDescription = 'personal' LIMIT 1));\n");
        sb
                .append("    INSERT INTO quest (questName, questDescription, questValue,image, image_marked, idQuestCategory )\n")
                .append("    VALUES ('test quest group', 'it does nothing', 10, 'image4','image4_marked',")
                .append("(SELECT idQuestCategory from questCategory where questCategoryDescription = 'group' LIMIT 1));\n");
        return sb.toString();
    }

    private String generateCreationStatementForLevelChart() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO levelChart (levelTitle) \n")
                .append("    VALUES ('noobie'),('rookie'),('apprentice'),('codecooler'),('hotcoder'),('codemaster'),('op dev');");
        return sb.toString();
    }

    private String generateCreationStatementForFundingGroup() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO artifactFundingGroup (groupDescription, idArtifact, cost) \n")
                .append("    VALUES ('workshop about databases',(SELECT idArtifact from artifact where artifactName = 'test artifact group'), 100),")
                .append("           ('workshop about multithreading',(SELECT idArtifact from artifact where artifactName = 'test artifact group'), 200);");
        return sb.toString();
    }

    private String generateCreationStatementForPersonalArtifactHistory(){
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO personalArtifactHistory (idStudent, idArtifact, cost) \n")
                .append("    VALUES (1,(SELECT idArtifact from artifact where artifactName = 'test artifact personal'), 10),")
                .append(" (1,(SELECT idArtifact from artifact where artifactName = 'test artifact personal'), 10),")
                .append(" (2,(SELECT idArtifact from artifact where artifactName = 'test artifact personal'), 10),")
                .append(" (1,(SELECT idArtifact from artifact where artifactName = 'test artifact personal'), 200);");
        return sb.toString();

    }






}



