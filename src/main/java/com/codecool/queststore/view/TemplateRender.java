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

    public String RenderProfilePage() {
        Map<String, Object> data = getProfileDataMap();
        String title = "Class";

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
        Map<String, String> headerData = new HashMap<>();

        headerData.put("hName", "Piotr");
        headerData.put("hSurname", "Kaminski");
        headerData.put("role", "student");
        headerData.put("rank", "pro");
        headerData.put("wallet", "1000");

        data.put("headerData", headerData);
        data.put("classes", classes);
        data.put("mentors", mentors);
        data.put("students", students);

        return data;
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
        Map<String, String> headerData = new HashMap<>();

        headerData.put("hName", "Piotr");
        headerData.put("hSurname", "Kaminski");
        headerData.put("role", "mentor");
        headerData.put("rank", "pro");
        headerData.put("wallet", "1000");

        data.put("headerData", headerData);
        data.put("email", "pkaminski@o2.pl");
        data.put("address", "Slusarska 9");
        data.put("class", "2018.1");
        data.put("classes", classes);
//        data.put("items", items);
        data.put("students", students);
//        data.put("students", students);

        return data;
    }


    public static void main(String[] args) {
        RenderInteface rf = new TemplateRender();
        System.out.println(rf.RenderProfilePage());
    }
}
