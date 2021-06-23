/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import static librarymanagementsystem.login.conn;
import static librarymanagementsystem.test.getSeqValue;

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
                  else{
                      JOptionPane.showMessageDialog(null, "invalid user name or password");
                  }
              }else{
                  JOptionPane.showMessageDialog(null, "invalid id");
              }
              
              
          }catch(Exception e){
              e.printStackTrace();
          
          }
          return false;
    }
    
    public boolean registerStudent(student s){
        PreparedStatement stmt;
         conn c = new conn();
        try{
            String sql = "insert into member (id, fname,lname, email, contact_address, address,fine) values(?,?,?,?,?,?,0)";
            stmt = c.c.prepareStatement(sql);
            stmt.setInt(1, s.id);
            stmt.setString(2,s.fname);
            stmt.setString(3,s.lname);
            stmt.setString(4,s.email);
            stmt.setString(5, s.contact);
            stmt.setString(6, s.address);
            
            stmt.execute();
            
            
            sql = "insert into mlogin (password, member_id) values (?,?)";
            stmt = c.c.prepareStatement(sql);
            stmt.setString(1, s.password);
            stmt.setInt(2, s.id);
            stmt.execute();
            
            
            
            sql = "insert into student (id, rnum, degree, semester) values(?,?,?,?)";
            stmt = c.c.prepareStatement(sql);
            stmt.setInt(1, s.id);
            stmt.setInt(2, s.regNUmber);
            stmt.setString(3, s.degree);
            stmt.setInt(4, s.semester);
            
            stmt.execute();
            return true;
            
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "invalid information");
        }
        
        return false;
    }
    
    public boolean RegisterFaculty(faculty f){
        PreparedStatement stmt;
        conn c = new conn();
        try{
            String sql = "insert into member (id, fname,lname, email, contact_address, address,fine) values(?,?,?,?,?,?,0 )";
            stmt = c.c.prepareStatement(sql);
            stmt.setInt(1,f.id);
            stmt.setString(2,f.fname);
            stmt.setString(3,f.lname);
            stmt.setString(4,f.email);
            stmt.setString(5,f.contact);
            stmt.setString(6,f.address);
            
            stmt.execute();
            
            sql = "insert into mlogin (password, member_id) values (?,?)";
            stmt = c.c.prepareStatement(sql);
            stmt.setString(1, f.password);
            stmt.setInt(2, f.id);
            stmt.execute();
            
            sql = "insert into faculty (id, fid_1, department) values (?, ?,?)";
            stmt = c.c.prepareStatement(sql);
            stmt.setInt(1,f.id );
            stmt.setInt(2, f.fid);
            stmt.setString(3, f.departement);
            stmt.execute();
            return true;
        
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "invalid id");
        
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
        
        PreparedStatement stmt;
        String sql = "";
        boolean flag = false;
        try{
            sql = "insert into book (isbn, title, language, quantity) VALUES(?,?,?,?)";
            stmt = new conn().c.prepareStatement(sql);
            stmt.setInt(1, Integer.valueOf(details[0]));
            stmt.setString(2, details[1]);
            stmt.setString(3, details[2]);
            stmt.setInt(4, Integer.valueOf(details[5]));
            stmt.executeUpdate();
            
            if(this.addAuthors(this.splitAuthors(details[3]), Integer.valueOf(details[0]), details[1]))
                flag = true;
            else
                flag = false;
            
            if(this.addbookCategory(this.splitCategories(details[6]), Integer.valueOf(details[0]), details[1]))
                flag = true;
            else
                flag = false;
            sql = "insert into publisher values(?,?)";
            stmt = new conn().c.prepareStatement(sql);
            stmt.setInt(1, Integer.valueOf(details[0]));
            stmt.setString(2,details[4]);
            stmt.executeUpdate();
            
                
            
            
            
            return flag;
        
        }catch(Exception e){
            e.printStackTrace();
        }
    
        return false;
    }
    
    private ArrayList<String>splitAuthors(String authorNames){
        
        return new ArrayList<>(Arrays.asList(authorNames.split(",")));
           
    }
    
    private ArrayList<String> splitCategories(String categories){
        return new ArrayList<>(Arrays.asList(categories.split(",")));
    }
    
    private boolean addbookCategory(ArrayList <String> cat, int isbn, String title){
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
        return true;
    
    }
    
    private boolean addAuthors(ArrayList<String> authors, int isbn, String title){
    
        conn c= new conn();
        boolean flag = true;
        boolean flag2 = true;
        boolean flag3 =true;
        boolean flag4 = false;
        int AId = 0;
        String sql = "";
        PreparedStatement stmt;
        PreparedStatement stmt2;
        int CId = 0;
        for(String name: authors){
            flag2 = true;
            while(flag2){
                try{
                    Statement s = c.c.createStatement();
                    ResultSet rs=s.executeQuery("select * from author");
                    while (rs.next()){
                        System.out.println(rs.getString(2));
                        if(rs.getString(2).equals(name)){
                            System.out.print(name);
                            CId = rs.getInt(1);
                            flag = false;
                            break;

                        }
                    }
                    if(flag){
                        AId = getSeqValue(c);
                        sql = String.format("insert into author (id,name) values(%d, '%s')",AId,name );

                        stmt2 = c.c.prepareStatement(sql);

                        stmt2.executeUpdate(sql);
                        flag3 = true;

                    }
                    else if(!flag){
                        sql = "insert into hasauthor (book_isbn, title, author_id) values (?,?,?)";
                        stmt = c.c.prepareStatement(sql);
                        stmt.setInt(1, isbn);
                        stmt.setString(2, title);
                        stmt.setInt(3, CId);
                        stmt.executeUpdate();
                        if(flag3){
                            flag2 = false;
                            
                        }


                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return true;
    
    }
    
    public boolean updateBook(String[] details){
        
        PreparedStatement stmt;
        String sql = "update book set title = ?, language = ?, quantity = ? where isbn = ?";
        boolean flag = false;
        try{
            
            stmt = new conn().c.prepareStatement(sql);
            stmt.setString(1, details[1]);
            stmt.setString(2, details[2]);
            stmt.setInt(3, Integer.valueOf(details[3]));
            stmt.setInt(4, Integer.valueOf(details[0]));
            stmt.executeUpdate();
            
            sql = "update publisher set name = ? where book_isbn = ?";
            stmt = new conn().c.prepareStatement(sql);
            stmt.setInt(2, Integer.valueOf(details[0]));
            stmt.setString(1,details[4]);
            stmt.executeUpdate();
            
            
            
            if(this.updateAuthors(Integer.valueOf(details[0]), details))
                flag = true;
            else
                flag = false;
            
            if(this.updateCategories(Integer.valueOf(details[0]), details))
                flag = true;
            else
                flag = false;
                
            
            
            
            return flag;
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean updateAuthors(int isbn, String [] details){
        
        try{
            PreparedStatement stmt = new conn().c.prepareStatement("delete from hasauthor where book_isbn = ?");
            stmt.setInt(1, isbn);
            stmt.executeUpdate();
            if(this.addAuthors(this.splitAuthors(details[6]), Integer.valueOf(details[0]), details[1]))
                 return true;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    
    }
    
    public boolean updateCategories(int isbn, String[] details){
        
        try{
            PreparedStatement stmt = new conn().c.prepareStatement("delete from hascategory where bookid = ?");
            stmt.setInt(1, isbn);
            stmt.executeUpdate();
              if(this.addbookCategory(this.splitCategories(details[5]), Integer.valueOf(details[0]), details[1]))
                 return true;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteBook(int isbn){
        conn c = new conn();
        if(checkIfBorrowed(isbn))
            JOptionPane.showMessageDialog(null, "cannot delete ...... book is borrowed");
        else{
            try{
                PreparedStatement stmt = c.c.prepareStatement("delete from hasauthor where book_isbn = ?");
                stmt.setInt(1, isbn);
                stmt.execute();
                
                stmt = c.c.prepareStatement("delete from hascategory where bookid = ?");
                stmt.setInt(1, isbn);
                stmt.execute();
                
                stmt = c.c.prepareStatement("delete from book where isbn = ?");
                stmt.setInt(1, isbn);
                stmt.execute();
                
                return true;
                
            }catch(Exception e){
                
            }
        }
            return false;
    }
    
    private boolean checkIfBorrowed(int isbn){
        try{
            PreparedStatement s = new conn().c.prepareStatement("select borrowed.book_isbn from borrowed, book where book.isbn = borrowed.book_isbn and book.isbn = ?");
            s.setInt(1, isbn);
            if(s.executeQuery().next())
                return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
     public boolean deleteAccount(int id){
         conn c = new conn();
        int type = 0;
        boolean flag = false;
        PreparedStatement stmt;
        try{
            String sql = "";
            stmt = c.c.prepareStatement("delete from mlogin where member_id = ?");
            stmt.setInt(1, id);
            stmt.execute();
            type = checkMemberType(id);
            if(type == 1){
                sql = "delete from student where id = ?";
                flag = true;
            }
            else if(type == 2){
                sql = "delete from faculty where id = ?";
                flag = true;
            }
            else if(type == 3){
                System.out.println("type data not found");
                flag = false;
            }
            
            if(flag){
                stmt = c.c.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.execute();
            }
            
            stmt = c.c.prepareStatement("delete from member where id = ?");
            stmt.setInt(1, id);
            stmt.execute();
            
            return true;
            
            
            
        
        }catch(Exception e){
            
            e.printStackTrace();
            System.out.println("cannot delete the account member must first return all the borrowed books");
        }
        return false;
    }
    
    private int checkMemberType(int id){
        
        try{
            PreparedStatement stmt = new conn().c.prepareStatement("select * from student where id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return 1;
            stmt = new conn().c.prepareStatement("select * from faculty where id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next())
                return 2;
            else return 3;
            
            
            
            
            
            
        
        }catch(Exception e){
            
            e.printStackTrace();
            return 3;
        }
        
       
    }
    
    public ResultSet viewAllMembers(){
        try{
            conn c = new conn();
            PreparedStatement s = c.c.prepareStatement("select id, fname, lname, email, contact_address, address from member");
            
            return s.executeQuery();
        }catch(Exception e){            

            
            
            e.printStackTrace();
        }
        return null;
    }
    
}
    
    

