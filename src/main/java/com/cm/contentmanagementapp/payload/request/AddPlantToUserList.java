package com.cm.contentmanagementapp.payload.request;

import javax.validation.constraints.NotNull;

public class AddPlantToUserList {

    @NotNull
    private Long userId;

    @NotNull
    private Long plantId;

    @NotNull
    private String listType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }
}
