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
public class Admin extends person {
    String role;
    public Admin(){
        
    }
    public Admin(int id, String fname, String lname, String email, String contact, String address){
        super(id, fname, lname, email, contact,address);
    }
    public void logout(){}
    
    public boolean login(int id, String userName, String password){
        PreparedStatement s;
        
          try{
              ResultSet rs;
              
              String sql = "select employee.id, employee.fname, emplogin.password from employee, emplogin,roles where employee.id = ? and emplogin.employee_id = ? and roles.employee_id =? and roles.role = 'admin'";
              s = conn.c.prepareStatement(sql);
              s.setInt(1,id);
              s.setInt(2, id);
              s.setInt(3, id);
              rs = s.executeQuery();
              if(rs.next()){
                  
                  
                  if( rs.getString(2).equals(userName) && rs.getString(3).equals(password)){
                      conn.c.close();
                      return true;
                      
                  }
              }
              
              
          }catch(Exception e){
              e.printStackTrace();
          
          }
          return false;
    }
    
    public boolean registerStudent(student s){
        PreparedStatement stmt;
         
        try{
            String sql = "insert into member (id, fname,lname, email, contact_address, address) values(?,?,?,?,?,? )";
            stmt = conn.c.prepareStatement(sql);
            stmt.setInt(1, s.id);
            stmt.setString(2,s.fname);
            stmt.setString(3,s.lname);
            stmt.setString(4,s.email);
            stmt.setString(5, s.contact);
            stmt.setString(6, s.address);
            
            stmt.execute();
            
            
            sql = "insert into mlogin (password, member_id) values (?,?)";
            stmt = conn.c.prepareStatement(sql);
            stmt.setString(1, s.password);
            stmt.setInt(2, s.id);
            stmt.execute();
            
            
            
            sql = "insert into student (id, rnum, degree, semester) values(?,?,?,?)";
            stmt = conn.c.prepareStatement(sql);
            stmt.setInt(1, s.id);
            stmt.setInt(2, s.regNUmber);
            stmt.setString(3, s.degree);
            stmt.setInt(4, s.semester);
            
            stmt.execute();
            return true;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean RegisterFaculty(faculty f){
        PreparedStatement stmt;
        try{
            String sql = "insert into member (id, fname,lname, email, contact_address, address) values(?,?,?,?,?,? )";
            stmt = conn.c.prepareStatement(sql);
            stmt.setInt(1,f.id);
            stmt.setString(2,f.fname);
            stmt.setString(3,f.lname);
            stmt.setString(4,f.email);
            stmt.setString(5,f.contact);
            stmt.setString(6,f.address);
            
            stmt.execute();
            
            sql = "insert into mlogin (password, member_id) values (?,?)";
            stmt = conn.c.prepareStatement(sql);
            stmt.setString(1, f.password);
            stmt.setInt(2, f.id);
            stmt.execute();
            
            sql = "insert into faculty (id, fid_1, department) values (?, ?,?)";
            stmt = conn.c.prepareStatement(sql);
            stmt.setInt(1,f.id );
            stmt.setInt(2, f.fid);
            stmt.setString(3, f.departement);
            stmt.execute();
        
        }catch(Exception e){
            e.printStackTrace();
        
        }
        
        return false;
    
    }
    
    public boolean  updateStudent(int id, String [] args){
        
        PreparedStatement s;
        try{
            conn c = new conn();
            String sql = "update member set fname = ?, lname =?, email = ?, contact_address = ?, address = ? where id = ?";
            s = c.c.prepareStatement(sql);
            s.setString(1, args[0]);
            s.setString(2, args[1]);
            s.setString(3, args[2]);
            s.setString(4, args[3]);
            s.setString(5, args[4]);
            s.setInt(6, id);
            
            
            s.executeUpdate();
            
            sql = "update student set degree = ?, semester = ? where id = ?";
            s= conn.c.prepareStatement(sql);
            s.setString(1, args[4]);
            s.setString(2, args[5]);
            s.setInt(3, id);
            s.execute();
            
            return true;
            
            
        
        }catch(Exception e){
            e.printStackTrace();
        
        }
        
    
        return false;
    }
    
    public boolean updateMember(int id , String args[]){
        
        PreparedStatement s;
        try{
            conn c = new conn();
            String sql = "update member set fname = ?, lname =?, email = ?, contact_address = ?, address = ? where id = ?";
            s = c.c.prepareStatement(sql);
            s.setString(1, args[0]);
            s.setString(2, args[1]);
            s.setString(3, args[2]);
            s.setString(4, args[3]);
            s.setString(5, args[4]);
            s.setInt(6, id);
            
            
            s.executeUpdate();
            
            sql = "update student set degree = ?, semester = ? where id = ?";
            s= conn.c.prepareStatement(sql);
            s.setString(1, args[4]);
            s.setString(2, args[5]);
            s.setInt(3, id);
            s.execute();
            
            return true;
            
            
        
        }catch(Exception e){
            e.printStackTrace();
        
        }
        
        
        return false;
    }
    
}
    
    

