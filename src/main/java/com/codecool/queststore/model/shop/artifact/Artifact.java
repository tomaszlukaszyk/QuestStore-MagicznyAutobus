package com.codecool.queststore.model.shop.artifact;

public class Artifact {

    private final String NAME;
    private final String DESCRIPTION;
    private final int COST; // his COST while bought
    private final String IMAGE_FILENAME;
    private final String IMAGE_MARKED_FILENAME;
    private final Boolean IS_USED;
    private final ArtifactCategory CATEGORY;
    private final int ARTIFACT_HISTORY_ID;


    public Artifact(int id,String name, String description, int cost, String imageName, String imageMarkedName, ArtifactCategory category, boolean isUsed) {
        this.NAME = name;
        this.DESCRIPTION = description;
        this.COST = cost;
        this.IMAGE_FILENAME = imageName;
        this.IMAGE_MARKED_FILENAME = imageMarkedName;
        this.CATEGORY = category;
        this.IS_USED = isUsed;
        this.ARTIFACT_HISTORY_ID = id;
    }

    public Artifact(int id,String name, String description, int cost, String imageName, String imageMarkedName, boolean isUsed) {
        this(id, name, description, cost, imageName, imageMarkedName, ArtifactCategory.PERSONAL, isUsed);
    }

}
