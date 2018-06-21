package com.codecool.queststore.dbcreation;

import java.sql.SQLException;

public class DataInserter {

    void start() {

        DbHandler db = new DbHandler();
        try {
            db.connect();

            insertData(db);

            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertData(DbHandler db) throws SQLException {

        db.executeUpdate(generateCreationStatementForLevelChart());
        db.executeUpdate(generateCreationStatementForRoles());
        db.executeUpdate(generateCreationStatementForAdmin());
        db.executeUpdate(generateCreationStatementForMentor());
        db.executeUpdate(generateCreationStatementForClass());
        db.executeUpdate(generateCreationStatementForStudent());
        db.executeUpdate(generateCreationStatementForArtifactsCategories());
        db.executeUpdate(generateCreationStatementForArtifacts());
        db.executeUpdate(generateCreationStatementForQuestCategories());
        db.executeUpdate(generateCreationStatementForQuests());
        db.executeUpdate(generateCreationStatementForFundingGroup());
        db.executeUpdate(generateCreationStatementForPersonalArtifactHistory());

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
        sb      .append("    INSERT INTO users (userLogin, userPassword, userName, userSurname, userEmail, idCodecoolRole)\n")
                .append("    VALUES ('testMentor', '123', 'John', 'Doe', 'john@doe.com', ")
                .append("(SELECT idCodecoolRole from codecoolRole where roleDescription = 'mentor' LIMIT 1));\n")
                .append("\n")
                .append("INSERT INTO mentor (idUser, mentorAddress) VALUES\n" +
                        " ((SELECT idUser FROM users where userEmail='john@doe.com'),'SezameStreet');");
        return sb.toString();
        //SELECT users.user_name, users.user_surname, mentor.mentor_address FROM mentor
        //INNER JOIN users ON users.id_user = mentor.id_mentor
        // user_name | user_surname | mentor_address
        //
        //-----------+--------------+----------------
        // Jane      | Doe          | SezameStreet
        // -> works
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

        sb      .append("    INSERT INTO users (userLogin, userPassword, userName, userSurname, userEmail, idCodecoolRole)\n")
                .append("    VALUES ('test_student1', '123', 'Marry', 'sue', 'marry@sue.com', ")
                .append("(SELECT idCodecoolRole from codecoolRole where roleDescription = 'student' LIMIT 1));\n")
                .append("\n")
                .append("INSERT INTO student (idUser, gitHubAdress, idClass) VALUES\n" +
                        "((SELECT idUser FROM users where userEmail='marry@sue.com'),'marryS/github.com',(SELECT idClass FROM class where classDescription = 'javaoop'));");

//student2

        sb      .append("    INSERT INTO users (userLogin, userPassword, userName, userSurname, userEmail, idCodecoolRole)\n")
                .append("    VALUES ('test_student2', '123', 'Mark', 'Tfre', 'ma@tue.com', ")
                .append("(SELECT idCodecoolRole from codecoolRole where roleDescription = 'student' LIMIT 1));\n")
                .append("\n")
                .append("INSERT INTO student (idUser, gitHubAdress, idClass) VALUES\n" +
                        "((SELECT idUser FROM users where userEmail='ma@tue.com'),'newbie/github.com',(SELECT idClass FROM class where classDescription = 'python'));");

                return sb.toString();
    }

    private String generateCreationStatementForArtifactsCategories() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO artifactCategory (artifactCategoryDescription, isGroupArtifact)\n")
                .append("    VALUES ('personal test category', FALSE), ('group test category', TRUE);");
        return sb.toString();
    }


    private String generateCreationStatementForArtifacts() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO artifact (artifactName, artifactDescription, currentArtifactCost, idArtifactCategory)\n")
                .append("    VALUES ('test artifact personal', 'it does nothing', 1, ")
                    .append("(SELECT idArtifactCategory from artifactCategory where artifactCategoryDescription = 'personal test category' LIMIT 1));\n");
        sb
                .append("    INSERT INTO artifact (artifactName, artifactDescription, currentArtifactCost, idArtifactCategory)\n")
                .append("    VALUES ('test artifact group', 'it does nothing', 10, ")
                .append("(SELECT idArtifactCategory from artifactCategory where artifactCategoryDescription = 'group test category' LIMIT 1));\n");
        return sb.toString();
    }

    private String generateCreationStatementForQuestCategories() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO questCategory (questCategoryDescription, isGroupQuest)\n")
                .append("    VALUES ('personal test category', FALSE), ('group test category', TRUE);");
        return sb.toString();
    }


    private String generateCreationStatementForQuests() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("    INSERT INTO quest (questName, questDescription, questValue, idQuestCategory)\n")
                .append("    VALUES ('test quest personal', 'it does nothing', 1, ")
                .append("(SELECT idQuestCategory from questCategory where questCategoryDescription = 'personal test category' LIMIT 1));\n");
        sb
                .append("    INSERT INTO quest (questName, questDescription, questValue, idQuestCategory)\n")
                .append("    VALUES ('test quest group', 'it does nothing', 10, ")
                .append("(SELECT idQuestCategory from questCategory where questCategoryDescription = 'group test category' LIMIT 1));\n");
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



