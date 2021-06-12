/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

/**
 *
 * @author HP
 */
public abstract class person {
    
    protected int id;
    protected String fname;
    protected String lname;
    protected String email;
    protected String contact;
    protected String address;
    protected String password;
    public person(){}
    
    public person(int id, String fname, String lname, String email, String contact, String address){
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact = contact;
        this.address = address;
        
    }
    
    public person(int id, String fname, String lname, String email, String contact, String address,String password){
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.password = password;
        
    }
     
    //public abstract void Login();
    public abstract void logout();

    
}
