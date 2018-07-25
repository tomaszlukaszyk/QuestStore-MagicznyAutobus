package com.codecool.queststore.model.shop.artifact;

import com.codecool.queststore.model.shop.ShopItemAdapter;

import java.util.HashMap;
import java.util.Map;

public class ArtifactAdapter implements ShopItemAdapter {
    @Override
    public Map<String, String> getItemAsMap(Object item) {
        Map<String, String> artifactMap = new HashMap<>();

        try {
            Artifact artifact = (Artifact) item;

            artifactMap.put("id", String.valueOf(artifact.getARTIFACT_HISTORY_ID()));
            artifactMap.put("title", artifact.getNAME());
            artifactMap.put("description", artifact.getDESCRIPTION());
            artifactMap.put("category", artifact.getCATEGORY().getCATEGORY());
            artifactMap.put("cost", String.valueOf(artifact.getCOST()));
            artifactMap.put("imageName", artifact.getIS_USED() ? artifact.getIMAGE_MARKED_FILENAME() : artifact.getIMAGE_FILENAME());

            return artifactMap;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }
}
