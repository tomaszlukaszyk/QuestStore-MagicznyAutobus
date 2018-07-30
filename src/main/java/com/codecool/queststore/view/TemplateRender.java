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
        /* headerData:
            type: Map<String, String>
            key: hName, hSurname, role, wallet
           data:
            type: Map<String, Object>
            key,valueType: classes, List<Map>; mentors, List<Map>; students, List<Map>
            classes keys: id, desc; mentors\students keys: id, fullName
           title:
            type: String
        * */

            Map<String, Object> data = getClassDataMap();
            String title = "Class";

            // get a template file
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/class.html");

            // create a model that will be passed to a template
            JtwigModel model = JtwigModel.newModel();

            // fill the model with values
            model.with("headerData", data.get("headerData"));
            model.with("data", data);
            model.with("title", title);

            return template.render(model);
        }

    private Map<String,Object> getClassDataMap() {
        // mock data
        List<String> classes = new ArrayList<>();
        List<String> mentors = new ArrayList<>();
        List<String> students = new ArrayList<>();

        classes.add("2018.1");
        classes.add("2018.2");
        classes.add("2018.3");

        mentors.add("Tadeusz Baka");

        students.add("Piotr Kaminski");
        students.add("Pawel Kaminski");
        students.add("Jakub Marmol");


        Map<String, Object> data = new HashMap<>();

        data.put("headerData", getHeaderData());
        data.put("classes", classes);
        data.put("mentors", mentors);
        data.put("students", students);

        return data;
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

//    public String RenderProfilePage(JtwigModel model) {
//        /* Model
//         * currentUser - active user
//         * profile - user with data we want to show
//         * ccClass - CodecoolClass of user
//         * items - list of user's Artifacts
//         */
//
//        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/profile.html");
//
//        return template.render(get);
//    }

    private JtwigModel getMentorProfileModel() {
        TemplateModelInterface tmi = new TemplateModelHandler();
        UserFactory uf = new UserFactory();
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
            users.add(new UserFactory().fromData(role.getNAME(), String.valueOf(i), "---", i, role));
        }

        return users;
    }

    private static JtwigModel getStudentProfileModel() {
        TemplateModelInterface tmi = new TemplateModelHandler();
        UserFactory uf = new UserFactory();
        User currentUser = uf.fromData("Piotr", "Kaminski", "pkaminki95@gmail.com", 1, Role.STUDENT);
        User user = uf.fromData("Pawel", "Kaminski", "kaminski21@gmail.com", 2, Role.STUDENT);
        CodecoolClass ccClass = new CodecoolClass("2018.1", null, null);
        List<Artifact> artifacts = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            Artifact a = new Artifact(i, "item" + String.valueOf(i), ".", 1, ".", ".");
            artifacts.add(a);
        }

        return tmi.getProfileStudentModel(currentUser, user, ccClass, artifacts);
    }

    public String RenderListPage() {
        /* headerData:
            type: Map<String, String>
            key: hName, hSurname, role, wallet
           data:
            type: Map<String, Object>
            key,valueType: items, List<Map>
            items keys: id, title, category, desc, cost, fileName
           title:
            type: String
        * */

//        Map<String, Object> data = getProfileDataMap();
        String title = "students";

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/userList.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();
//
//        // fill the model with values
//        model.with("headerData", data.get("headerData"));
//        model.with("data", data);
//        model.with("title", title + " List");
//        model.with("listName", title);

        return template.render(model);
    }

    @Override
    public String RenderShopPage() {
        /* headerData:
            type: Map<String, String>
            key: hName, hSurname, role, wallet
           data:
            type: Map<String, Object>
            key,valueType: classes, List<Map>; mentors, List<Map>; students, List<Map>
            classes keys: id, desc; mentors\students keys: id, fullName
           title:
            type: String
        * */

        Map<String, Object> data = getShopDataMap();
        String title = "Shop";

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/shop.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values
        model.with("headerData", data.get("headerData"));
        model.with("data", data);
        model.with("title", title);

        return template.render(model);
    }

    @Override
    public String RenderQuestPage() {
        /* headerData:
            type: Map<String, String>
            key: hName, hSurname, role, wallet
           data:
            type: Map<String, Object>
            key,valueType: classes, List<Map>; mentors, List<Map>; students, List<Map>
            classes keys: id, desc; mentors\students keys: id, fullName
           title:
            type: String
        * */

        Map<String, Object> data = getQuestDataMap();
        String title = "Shop";

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/quests.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values
        model.with("headerData", data.get("headerData"));
        model.with("data", data);
        model.with("title", title);

        return template.render(model);
    }

    private Map<String,Object> getQuestDataMap() {
        Map<String, Object> data = new HashMap<>();

        data.put("headerData", getHeaderData());
        data.put("items", getQuestItems());

        return data;
    }

    private Map<String,Object> getShopDataMap() {
        Map<String, Object> data = new HashMap<>();

        data.put("headerData", getHeaderData());
        data.put("items", getShopItems("student", 1000));

        return data;
    }

    private List<Map<String, String>> getShopItems(String role, int wallet) {
        String fileName;
        List<Map<String, String>> items = new ArrayList<>();

        Map<String, String> item = new HashMap<>();

        if (role.equals("student")) {
            if ( 2 < wallet) {
                fileName = "town-hover.png";
            } else {
                fileName = "town.png";
            }
        } else {
            fileName = "town.png";
        }
        items.add(getItem("1", "Tipping the fanfare-guy", "2", fileName, "personal", "The student can use jukebox exclusively for half day"));

        if (role.equals("student")) {
            if ( 3 < wallet) {
                fileName = "elemental-hover.png";
            } else {
                fileName = "elemental.png";
            }
        } else {
            fileName = "elemental.png";
        }
        items.add(getItem("2","Summon code elemental", "3", fileName, "personal", "Mentor joins a student team for one hour (all team members must buy this item)"));

        if (role.equals("student")) {
            if ( 300 < wallet) {
                fileName = "portal-hover.png";
            } else {
                fileName = "portal.png";
            }
        } else {
            fileName = "portal.png";
        }
        items.add(getItem("3","Teleport", "300", fileName, "group", "The whole room goes to an off-school program instead for a specified day (which is at least 2 weeks ahead)"));

        if (role.equals("student")) {
            if ( 20 < wallet) {
                fileName = "portal-hover.png";
            } else {
                fileName = "portal.png";
            }
        } else {
            fileName = "portal.png";
        }
        items.add(getItem("4","Tome of knowledge", "20", fileName, "group", "Extra material for the current topic (mentors will do it within 2 weeks)"));

        return items;
    }

    private List<Map<String, String>> getQuestItems() {
        List<Map<String, String>> items = new ArrayList<>();

        Map<String, String> item = new HashMap<>();

        items.add(getItem("1", "Entering the arena", "15", "arena-hover.png", "Special", "Do a prasentation meet-up"));

        items.add(getItem("2","Making arrowheads", "3", "arrow.png", "regular", "Create Kahoot question list with at least 10 questions in a Codecool related topic"));

        items.add(getItem("3","Fruitful opportunism", "3", "opportunism-hover.png", "regular", "Helping to facilitate a mentor organized workshop"));

        items.add(getItem("4","Visiting the wiseman", "3", "wiseman.png", "regular", "Going to company interview"));

        return items;
    }

    private Map<String,String> getItem(String id, String title, String cost, String fileName, String category, String desc) {
        Map<String, String> item = new HashMap<>();

        item.put("id", id);
        item.put("title", title);
        item.put("cost", cost);
        item.put("fileName", fileName);
        item.put("category", category);
        item.put("desc", desc);
        return item;
    }


    private Map<String, String> getHeaderData() {
        Map<String, String> headerData = new HashMap<>();

        headerData.put("hName", "Piotr");
        headerData.put("hSurname", "Kaminski");
        headerData.put("role", "mentor");
        headerData.put("rank", "pro");
        headerData.put("wallet", "1000");
        return headerData;
    }


    public static void main(String[] args) {
        RenderInteface rf = new TemplateRender();
        System.out.println(rf.RenderProfilePage(null, null, null));
    }
}
