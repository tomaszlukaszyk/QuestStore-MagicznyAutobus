package com.codecool.queststore.controller.server.httphandler.codecoolClass;

import com.codecool.queststore.DAO.ClassDAO;
import com.codecool.queststore.DAO.UserDAO;
import com.codecool.queststore.dao.interfaces.ClassDAOInterface;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.view.RenderInteface;
import com.codecool.queststore.view.TemplateRender;

import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

class ClassHelper {
    private final HttpCookie cookie;
    private final String path;

    ClassHelper(HttpCookie cookie, String path) {
        this.cookie = cookie;
        this.path = path;
    }

    String generateResponseBody() throws SQLException {
        final int ACTION_PLACE = 2;
        String[] splitedPath = splitURL(path);
        String message = null;
        ClassDAOInterface classDAOInterface = new ClassDAO();
        RenderInteface renderInteface = new TemplateRender();
        User currentUser = new UserDAO().getUser(SessionPool.getSessionByUUID(UUID.fromString(cookie.getValue())).getUSER_ID());
        CodecoolClass targetClass;
        List<CodecoolClass> classes = classDAOInterface.getClasses();

        System.out.println("rendering class page...");
        System.out.println("current user: " + currentUser.getNAME() + " " + currentUser.getSURNAME());

        if (isAction(splitedPath)) {
            System.out.println("Action: assign");
            if (assignUser(splitedPath, classDAOInterface)) {
                System.out.println("Success!");
                message = "Assigned!";
            } else {
                System.out.println("Failed!");
                message = "Can't perform the operation";
            }
        } else System.out.println("no Action");

        System.out.println("looking for target...");
        targetClass = defineTarget(classes, splitedPath);
        if (targetClass != null) {
            System.out.println("target class: " + targetClass.getNAME());
            return renderInteface.RenderClassPage(currentUser, classes, targetClass);
        } else {
            System.out.println("target class: null");
            if (message == null) {
                return renderInteface.RenderClassPage(currentUser, classes);
            } else {
                return renderInteface.RenderClassPage(currentUser, classes, message);
            }
        }
    }

    private boolean isAction(String[] splitedPath) {
        final int ACTION_PLACE = 2;
        if (ACTION_PLACE < splitedPath.length) {
            return splitedPath[ACTION_PLACE].equals("assign");
        } else
            return false;
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

    private boolean assignUser(String[] array, ClassDAOInterface classDAOInterface) {
        /*
        example path: /class/assign/mentor/1:1
         */
        final int USER_ROLE_PLACE = 3;
        final int IDS_PLACE = 4;
        final int USER_ID_PLACE = 0;
        final int CLASS_ID_PLACE = 1;
        String[] ids = array[IDS_PLACE].split(":");

        if (isStringCastableToInt(ids[USER_ID_PLACE]) && isStringCastableToInt(ids[CLASS_ID_PLACE])) {
            switch (array[USER_ROLE_PLACE]) {
                case "mentor":
                    return classDAOInterface.assignMentor(Integer.parseInt(ids[USER_ID_PLACE]), Integer.parseInt(ids[CLASS_ID_PLACE]));
                case "student":
                    return classDAOInterface.assignMentor(Integer.parseInt(ids[USER_ID_PLACE]), Integer.parseInt(ids[CLASS_ID_PLACE]));
                default:
                    return false;
            }
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
}
