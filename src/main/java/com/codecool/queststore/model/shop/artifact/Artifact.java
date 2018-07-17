package com.codecool.queststore.model.shop.artifact;

public class Artifact {

    private final String NAME;
    private final String DESCRIPTION;
    private final int cost; // his cost while bought
    private final String imageName;
    private final String imageMarkedName;
    private ArtifactCategory category;


    public Artifact(String name, String description, int cost, String imageName, String imageMarkedName, ArtifactCategory category) {
        NAME = name;
        DESCRIPTION = description;
        this.cost = cost;
        this.imageName = imageName;
        this.imageMarkedName = imageMarkedName;
        this.category = category;
    }

    public Artifact(String name, String description, int cost, String imageName, String imageMarkedName) {
        this(name, description, cost, imageName, imageMarkedName, ArtifactCategory.PERSONAL);
    }

}
