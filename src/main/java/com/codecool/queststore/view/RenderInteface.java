package com.codecool.queststore.view;

import com.codecool.queststore.model.classes.CodecoolClass;
import com.codecool.queststore.model.shop.artifact.Artifact;
import com.codecool.queststore.model.user.User;
import org.jtwig.JtwigModel;

import java.util.List;

public interface RenderInteface {
    String RenderClassPage();

    // mentor profile
    String RenderProfilePage(User currentUser, User profile, List<CodecoolClass> classes);

    // student profile
    String RenderProfilePage(User currentUser, User profile, CodecoolClass ccClass, List<Artifact> items);

    String RenderListPage();

    String RenderShopPage();

    String RenderQuestPage();
}
