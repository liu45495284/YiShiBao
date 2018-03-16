package com.xnliang.yishibao.module.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by JackLiu on 2018-03-16.
 */

public class CityBean implements IPickerViewData {
    private int id;
    private String name;
    private int level;
    private int parent_id;

    public CityBean(int id, String name, int level, int parent_id) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parent_id = parent_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
