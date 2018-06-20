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

            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateStatementTableLevelChart() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("level_chart")
                .append(" ( ")
                .append("id_level_chart SERIAL PRIMARY KEY, ")
                .append("level_title TEXT UNIQUE NOT NULL ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableShopGroup() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("shop_group")
                .append(" ( ")
                .append("id_shop_group SERIAL PRIMARY KEY, ")
                .append("group_description TEXT NOT NULL, ")
                .append("id_artifact INTEGER REFERENCES artifact(id_artifact), ")
                .append("is_funding_achieved BOOLEAN NOT NULL DEFAULT FALSE ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableGroupHistory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("group_history")
                .append(" ( ")
                .append("id_funding_history SERIAL PRIMARY KEY, ")
                .append("donation INTEGER NOT NULL, ")
                .append("id_student INTEGER REFERENCES student(id_student), ")
                .append("id_shop_group INTEGER REFERENCES shop_group(id_shop_group) ")
                .append(");");
        return sb.toString();
    }


    private String generateStatementTableArtifactHistory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("artifact_history")
                .append(" ( ")
                .append("id_artifact_history SERIAL PRIMARY KEY, ")
                .append("id_student INTEGER REFERENCES student(id_student), ")
                .append("id_artifact INTEGER REFERENCES artifact(id_artifact), ")
                .append("is_used BOOLEAN NOT NULL DEFAULT FALSE ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableQuestsHistory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("quest_history")
                .append(" ( ")
                .append("id_quest_history SERIAL PRIMARY KEY, ")
                .append("id_student INTEGER REFERENCES student(id_student), ")
                .append("id_quest INTEGER REFERENCES quest(id_quest), ")
                .append("status TEXT ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableQuest() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("quest")
                .append(" ( ")
                .append("id_quest SERIAL PRIMARY KEY, ")
                .append("quest_name TEXT UNIQUE NOT NULL, ")
                .append("quest_description TEXT NOT NULL, ")
                .append("quest_value INTEGER NOT NULL, ")
                .append("id_quest_category INTEGER REFERENCES quest_category(id_quest_category) ")
                .append(");");
        return sb.toString();

    }

    private String generateStatementTableArtifact() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("artifact")
                .append(" ( ")
                .append("id_artifact SERIAL PRIMARY KEY, ")
                .append("artifact_name TEXT NOT NULL UNIQUE, ")
                .append("artifact_description TEXT, ")
                .append("artifact_cost INTEGER NOT NULL, ")
                .append("id_artifact_category INTEGER REFERENCES artifact_category(id_artifact_category) ")
                .append(");");
        return sb.toString();

    }

    private String generateStatementTableQuestCategory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("quest_category")
                .append(" ( ")
                .append("id_quest_category SERIAL PRIMARY KEY, ")
                .append("quest_category_description TEXT NOT NULL UNIQUE, ")
                .append("is_group_quest BOOLEAN ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableArtifactCategory() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("artifact_category")
                .append(" ( ")
                .append("id_artifact_category SERIAL PRIMARY KEY, ")
                .append("artifact_category_description TEXT NOT NULL UNIQUE, ")
                .append("is_group_artifact BOOLEAN ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableMentorsClasses() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("mentor_class")
                .append(" ( ")
                .append("id_mentor INTEGER REFERENCES mentor(id_mentor), ")
                .append("id_class INTEGER REFERENCES class(id_class), ")
                .append("PRIMARY KEY (id_mentor, id_class) ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableStudent() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("student")
                .append(" ( ")
                .append("id_student SERIAL PRIMARY KEY, ")
                .append("git_hub_adress TEXT, ")
                .append("coolcoins INTEGER NOT NULL, ")
                .append("xp_points INTEGER NOT NULL, ")
                .append("id_user INTEGER REFERENCES users(id_user), ")
                .append("id_class INTEGER REFERENCES class(id_class) ")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableCodecoolRole() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("codecool_role")
                .append(" ( ")
                .append("id_codecool_role SERIAL PRIMARY KEY, ")
                .append("role_description TEXT UNIQUE")
                .append(");");
        return sb.toString();
    }

    private String generateStatementTableUsers() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("users")
                .append(" ( ")
                .append("id_user SERIAL PRIMARY KEY, ")
                .append("user_surname TEXT NOT NULL, ")
                .append("user_name TEXT NOT NULL, ")
                .append("user_email TEXT UNIQUE, ")
                .append("user_login TEXT UNIQUE, ")
                .append("salt INTEGER, ")
                .append("user_password TEXT NOT NULL, ")
                .append("role_id INTEGER REFERENCES codecool_role(id_codecool_role)")
                .append(" );");
        return sb.toString();
    }


    private String generateStatementTableMentor() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("mentor")
                .append(" ( ")
                .append("id_mentor SERIAL PRIMARY KEY, ")
                .append("mentor_address TEXT, ")
                .append("id_user INTEGER REFERENCES users(id_user)")
                .append(");");
        return sb.toString();
    }


    private String generateStatementTableClass() {
        StringBuilder sb = new StringBuilder();
        sb      .append("CREATE TABLE IF NOT EXISTS ")
                .append("class")
                .append(" ( ")
                .append("id_class SERIAL PRIMARY KEY, ")
                .append("class_description TEXT ") //todo: maybe change that name to class_name?
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
        db.executeUpdate(generateStatementTableArtifactHistory());
        db.executeUpdate(generateStatementTableShopGroup());
        db.executeUpdate(generateStatementTableGroupHistory());
        db.executeUpdate(generateStatementTableLevelChart());

    }

}
