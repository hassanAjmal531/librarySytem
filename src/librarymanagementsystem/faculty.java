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
public class faculty extends Member{
    public int fid;
    public String departement;
    
    public faculty(){}
    
    public faculty(int fid, int id,String department, String fname, String lname, String email, String contact, String address,String password){
         super(id, fname, lname, email, contact,address, password);
         this.fid = fid;
         this.departement = department;
    }
    
}
