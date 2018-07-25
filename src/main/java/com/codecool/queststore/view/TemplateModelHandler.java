package com.codecool.queststore.view;

import org.jtwig.JtwigModel;

import java.util.List;
import java.util.Map;

public class TemplateModelHandler implements TemplateModelInterface {
    @Override
    public JtwigModel getClassModel(Map<String, String> user, Map<String, Object> ccClass) {
        return null;
    }

    @Override
    public JtwigModel getProfileStudentModel(Map<String, String> logedUser, Map<String, String> user, String ccClass, Map<String, String> items) {
        return null;
    }

    @Override
    public JtwigModel getProfileMentorModel(Map<String, String> logedUser, Map<String, String> user, List<Map<String, Object>> classes) {
        return null;
    }

    @Override
    public JtwigModel getUserListModel(Map<String, String> logedUser, List<Map<String, String>> users) {
        return null;
    }

    @Override
    public JtwigModel getQuestModel(Map<String, String> logedUser, List<Map<String, String>> quests) {
        return null;
    }

    @Override
    public JtwigModel getArtifactModel(Map<String, String> logedUser, List<Map<String, String>> artifacts) {
        return null;
    }
}
