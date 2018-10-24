package com.codecool.queststore.controller.server.service;

import com.codecool.queststore.dao.interfaces.ClassDAOInterface;
import com.codecool.queststore.dao.interfaces.UserDAOInterface;
import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.server.session.Session;
import com.codecool.queststore.model.server.session.SessionPool;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.view.RenderInteface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;

class ClassServiceTest {

    private final String basicClassPagePath = "/class";
    private final String classDetailPagePath = "/class/1";
    private final String addClassPagePath = "/classes/add";
    private final String assignToClassPagePath = "/class/assign/1";
    private final String assignMentorToClassPath = "/class/assign/mentor/1:1";

    private final String basicClassPageResponse = "Class page, user, classes";
    private final String classPageWithDetailResponse = "Class page, user, classes, target class";
    private final String addClassPageResponse = "Add class page";
    private final String assignToClassPageResponse = "Class page, user, classes, users, id";
    private final String classPageWithMessageResponse = "Class page, user, classes, message";

    private ClassService classService;
    private User user;
    private List<CodecoolClass> classes;
    private Session session;

    @Mock
    private ClassDAOInterface classDAOInterface;

    @Mock
    private RenderInteface renderInteface;

    @Mock
    private UserDAOInterface userDAOInterface;

    private HttpCookie cookie;

    @BeforeEach
    void init() throws SQLException {
        MockitoAnnotations.initMocks(this);
        user = createUser(Role.ADMIN);
        classes = createClasses();
        session = SessionPool.getNewSession(user.getID());
        cookie = session.getCookie();

        when(userDAOInterface.getUser(any(Integer.class))).thenReturn(user);
        when(userDAOInterface.getUsers()).thenReturn(new ArrayList<>());

        when(classDAOInterface.getClasses()).thenReturn(classes);
        when(classDAOInterface.assignMentor(any(Integer.class), any(Integer.class))).thenReturn(true);

        when(renderInteface.RenderClassPage(any(User.class), anyListOf(CodecoolClass.class)))
                .thenReturn(basicClassPageResponse);
        when(renderInteface.RenderClassPage(any(User.class), anyListOf(CodecoolClass.class), any(CodecoolClass.class)))
                .thenReturn(classPageWithDetailResponse);
        when(renderInteface.RenderClassPage(any(User.class), anyListOf(CodecoolClass.class),
                anyListOf(User.class), any(Integer.class)))
                .thenReturn(assignToClassPageResponse);
        when(renderInteface.RenderClassPage(any(User.class), anyListOf(CodecoolClass.class), any(String.class)))
                .thenReturn(classPageWithMessageResponse);
        when(renderInteface.renderAddClassTemplatesPage(any(User.class))).thenReturn(addClassPageResponse);
    }

    @AfterEach
    void cleanup() {
        SessionPool.terminate(session);
    }

    @Test
    void testRendersBasicClassPage() throws SQLException {
        classService = new ClassService(cookie, basicClassPagePath, classDAOInterface, renderInteface, userDAOInterface);
        String response = classService.generateResponseBody();
        assertEquals(response, basicClassPageResponse);
    }

    @Test
    void testRendersClassPageWithClassDetail() throws SQLException {
        classService = new ClassService(cookie, classDetailPagePath, classDAOInterface, renderInteface, userDAOInterface);
        String response = classService.generateResponseBody();
        assertEquals(response, classPageWithDetailResponse);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/class/abc", "/class/assign/abc"})
    void testRendersBasicClassPageWhenPathMalformed(String malformedPath) throws SQLException {
        classService = new ClassService(cookie, malformedPath, classDAOInterface, renderInteface, userDAOInterface);
        String response = classService.generateResponseBody();
        assertEquals(response, basicClassPageResponse);
    }

    @Test
    void testRendersAssignToClassPage() throws SQLException {
        classService = new ClassService(cookie, assignToClassPagePath, classDAOInterface, renderInteface, userDAOInterface);
        String response = classService.generateResponseBody();
        assertEquals(response, assignToClassPageResponse);
    }

    @Test
    void testRendersAddClassPage() throws SQLException {
        classService = new ClassService(cookie, addClassPagePath, classDAOInterface, renderInteface, userDAOInterface);
        String response = classService.generateResponseBody();
        assertEquals(response, addClassPageResponse);
    }

    @Test
    void testRendersClassPageWithMessageWhenMentorAssigned() throws SQLException {
        classService = new ClassService(cookie, assignMentorToClassPath, classDAOInterface, renderInteface, userDAOInterface);
        String response = classService.generateResponseBody();
        assertEquals(response, classPageWithMessageResponse);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/class/assign/mentor/abc", "/class/assign/mentor/a:b"})
    void testRendersClassPageWithMessageWhenAssignFails(String malformedPath) throws SQLException {
        classService = new ClassService(cookie, malformedPath, classDAOInterface, renderInteface, userDAOInterface);
        String response = classService.generateResponseBody();
        assertEquals(response, classPageWithMessageResponse);
    }

    @Test
    void testRendersClassPageWithMessageWhenUserNotAuthorisedToAssign() throws SQLException {
        classService = new ClassService(cookie, assignToClassPagePath, classDAOInterface, renderInteface, userDAOInterface);
        User mentor = createUser(Role.MENTOR);
        when(userDAOInterface.getUser(any(Integer.class))).thenReturn(mentor);
        String response = classService.generateResponseBody();
        assertEquals(response, classPageWithMessageResponse);
    }

    private User createUser(Role role) {
        return new User("Jan",
                "Kowalski",
                "jkowalski@gmail.com",
                "Slusarska 5",
                1,
                role);
    }

    private List<CodecoolClass> createClasses() {
        List<CodecoolClass> classes = new ArrayList<>();
        classes.add(new CodecoolClass(1, "Class1", null, null));
        classes.add(new CodecoolClass(2, "Class2", null, null));
        return classes;
    }
}