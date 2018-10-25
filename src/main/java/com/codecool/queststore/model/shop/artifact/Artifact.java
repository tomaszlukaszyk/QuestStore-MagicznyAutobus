package com.codecool.queststore.model.shop.artifact;

import java.util.Objects;

public class Artifact {

    private final int ID;
    private final String NAME;
    private final String DESCRIPTION;
    private final int COST; // his COST while bought
    private final String IMAGE_FILENAME;
    private final String IMAGE_MARKED_FILENAME;
    private final Boolean IS_USED;
    private final ArtifactCategory CATEGORY;
    private final int ARTIFACT_HISTORY_ID;


    public Artifact(int id, int historyId, String name, String description, int cost, String imageName, String imageMarkedName, ArtifactCategory category, boolean isUsed) {
        this.ID = id;
        this.NAME = name;
        this.DESCRIPTION = description;
        this.COST = cost;
        this.IMAGE_FILENAME = imageName;
        this.IMAGE_MARKED_FILENAME = imageMarkedName;
        this.CATEGORY = category;
        this.IS_USED = isUsed;
        this.ARTIFACT_HISTORY_ID = historyId;
    }

    public Artifact(int id, int historyId, String name, String description, int cost, String imageName, String imageMarkedName, boolean isUsed) {
        this(id, historyId, name, description, cost, imageName, imageMarkedName, ArtifactCategory.PERSONAL, isUsed);
    }
    public int getID() {
        return ID;
    }
    public String getNAME() {
        return NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public int getCOST() {
        return COST;
    }

    public String getIMAGE_FILENAME() {
        return IMAGE_FILENAME;
    }

    public String getIMAGE_MARKED_FILENAME() {
        return IMAGE_MARKED_FILENAME;
    }

    public Boolean getIS_USED() {
        return IS_USED;
    }

    public ArtifactCategory getCATEGORY() {
        return CATEGORY;
    }

    public int getARTIFACT_HISTORY_ID() {
        return ARTIFACT_HISTORY_ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artifact artifact = (Artifact) o;
        return ID == artifact.ID &&
                COST == artifact.COST &&
                ARTIFACT_HISTORY_ID == artifact.ARTIFACT_HISTORY_ID &&
                Objects.equals(NAME, artifact.NAME) &&
                Objects.equals(DESCRIPTION, artifact.DESCRIPTION) &&
                Objects.equals(IMAGE_FILENAME, artifact.IMAGE_FILENAME) &&
                Objects.equals(IMAGE_MARKED_FILENAME, artifact.IMAGE_MARKED_FILENAME) &&
                Objects.equals(IS_USED, artifact.IS_USED) &&
                CATEGORY == artifact.CATEGORY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, NAME, DESCRIPTION, COST, IMAGE_FILENAME, IMAGE_MARKED_FILENAME, IS_USED, CATEGORY, ARTIFACT_HISTORY_ID);
    }
}
