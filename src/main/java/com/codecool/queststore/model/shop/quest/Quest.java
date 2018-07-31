package com.codecool.queststore.model.shop.quest;

public class Quest extends QuestTemplate{

    private final boolean IS_DONE;
    private final int QUEST_HISTORY_ID;
    private final int STUDENT_ID;


    public Quest(String name, String description, int value, String imageName, String imageMarkedName, QuestCategory category, boolean isDone, int historyID, int studentID) {
        super(name, description, value, imageName, imageMarkedName, category);
        this.IS_DONE = isDone;
        this.QUEST_HISTORY_ID = historyID;
        this.STUDENT_ID = studentID;
    }


    public Quest(QuestTemplate questTemplate, boolean isDone, int historyID, int studentID) {
        super(questTemplate.getNAME(), questTemplate.getDESCRIPTION(), questTemplate.getValue(), questTemplate.getIMAGE_FILENAME(), questTemplate.getIMAGE_MARKED_FILENAME(), questTemplate.getCATEGORY());
        this.IS_DONE = isDone;
        this.QUEST_HISTORY_ID = historyID;
        this.STUDENT_ID = studentID;
    }


    public boolean IS_DONE() {
        return IS_DONE;
    }

    public int getQUEST_HISTORY_ID() {
        return QUEST_HISTORY_ID;
    }

    public int getSTUDENT_ID() {
        return STUDENT_ID;
    }
}

