package com.codecool.queststore.dao.interfaces;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Credentials class provides essential data used to create database connection.
 * <p>
 * it takes password, login and url from file, so when using remote repositories
 * we need only add to .gitignore file credentials.cred.
 *
 * @author kret0
 * @version 0.1
 * @since 20.06.2018
 *
 */
class Credentials {

    private static final String DIR = "src/main/resources/";
    private static final String FILE = "credentials.cred";
    private static String url = null;
    private static String login = null;
    private static String password = null;

    static String getDbLogin() {
        if(login == null)
            readDbCredentials();
        return login;
    }
    static String getDbPasswd() {
        if(password == null)
            readDbCredentials();
        return password;
    }
    static String getDbUrl() {
        if(url == null)
            readDbCredentials();
        return url;
    }

    private static void readDbCredentials() {
        int first_line = 1, second_line = 2, third_line = 3;
        try (BufferedReader br = new BufferedReader(new FileReader(DIR + FILE))) {
            int flag = first_line;
            for (String line; (line = br.readLine()) != null; ) {
                if(flag == first_line)
                    login = line;
                else
                    if(flag == second_line)
                        password = line;
                    else
                        if(flag == third_line)
                            url = line;
                    else
                        System.out.println("Warning, 'credentials.cred' file should have only 3 lines! \nFor more info -> Read Readme.md");
                flag++;
            }
        } catch (IOException e) {
            System.out.println("Error. You must create 'credentials.cred' file to use this app.\nFor more info -> Read Readme.md");

        }
    }


}
