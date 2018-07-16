package com.codecool.queststore.dbcreation;

import java.sql.SQLException;

/**
 * Class TablesCreator is a quick implementation of preparing creation statements for initialization database only usage.
 * <p>
 * It's final and package only. Should not be used anywhere else.
 */
final class TablesCreator {

    void start() {

        DbHandler db = new DbHandler();
        try {
            db.connect();

            initializeDatabase(db);
            new DataInserter().start();

            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateStatementTableLevelChart() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("levelChart")
                .append(" ( ")
                .append("idLevelChart SERIAL PRIMARY KEY, ")
                .append("levelTitle TEXT UNIQUE NOT NULL ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableGroupArtifactFunding() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("artifactFundingGroup")
                .append(" ( ")
                .append("idArtifactFundingGroup SERIAL PRIMARY KEY, ")
                .append("groupDescription TEXT NOT NULL, ")
                .append("idArtifact INTEGER REFERENCES artifact(idArtifact), ")
                .append("cost INTEGER NOT NULL ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableGroupArtifactHistory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("groupArtifactHistory")
                .append(" ( ")
                .append("idGroupArtifactHistory SERIAL PRIMARY KEY, ")
                .append("donation INTEGER NOT NULL, ")
                .append("idStudent INTEGER REFERENCES student(idStudent), ")
                .append("date DATE NOT NULL DEFAULT CURRENT_DATE, ")
                .append("idArtifactFundingGroup INTEGER REFERENCES artifactFundingGroup(idArtifactFundingGroup) ")
                .append(");");
        return sb.toString();
    }


    private String generateStatementTablePersonalArtifactHistory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("personalArtifactHistory")
                .append(" ( ")
                .append("idPersonalArtifactHistory SERIAL PRIMARY KEY, ")
                .append("idStudent INTEGER REFERENCES student(idStudent), ")
                .append("idArtifact INTEGER REFERENCES artifact(idArtifact), ")
                .append("cost INTEGER, ")
                .append("date DATE NOT NULL DEFAULT CURRENT_DATE, ")
                .append("isUsed BOOLEAN NOT NULL DEFAULT FALSE ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableQuestsHistory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("questHistory")
                .append(" ( ")
                .append("idQuestHistory SERIAL PRIMARY KEY, ")
                .append("idStudent INTEGER REFERENCES student(idStudent), ")
                .append("idQuest INTEGER REFERENCES quest(idQuest), ")
                .append("date DATE NOT NULL DEFAULT CURRENT_DATE, ")
                .append("status TEXT ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableQuest() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("quest")
                .append(" ( ")
                .append("idQuest SERIAL PRIMARY KEY, ")
                .append("questName TEXT UNIQUE NOT NULL, ")
                .append("questDescription TEXT NOT NULL, ")
                .append("questValue INTEGER NOT NULL, ")
                .append("image TEXT NOT NULL, ")
                .append("image_marked TEXT NOT NULL, ")
                .append("idQuestCategory INTEGER REFERENCES questCategory(idQuestCategory) ")
                .append(");");
        return sb.toString();

    }

    private String generateStatementTableArtifact() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("artifact")
                .append(" ( ")
                .append("idArtifact SERIAL PRIMARY KEY, ")
                .append("artifactName TEXT NOT NULL UNIQUE, ")
                .append("artifactDescription TEXT, ")
                .append("currentArtifactCost INTEGER NOT NULL, ")
                .append("image TEXT NOT NULL, ")
                .append("image_marked TEXT NOT NULL, ")
                .append("idArtifactCategory INTEGER REFERENCES artifactCategory(idArtifactCategory) ")
                .append(");");
        return sb.toString();

    }

    private String generateStatementTableQuestCategory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("questCategory")
                .append(" ( ")
                .append("idQuestCategory SERIAL PRIMARY KEY, ")
                .append("questCategoryDescription TEXT NOT NULL UNIQUE, ")
                .append("isGroupQuest BOOLEAN ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableArtifactCategory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("artifactCategory")
                .append(" ( ")
                .append("idArtifactCategory SERIAL PRIMARY KEY, ")
                .append("artifactCategoryDescription TEXT NOT NULL UNIQUE, ")
                .append("isGroupArtifact BOOLEAN ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableMentorsClasses() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("mentor_class")
                .append(" ( ")
                .append("idMentor INTEGER REFERENCES mentor(idMentor), ")
                .append("idClass INTEGER REFERENCES class(idClass), ")
                .append("PRIMARY KEY (idMentor, idClass) ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableStudent() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("student")
                .append(" ( ")
                .append("idStudent SERIAL PRIMARY KEY, ")
                .append("gitHubAdress TEXT, ")
                .append("idUser INTEGER REFERENCES users(idUser), ")
                .append("idClass INTEGER REFERENCES class(idClass) ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableCodecoolRole() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("codecoolRole")
                .append(" ( ")
                .append("idCodecoolRole SERIAL PRIMARY KEY, ")
                .append("roleDescription TEXT UNIQUE")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableUsers() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("users")
                .append(" ( ")
                .append("idUser SERIAL PRIMARY KEY, ")
                .append("userSurname TEXT NOT NULL, ")
                .append("userName TEXT NOT NULL, ")
                .append("userEmail TEXT UNIQUE, ")
                .append("userLogin TEXT UNIQUE, ")
                .append("salt INTEGER, ")
                .append("userPassword TEXT NOT NULL, ")
                .append("idCodecoolRole INTEGER REFERENCES codecoolRole(idCodecoolRole)")
                .append(" );");
        return sb.toString();
    }


    private String generateStatementTableMentor() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("mentor")
                .append(" ( ")
                .append("idMentor SERIAL PRIMARY KEY, ")
                .append("mentorAddress TEXT, ")
                .append("idUser INTEGER REFERENCES users(idUser)")
                .append(");");
        return sb.toString();
    }


    private String generateStatementTableClass() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("class")
                .append(" ( ")
                .append("idClass SERIAL PRIMARY KEY, ")
                .append("classDescription TEXT ")
                .append(");");
        return sb.toString();
    }

    private void initializeDatabase(DbHandler db) throws SQLException {

        db.executeUpdate(generateStatementTableCodecoolRole());
        db.executeUpdate(generateStatementTableUsers());
        db.executeUpdate(generateStatementTableMentor());
        db.executeUpdate(generateStatementTableClass());
        db.executeUpdate(generateStatementTableStudent());
        db.executeUpdate(generateStatementTableMentorsClasses());
        db.executeUpdate(generateStatementTableArtifactCategory());
        db.executeUpdate(generateStatementTableQuestCategory());
        db.executeUpdate(generateStatementTableArtifact());
        db.executeUpdate(generateStatementTableQuest());
        db.executeUpdate(generateStatementTableQuestsHistory());
        db.executeUpdate(generateStatementTablePersonalArtifactHistory());
        db.executeUpdate(generateStatementTableGroupArtifactFunding());
        db.executeUpdate(generateStatementTableGroupArtifactHistory());
        db.executeUpdate(generateStatementTableLevelChart());

    }
}