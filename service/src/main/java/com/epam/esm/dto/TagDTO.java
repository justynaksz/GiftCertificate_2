package com.epam.esm.dto;

import org.springframework.stereotype.Component;

/**
 * DTO class fo Tag.
 */
@Component
public class TagDTO {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
