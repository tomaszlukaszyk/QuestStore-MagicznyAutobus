package com.codecool.queststore.DAO;

import com.codecool.queststore.model.Title;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TitleDAO implements Connectable {

    public boolean createTitle(String title) throws SQLException {
       try{
            Connection conn = cp.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT addexplevel(?);");
            stmt.setString(1, title);
            stmt.executeQuery();
            stmt.close();
            conn.close();
            return true;
    } catch (SQLException e) {
       return false;}
    }

    public boolean updateTitle(Title title) throws SQLException {
        try {
            Connection conn = cp.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE levelchart " +
                    "SET leveltitle = ? " +
                    "WHERE idlevelchart = ?;");
            stmt.setString(1, title.getNAME());
            stmt.setInt(2,title.getID());
            stmt.executeQuery();
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
        return false;}

    }

    public List<Title> getTitlesList() {
        List<Title> result= new ArrayList<>();
        try {
            Connection conn = cp.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from levelchart ORDER BY idlevelchart;");
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
                result.add(new Title(rs.getInt(1),rs.getString(2)));
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
     }

    public Title getTitle(int id) {
        String name = null;
        try {
            Connection conn = cp.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from levelchart where idlevelchart = ?");
            stmt.setInt(1,id);
            stmt.executeQuery();
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
                name = rs.getString(2);


            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(name != null) {
        return new Title(id,name);
        } else
            return new Title(0,"overly active.<br /> But seriously,<br /> " +
                    "contact administrator: we have no title for you :)");

    }

    public Title getUserTitle(int id){
        Integer exp = 0;
        Integer titleID = null;
        try {
            Connection conn = cp.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT getexp(?);");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next())
                exp = rs.getInt(1);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = cp.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT verifyexplevel(?);");
            stmt.setInt(1, exp);

            ResultSet rs = stmt.executeQuery();

            while(rs.next())
                titleID = rs.getInt(1);

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getTitle(titleID);
        }

    }

