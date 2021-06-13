/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
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
    
   
    
    public ResultSet viewfines(){
        try{
            conn c = new conn();
            PreparedStatement s;
            String sql = "select member.id, member.fname, member.lname, member.fine from member";
            s = c.c.prepareStatement(sql);
            ResultSet rs = s.executeQuery();
            return rs;
        
        }catch(Exception e){
    
            e.printStackTrace();
        }
        return null;
    
    }
    
    public boolean ResolveFines(int amount, int id){
        int fine = 0;
        int total = 0;
        conn c = new conn();
        try{
            String sql = "select member.id, member.fname, member.lname, member.fine from member where id = ?";
            PreparedStatement s = c.c.prepareStatement(sql);
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            if(rs.next()){
                fine = rs.getInt(4);
                if (amount > fine){
                    JOptionPane.showMessageDialog(null, "please enter the correct amount, the ammount entered is greater than the amount of fine");
                    
                }
                else{
                    total = fine - amount;
                    sql = "update member set fine = ? where id = ?";
                    s = c.c.prepareStatement(sql);
                    s.setInt(1, total);
                    s.setInt(2, id);
                    s.executeUpdate();
                    JOptionPane.showMessageDialog(null, "updated");
                    
                }
                
            }
                
        
        }catch (Exception e){
            e.printStackTrace();
        }
        
        
    
        return false;
    }
    
    public boolean addBooks(String [] details){
        
        
    
        return false;
    }
    
    private ArrayList<String>splitAuthors(String authorNames){
        
        return new ArrayList<>(Arrays.asList(authorNames.split(",")));
           
    }
    
    private ArrayList<String> splitCategories(String categories){
        return new ArrayList<>(Arrays.asList(categories.split(",")));
    }
    
    private void addbookCategory(ArrayList <String> cat, int isbn, String title){
        int categoryId = 0;
        PreparedStatement stmt;
        conn c= new conn();
        String sql = "";
        for (String category: cat){
            switch(category){
                case "science":
                    try{
                    categoryId = 4;
                    sql = "insert into hascategory (bookid, booktitle, cid) values (?,?, ?)";
                    stmt = c.c.prepareStatement(sql);
                    stmt.setInt(1, isbn);
                    stmt.setString(2,title);
                    stmt.setInt(3, categoryId);
                    stmt.executeUpdate();
                    
                    
                    
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case "arts":
                    try{
                    categoryId = 1;
                    sql = "insert into hascategory (bookid, booktitle, cid) values (?,?, ?)";
                    stmt = c.c.prepareStatement(sql);
                    stmt.setInt(1, isbn);
                    stmt.setString(2,title);
                    stmt.setInt(3, categoryId);
                    stmt.executeUpdate();
                    
                    
                    
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    
            
            }
        
        }
    
    }
    
    private void addAuthors(){
    
        
    
    }
    
}
    
    

