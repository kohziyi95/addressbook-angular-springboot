package com.vttp.addressbookserver.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Entity
public class Contact implements Serializable {
    @Id
    private String id;
    private String name;
    private String email;
    private String mobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public static Contact create(JsonObject jsonObject) {
        Contact c = new Contact();
        c.name = jsonObject.getString("name");
        c.email = jsonObject.getString("email");
        c.mobile = jsonObject.getString("mobile");
        try {
            c.id = jsonObject.getString("id");
        } catch (Exception ignored) {
        }
        return c;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("name", name)
                .add("email", email)
                .add("mobile", mobile)
                .build();
    }
}
