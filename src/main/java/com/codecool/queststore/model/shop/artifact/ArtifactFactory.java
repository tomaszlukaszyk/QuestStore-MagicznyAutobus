package com.codecool.queststore.model.shop.artifact;

public class ArtifactFactory {


    public Artifact fromData(int id, int historyid, String name, String description, int cost, String imageName, String imageMarkedName, ArtifactCategory category, boolean isUsed) {
        return new Artifact(id, historyid, name, description, cost, imageName, imageMarkedName, category, isUsed);
    }

    public Artifact fromData(int id, int historyid, String name, String description, int cost, String imageName, String imageMarkedName, ArtifactCategory category) {
        return new Artifact(id, historyid, name, description, cost, imageName, imageMarkedName, category, false);
    }

}
