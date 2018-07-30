package com.codecool.queststore.view;

import org.jtwig.JtwigModel;

public interface RenderInteface {
    String RenderClassPage();
    String RenderProfilePage(JtwigModel model);
    String RenderListPage();
    String RenderShopPage();
    String RenderQuestPage();
}
