package com.codecool.queststore.view;

import org.jtwig.JtwigModel;

import java.util.List;
import java.util.Map;

public interface TemplateModelInterface {
    JtwigModel getClassModel(Map<String, String> user, Map<String, Object> ccClass);
    JtwigModel getProfileStudentModel(Map<String, String> logedUser, Map<String, String> user, String ccClass, Map<String, String> items);
    JtwigModel getProfileMentorModel(Map<String, String> logedUser, Map<String, String> user, List<Map<String, Object>> classes);
    JtwigModel getUserListModel(Map<String, String> logedUser, List<Map<String, String>> users);
    JtwigModel getQuestModel(Map<String, String> logedUser, List<Map<String, String>> quests);
    JtwigModel getArtifactModel(Map<String, String> logedUser, List<Map<String, String>> artifacts);
}
