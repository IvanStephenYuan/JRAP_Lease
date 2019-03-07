package com.jingrui.jrap.activiti.dto;

import org.activiti.engine.identity.Picture;
import org.activiti.engine.impl.persistence.entity.ByteArrayRef;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;

/**
 * @author shengyang.zhou@jingrui.com
 */

public class ActivitiUser extends UserEntityImpl {

    private String id;
    private String firstName;
    private String lastName;

    private String email;

    @Override
    public Picture getPicture() {
        return null;
    }

    @Override
    public void setPicture(Picture picture) {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isInserted() {
        return false;
    }

    @Override
    public void setInserted(boolean inserted) {

    }

    @Override
    public boolean isUpdated() {
        return false;
    }

    @Override
    public void setUpdated(boolean updated) {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public void setDeleted(boolean deleted) {

    }

    @Override
    public Object getPersistentState() {
        return null;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public boolean isPictureSet() {
        return false;
    }

    @Override
    public ByteArrayRef getPictureByteArrayRef() {
        return null;
    }

    @Override
    public void setRevision(int revision) {

    }

    @Override
    public int getRevision() {
        return 0;
    }

    @Override
    public int getRevisionNext() {
        return 0;
    }
}
