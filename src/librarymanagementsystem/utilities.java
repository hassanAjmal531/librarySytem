/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class utilities {
    
    public static String[] searchByISBN(int isbn){
        String values[] = new String [7];
        try{
            conn c  = new conn();
            String sql = "select book.isbn, book.title, book.language, book.quantity, publisher.name from book, publisher where  book.isbn = publisher.book_isbn and book.isbn =?";
            PreparedStatement s = c.c.prepareStatement(sql);
            s.setInt(1, isbn);
            ResultSet rs =s.executeQuery();
            
            
            if(rs.next()){
                
                for(int i = 0; i < 5; i++){
                    values[i] = rs.getString(i+1);
                }
                sql = "select  listagg(author.name, ', ') as authors from author where author.id in (select hasauthor.author_id from hasauthor, book where book.isbn = hasauthor.book_isbn  and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,isbn);
                rs = s.executeQuery();
                
                if(rs.next())
                    values[5] = rs.getString(1);
                
                sql = "select listagg(category.cname,', ') from category where category.id in (select hascategory.cid from hascategory, book where book.isbn = hascategory.bookid and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,isbn);
                rs = s.executeQuery();
                
                if(rs.next())
                    values[6] = rs.getString(1);
                
            }else{
                System.out.println("dfg");
            }
            return values;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static String[] searchByName(String title){
    
        String values[] = new String [7];
        int isbn = getisbn(title);
        try{
            conn c  = new conn();
            String sql = "select book.isbn, book.title, book.language, book.quantity, publisher.name from book, publisher where  book.isbn = publisher.book_isbn and book.isbn = ?";
            PreparedStatement s = c.c.prepareStatement(sql);
            s.setInt(1, isbn);
            ResultSet rs =s.executeQuery();
            
            
            if(rs.next()){
                
                for(int i = 0; i < 5; i++){
                    values[i] = rs.getString(i+1);
                }
                isbn = Integer.valueOf(values[0]);
                sql = "select  listagg(author.name, ', ') as authors from author where author.id in (select hasauthor.author_id from hasauthor, book where book.isbn = hasauthor.book_isbn  and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,isbn);
                rs = s.executeQuery();
                
                if(rs.next())
                    values[5] = rs.getString(1);
                
                sql = "select listagg(category.cname,', ') from category where category.id in (select hascategory.cid from hascategory, book where book.isbn = hascategory.bookid and book.isbn = ?)";
                s = c.c.prepareStatement(sql);
                s.setInt(1,isbn);
                rs = s.executeQuery();
                
                if(rs.next())
                    values[6] = rs.getString(1);
                
            }else{
                System.out.println("dfg");
            }
            return values;
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static int getisbn(String title){
        try{
            PreparedStatement s= new conn().c.prepareStatement("select isbn, title from book");
            ResultSet rs = s.executeQuery();
            
            while(rs.next()){
                if(rs.getString(2).equals(title))
                    return rs.getInt(1);
            }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    
    }
    
    
    
    public static void main(String args[]){
    
        String [] arr =searchByName("harry potter v3");
        for (String i : arr)
            System.out.println(i);
    
    }
    
    public static String DateToString(Date date){
        
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
    
    public static Date toDate(String string){
        try{
        return new SimpleDateFormat("dd/MM/yyyy").parse(string); 
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static int getdifference(String d1, String d2){
        
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
	 String dateBeforeString = d1;
	 String dateAfterString = d2;
         Date dateBefore = null;

	 try {
	       dateBefore = myFormat.parse(dateBeforeString);
	       Date dateAfter = myFormat.parse(dateAfterString);
	       long difference = dateAfter.getTime() - dateBefore.getTime();
	       float daysBetween = (difference / (1000*60*60*24));
               int day = (int) daysBetween;
               
                
	      return day;
	} catch (Exception e) {
	       e.printStackTrace();
	 }
        
        return 0;
    }
    
    public static ResultSet displayBook(){
        
        try{
            PreparedStatement stmt = new conn().c.prepareStatement("select * from book");
            return stmt.executeQuery();
            
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
}
