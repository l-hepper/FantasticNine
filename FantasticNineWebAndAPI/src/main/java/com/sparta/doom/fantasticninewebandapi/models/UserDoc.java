package com.sparta.doom.fantasticninewebandapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Document(collection = "users")
public class UserDoc {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String name;
    private String password;

    @Field("permissions")
    private Set<String> permissions;

    public UserDoc(){

    }

    public UserDoc(String email, String name, String password, Set<String> permissions) {

        this.email = email;
        this.name = name;
        this.password = password;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
