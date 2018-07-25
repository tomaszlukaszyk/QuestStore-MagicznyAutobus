package com.codecool.queststore.model.classes;

import com.codecool.queststore.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassAdapter {

    public static Map<String, Object>getClassAsMap(CodecoolClass ccClass) {
        Map<String, Object> classMap = new HashMap<>();

        classMap.put("name", ccClass.getNAME());
        classMap.put("mentors", getUserFullNameList(ccClass.getASSIGNED_MENTORS()));
        classMap.put("students", getUserFullNameList(ccClass.getASSIGNED_STUDENTS()));

        return classMap;
    }

    private static List<Map<String, String>> getUserFullNameList(List<User> users) {
        List<Map<String, String>> usersFullNameList = new ArrayList<>();

        for (User user: users) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(user.getID()));
            map.put("fullName", String.format("%s %s", user.getNAME(), user.getSURNAME()));
            usersFullNameList.add(map);
        }

        return usersFullNameList;
    }
}
