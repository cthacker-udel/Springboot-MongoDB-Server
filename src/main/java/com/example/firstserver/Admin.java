package com.example.firstserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "ADMIN")
public class Admin {

    @Id
    private String adminId;

    @Size(min=1,max=50)
    @Field("Name")
    @NotNull
    private String Name;

    @Size(min=1,max=20)
    @Field("Clearance")
    @NotNull
    private String Clearance;

    @Size(min=1,max=30)
    @Field("Role")
    @NotNull
    private String Role;

    // MM/DD/YYYY
    @Size(min=9,max=9)
    @Field("DOB")
    @NotNull
    private String DOB;

    @Min(0)
    @Max(999999)
    @Field("ADMINID")
    @NotNull
    private Integer ADMINID;

    public Admin(String Name, String Clearance, String Role, String DOB, Integer ADMINID){

        this.Name = Name;
        this.Clearance = Clearance;
        this.Role = Role;
        this.DOB = DOB;
        this.ADMINID = ADMINID;

    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getClearance() {
        return Clearance;
    }

    public void setClearance(String clearance) {
        Clearance = clearance;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public Integer getADMINID() {
        return ADMINID;
    }

    public void setADMINID(Integer ADMINID) {
        this.ADMINID = ADMINID;
    }

    public org.bson.Document pojoToDoc(){

        org.bson.Document newDoc = new org.bson.Document();
        newDoc.append("Name",this.Name);
        newDoc.append("Clearance",this.Clearance);
        newDoc.append("ADMINID",this.ADMINID);
        newDoc.append("Role",this.Role);
        newDoc.append("DOB",this.DOB);
        return newDoc;

    }

    public String toString(){

        return String.format("-=-=-=-=-=ADMIN ID %s-=-=-=-=-=\nName : %s\nClearance : %s\nADMINID : %d\nRole : %s\nDOB : %s\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n",this.adminId,this.Name,this.Clearance,this.ADMINID,this.Role,this.DOB);

    }

}
