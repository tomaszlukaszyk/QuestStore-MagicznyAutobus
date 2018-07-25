package com.codecool.queststore.model.user;

import java.util.HashMap;
import java.util.Map;

public class UserAdapter {

    public static Map<String, String> getUserAsMap(User user) {
        HashMap<String, String> userMap = new HashMap<>();

        userMap.put("id", Integer.toString(user.getID()));
        userMap.put("name", user.getNAME());
        userMap.put("surname", user.getSURNAME());
        userMap.put("email", user.getEMAIL());
        userMap.put("address", user.getAddress());
        userMap.put("role", user.getROLE().getNAME());
        userMap.put("wallet", Integer.toString(user.getWallet()));
        userMap.put("userTitle", user.getTitle());

        return userMap;
    }
}
