/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static librarymanagementsystem.login.conn;

/**
 *
 * @author HP
 */
public class student extends Member{
    public int regNUmber;
    public String degree;
    public int semester;
    
    public student(){}
    
    
    public student(int id, String fname, String lname, String email, String contact, String address){
        super(id, fname, lname, email, contact,address);
        
       
    }
    public student(int id, String fname, String lname, String email, String contact, String address, String password){
        super(id, fname, lname, email, contact,address, password);
        
       
    }
    public student(int id,int regNumber,int sem, String degree, String fname, String lname, String email, String contact, String address, String password){
        super(id, fname, lname, email, contact,address, password);
        this.regNUmber = regNumber;
        this.semester = sem;
        this.degree = degree;
    }
    
    public void setId(int id){
        this.id = id;
        
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    
    
    
}
