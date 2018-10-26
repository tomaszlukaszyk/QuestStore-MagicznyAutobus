package com.codecool.queststore.model.server.session;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.net.HttpCookie;


class SessionPoolTest {

    @Test
    void TestIsDifferentSessionForTheSameUser() {
        Session session1 = SessionPool.getNewSession(1);
        Session session2 = SessionPool.getNewSession(1);
        Assertions.assertNotEquals(session1, session2);
    }


    @Test
    void TestIsSessionByCookie(){
        Session session = SessionPool.getNewSession(1);
        HttpCookie cookie = new HttpCookie("ID-Session", session.getUuid().toString());
        Assertions.assertTrue(SessionPool.isSessionByCookie(cookie));
    }
}