package com.codecool.queststore.view;

import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.user.Role;
import com.codecool.queststore.model.user.User;
import com.codecool.queststore.model.user.UserFactory;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateRender implements RenderInteface{

    @Override
    public String RenderClassPage() {

            // get a template file
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/class.html");

            // create a model that will be passed to a template
            JtwigModel model = JtwigModel.newModel();

            return template.render(model);
        }

    @Override
    public String RenderProfilePage(User currentUser, User profile, List<CodecoolClass> classes) {
        /* Mentor profile model:
         *  currentUser - active user
         *  profile - user with data we want to show
         *  classes - list of Codecool Classes assigned to user
         */

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/profile.html");
        TemplateModelInterface tmi = new TemplateModelHandler();

        //render from stab
//        return template.render(getMentorProfileModel());
        //render from args
        return template.render(tmi.getProfileMentorModel(currentUser, profile, classes));
    }

    @Override
    public String RenderProfilePage(User currentUser, User profile, CodecoolClass ccClass, List<Artifact> items) {
        /* Student profile model:
         *  currentUser - active user
         *  profile - user with data we want to show
         *  ccClass - CodecoolClass of user
         *  items - list of user's Artifacts
         */

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/profile.html");
        TemplateModelInterface tmi = new TemplateModelHandler();

        // render from stab
//        return template.render(getStudentProfileModel());

        // render from args
        return template.render(tmi.getProfileStudentModel(currentUser, profile, ccClass, items));
    }

    private JtwigModel getMentorProfileModel() {
        TemplateModelInterface tmi = new TemplateModelHandler();
        User currentUser = new User("Piotr", "Kaminski", "pkaminki95@gmail.com", "address", 1, Role.STUDENT);
        User user = new User("Pawel", "Kaminski", "kaminski21@gmail.com", "address", 2, Role.MENTOR);
        List<CodecoolClass> classes = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            CodecoolClass c = new CodecoolClass("2018." + i, getUserList(Role.MENTOR, 2), getUserList(Role.STUDENT, 5));
            classes.add(c);
        }

        return tmi.getProfileMentorModel(currentUser, user, classes);
    }

    private List<User> getUserList(Role role, int amount) {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            users.add(new User(role.getNAME(), String.valueOf(i), "---", "---", i, role));
        }

        return users;
    }

    private static JtwigModel getStudentProfileModel() {
        TemplateModelInterface tmi = new TemplateModelHandler();
        User currentUser = new User("Piotr", "Kaminski", "pkaminki95@gmail.com", "address", 1, Role.STUDENT);
        User user = new User("Pawel", "Kaminski", "kaminski21@gmail.com", "address", 2, Role.MENTOR);
        CodecoolClass ccClass = new CodecoolClass("2018.1", null, null);
        List<Artifact> artifacts = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Artifact a = new Artifact(i, i,"item" + String.valueOf(i), ".", 1, ".", ".", null, false);
            artifacts.add(a);
        }

        return tmi.getProfileStudentModel(currentUser, user, ccClass, artifacts);
    }

    public String RenderListPage() {

        String title = "students";

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/userList.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    @Override
    public String RenderShopPage() {

        String title = "Shop";

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/shop.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    @Override
    public String RenderQuestPage() {

        String title = "Shop";

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/quests.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    public static void main(String[] args) {
        RenderInteface rf = new TemplateRender();
        System.out.println(rf.RenderProfilePage(null, null, null));
    }
}
