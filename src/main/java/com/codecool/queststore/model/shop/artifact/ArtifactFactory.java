package com.codecool.queststore.model.shop.artifact;

import java.util.List;

public class ArtifactFactory {

    public List<Artifact> listFromUserID(int id) {
        //todo: dao pullin all artifacts
        return null;
    }
    public Artifact fromData(int id,String name, String description, int cost, String imageName, String imageMarkedName, ArtifactCategory category, boolean isUsed) {
        return new Artifact(id, name, description, cost, imageName, imageMarkedName, category, isUsed);
    }

    public Artifact fromData(int id,String name, String description, int cost, String imageName, String imageMarkedName, ArtifactCategory category) {
        return new Artifact(id, name, description, cost, imageName, imageMarkedName, category, false);
    }

}
