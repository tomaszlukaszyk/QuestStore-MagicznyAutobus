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
            // collect data
                String hName = (String) data.get("userName");
                String hSurname = (String) data.get("userSurname");
                String role = (String) data.get("userRole");
                String rank = (String) data.get("rank");
                String wallet = (String) data.get("wallet");
                String title = "Class";
                List classes = (List) data.get("classes");
                List mentors = (List) data.get("mentors");
                List students = (List) data.get("students");

                // get a template file
                JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/class.html");

                // create a model that will be passed to a template
                JtwigModel model = JtwigModel.newModel();

                // fill the model with values
                model.with("hName", hName);
                model.with("hSurname", hSurname);
                model.with("role", role);
                model.with("rank", rank);
                model.with("wallet", wallet);
                model.with("title", title);
                model.with("classes", classes);
                model.with("mentors", mentors);
                model.with("students", students);

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
        data.put("userName", "Piotr");
        data.put("userSurname", "Kaminski");
        data.put("userRole", "mentor");
        data.put("rank", "pro");
        data.put("wallet", "1000");
        data.put("classes", classes);
        data.put("mentors", mentors);
        data.put("students", students);

        return data;
    }

    public static void main(String[] args) {
        RenderInteface rf = new TemplateRender();
        System.out.println(rf.RenderClassPage());
    }
}
