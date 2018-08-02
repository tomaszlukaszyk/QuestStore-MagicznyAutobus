package com.codecool.queststore.dbcreation;

/**
 * Class DbCreator is used to quickly fill newly created database with tables and,
 * what's even more important - with some essential data.
 * <p>
 * It's final and package only. Should not be used anywhere else.
 */
final class DbCreator {

public static void main(String args[]){
    System.out.println("create tables");
    new TablesCreator().start();
    System.out.println("create functions");
    new FunctionsCreator().start();
    System.out.println("insert data");
    new DataInserter().start();
    }
}
