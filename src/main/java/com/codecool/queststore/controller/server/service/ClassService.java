package com.codecool.queststore.controller.server.service;

import com.codecool.queststore.DAO.ClassDAO;
import com.codecool.queststore.DAO.UserDAO;
import com.codecool.queststore.dao.interfaces.ClassDAOInterface;
import com.codecool.queststore.dao.interfaces.UserDAOInterface;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.view.RenderInteface;
import com.codecool.queststore.view.TemplateRender;

import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ClassService {
    private final HttpCookie cookie;
    private final String path;
    private ClassDAOInterface classDAOInterface = new ClassDAO();
    private RenderInteface renderInteface = new TemplateRender();
    private UserDAOInterface userDAOInterface = new UserDAO();

    public ClassService(HttpCookie cookie, String path) {
        this.cookie = cookie;
        this.path = path;
    }

    public ClassService(HttpCookie cookie, String path, ClassDAOInterface classDAOInterface, RenderInteface renderInteface, UserDAOInterface userDAOInterface) {
        this.cookie = cookie;
        this.path = path;
        this.classDAOInterface = classDAOInterface;
        this.renderInteface = renderInteface;
        this.userDAOInterface = userDAOInterface;
    }

    public String generateResponseBody() throws SQLException {
        System.out.println("path: " + path);
        String[] splitedPath = splitURL(path);
        User currentUser = userDAOInterface.getUser(SessionPool.getSessionByUUID(UUID.fromString(cookie.getValue())).getUSER_ID());
        CodecoolClass targetClass;
        List<CodecoolClass> classes = classDAOInterface.getClasses();

        System.out.println("rendering class page...");
        System.out.println("current user: " + currentUser.getNAME() + " " + currentUser.getSURNAME());
        System.out.println("looking for target...");
        targetClass = defineTarget(classes, splitedPath);
        if(path.equals("/classes/add")){
            return renderAddClass(currentUser);
        }else if (isAction(splitedPath)) {
            System.out.println("Action: assign");
            return handleAction(splitedPath, currentUser, classes);

        } else if (targetClass != null) {
            System.out.println("target class: " + targetClass.getNAME());
            return renderInteface.RenderClassPage(currentUser, classes, targetClass);

        } else {
            System.out.println("target class: null");
            return renderInteface.RenderClassPage(currentUser, classes);
        }
    }

    private boolean isAction(String[] splitedPath) {
        final int ACTION_PLACE = 2;
        if (ACTION_PLACE < splitedPath.length) {
            return splitedPath[ACTION_PLACE].split(":")[0].equals("assign");
        } else
            return false;
    }

    private String handleAction(String[] splitedPath, User currentUser, List<CodecoolClass> classes) {
        final int TARGET_CLASS_ID_PLACE = 3;
        String message;
        List<User> users;

        if (splitedPath.length == 4) {
            System.out.println("Choose user");

                if (currentUser.getROLE() == Role.ADMIN) {
                    users = userDAOInterface.getUsers(Role.MENTOR);
                    return renderInteface.RenderClassPage(currentUser, classes, users, Integer.parseInt(splitedPath[TARGET_CLASS_ID_PLACE]));
                } else {
                    message = "Can't perform the operation";
                    return renderInteface.RenderClassPage(currentUser, classes, message);
                }



        }else if (assignUser(splitedPath)) {
            System.out.println("Success!");
            message = "Assigned!";
            return renderInteface.RenderClassPage(currentUser, classes, message);
        } else {
            System.out.println("Failed!");
            message = "Can't perform the operation";
            return renderInteface.RenderClassPage(currentUser, classes, message);
        }
    }

    private CodecoolClass defineTarget(List<CodecoolClass> classes, String[] array) {
        Integer classID = getTargetClassID(array);
        Iterator<CodecoolClass> classIterator = classes.iterator();

        if (classID == null) { return null;}
        while (classIterator.hasNext()) {
            CodecoolClass class_ = classIterator.next();
            if (class_.getID() == classID) {
                return class_;
            }
        }
        return null;
    }

    private boolean assignUser(String[] array) {
        /*
        example path: /class/assign/mentor/1:1
         */
        final int IDS_PLACE = 4;
        final int USER_ID_PLACE = 1;
        final int CLASS_ID_PLACE = 0;
        String[] ids = array[IDS_PLACE].split(":");

        if (isStringCastableToInt(ids[USER_ID_PLACE]) && isStringCastableToInt(ids[CLASS_ID_PLACE])) {
            return classDAOInterface.assignMentor(Integer.parseInt(ids[USER_ID_PLACE]), Integer.parseInt(ids[CLASS_ID_PLACE]));
        } else {
            return false;
        }
    }

    private String[] splitURL(String path) {
        return path.split("/");
    }

    private Integer getTargetClassID(String[] array) {
        final int class_ID_PLACE = 2;

        if(array.length > class_ID_PLACE) {
            if(isStringCastableToInt(array[class_ID_PLACE])) {
                return Integer.parseInt(array[class_ID_PLACE]);
            }
        }
        return null;
    }

    private boolean isStringCastableToInt(String string) {
        try{
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String renderAddClass(User currentuser){
        return renderInteface.renderAddClassTemplatesPage(currentuser);
    }
}
