package com.codecool.queststore.dbcreation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class FunctionsCreator {
    private static final String DIR = "src/main/resources/dbcreation/";
    private static final String FILE = "dbsPaths.txt";

    void start() {

        DbHandler db = new DbHandler();
        try {
            db.connect();

            dumpDbScriptsFromFileToStringB(getPaths(), db);

            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dumpDbScriptsFromFileToStringB(List<String> fileName, DbHandler db) throws SQLException {

        for (String filePath: fileName) {
            db.executeUpdate(" "+ loadDbScript(filePath).toString() + " ");
        }

    }

    private StringBuilder loadDbScript(String filePath) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(DIR + filePath))) {
            for (String line; (line = br.readLine()) != null; ) {
                result.append(" " + line + " ");
            }
            return result;
        } catch (IOException e) {
            System.out.println("Error. while trying to load " + DIR + FILE);
        }
    return null;
    }

    private ArrayList<String> getPaths() {
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DIR + FILE))) {
            for (String line; (line = br.readLine()) != null; ) {
                result.add(line);
            }
            return result;
        } catch (IOException e) {
            System.out.println("Error. while trying to load " + DIR + FILE);
        }
        return null;
    }
}
