package com.example.firstserver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.text.SimpleDateFormat;
import java.util.*;

@Document(collection = "Employees")
public class Employee {

    @Id private String employeeId;

    @Field("Name")
    private String Name;

    @Field("Position")
    private String Position;

    @Field("DOB[Y]")
    private int dobYear;

    @Field("DOB[M]")
    private int dobMonth;

    @Field("DOB[D]")
    private int dobDay;

    @Field("ID")
    private Integer ID;


    private Calendar dob = Calendar.getInstance();
    //newCalendar.set(2021,7,23);
    private Date theDate;// = newCalendar.getTime();
    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @Field("DOB")
    private String DOB;// = format.format(theDate);

    public Employee(String Name, String Position, String DOB, Integer ID){

        this.Name = Name;
        this.Position = Position;
        this.DOB = DOB;
        this.ID = ID;

    }

    /*
    public Employee(String Name,String Position,int newMonth,int newDay,int newYear){

        this.name = Name;
        this.position = Position;
        this.dobYear = newYear;
        this.dobMonth = newMonth;
        this.dobDay = newDay;
        this.dob.set(dobYear,dobMonth,dobDay);
        this.theDate = dob.getTime();
        this.dobStr = this.format.format(theDate);

    }
    */

    public Integer getID() {
        return ID;
    }

    public void setId(Integer ID) {
        this.ID = ID;
    }

    public void setName(String Name){

        this.Name = Name;

    }

    public void setPosition(String Position){

        this.Position = Position;

    }

    public void setDOBYear(int newDOBYear){

        this.dobYear = newDOBYear;

    }

    public void setDOBMonth(int newDOBMonth){

        this.dobMonth = newDOBMonth;

    }

    public void setDOBDay(int newDOBDay){

        this.dobDay = newDOBDay;

    }

    public String getName(){

        return this.Name;

    }

    public String getPosition(){

        return this.Position;

    }

    public String getDOB(){

        return this.DOB;

    }

    public void setDob(String DOB){

        this.DOB = DOB;

    }

    public org.bson.Document pojoToDoc(){

        org.bson.Document doc = new org.bson.Document();

        doc.put("Name",this.getName());
        doc.put("ID",this.getID());
        doc.put("Position",this.getPosition());
        doc.put("DOB",this.getDOB());

        return doc;

    }

    public String toString(){

        return String.format("\n-=-=-=Employee %s-=-=-=\nName : %s\nPosition : %s\nDOB : %s\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=",this.employeeId,this.Name,this.Position,this.DOB);

    }



}
