package com.codecool.queststore.view;

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

    public String RenderProfilePage() {
        /* headerData:
            type: Map<String, String>
            key: hName, hSurname, role, wallet
           data:
            type: Map<String, Object>
            key,valueType: email, String; adress, String; classes, List<Map>; items/students, List<Map>
           title:
            type: String
        * */
        Map<String, Object> data = getProfileDataMap();
        String title = "Profile";

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/profile.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values
        model.with("headerData", data.get("headerData"));
        model.with("data", data);
        model.with("title", title);

        return template.render(model);
    }

    private Map<String,Object> getProfileDataMap() {
        // mock data
        List<String> classes = new ArrayList<>();
        List<String> students = new ArrayList<>();
//        List<String> items = new ArrayList<>();

        classes.add("2018.1");
        classes.add("2018.2");
        classes.add("2018.3");
//        for (int i= 0; i < 3; i++) {
//            items.add("item " + Integer.toString(i));
//        }

        students.add("Piotr Kaminski");
        students.add("Pawel Kaminski");
        students.add("Jakub Marmol");


        Map<String, Object> data = new HashMap<>();

        data.put("headerData", getHeaderData());
        data.put("email", "pkaminski@o2.pl");
        data.put("address", "Slusarska 9");
        data.put("class", "2018.1");
        data.put("classes", classes);
//        data.put("items", items);
        data.put("users", students);
//        data.put("students", students);

        return data;
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

        Map<String, Object> data = getProfileDataMap();
        String title = "students";

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/userList.html");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values
        model.with("headerData", data.get("headerData"));
        model.with("data", data);
        model.with("title", title + " List");
        model.with("listName", title);

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
}
