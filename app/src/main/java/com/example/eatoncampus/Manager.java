package com.example.eatoncampus;
import java.io.Serializable;

public class Manager implements Serializable{
    public static String managerID;
    public static String managerFname;
    public static String managerLname;
    public static String manager_RID;
    public static String managerPassword;

    public Manager(String managerID, String managerFname, String managerLname, String manager_RID, String managerPassword) {
        this.managerID = managerID;
        this.managerFname = managerFname;
        this.managerLname = managerLname;
        this.manager_RID = manager_RID;
        this.managerPassword = managerPassword;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManager_RID(String id) {
        manager_RID = id;
    }

    public String getManager_RID() {
        return manager_RID;
    }
}
