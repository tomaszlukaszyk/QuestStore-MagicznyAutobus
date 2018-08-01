package com.codecool.queststore.controller.server.httphandler.codecoolClass;

import com.codecool.queststore.DAO.ArtifactDAO;
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
import java.util.ArrayList;
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
        ClassDAOInterface classDAOInterface = new ClassDAO();
        RenderInteface renderInteface = new TemplateRender();
        User currentUser = new UserDAO().getUser(SessionPool.getSessionByUUID(UUID.fromString(cookie.getValue())).getUSER_ID());
        CodecoolClass targetClass;
        List<CodecoolClass> classes = classDAOInterface.getClasses();

        targetClass = defineTarget(classes, path);

        if (targetClass != null) {
            return renderInteface.RenderClassPage(currentUser, classes, targetClass);
        } else {
            return renderInteface.RenderClassPage(currentUser, classes);
        }
    }

    private CodecoolClass defineTarget(List<CodecoolClass> classes, String path) {
        Integer classID = getTargetClassID(splitURL(path));
        CodecoolClass target = null;
        Iterator<CodecoolClass> classIterator = classes.iterator();

        if (classID == null) { return null}
        while (classIterator.hasNext()) {
            CodecoolClass class_ = classIterator.next();
            if (class_.getID() == classID) {
                return class_;
            }
        }
        return null;
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
