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
public class Member extends person {
    
    public Member(){}
    
    public Member(int id, String fname, String lname, String email, String contact, String address){
        super(id, fname, lname, email, contact,address);
    }
    
    public Member(int id, String fname, String lname, String email, String contact, String address, String password){
        super(id, fname, lname, email, contact,address, password);
    }
    public void borrow(){
    }
    public void checkhistory(){}
    public void logout(){}
    public boolean login(int id, String password){
        PreparedStatement s;
        
          try{
              ResultSet rs;
              
              String sql = "select * from mlogin where member_id = ? ";
              s = conn.c.prepareStatement(sql);
              s.setInt(1,id);
              rs = s.executeQuery();
              if(rs.next()){
                  
                  
                  if( rs.getString(1).equals(password)){
                      return true;
                      
                  }
              }
              
              
          }catch(Exception e){
              e.printStackTrace();
          
          }
          return false;
    }
    
}
