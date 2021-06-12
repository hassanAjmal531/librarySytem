/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        int isbn = 0;
        try{
            conn c  = new conn();
            String sql = "select book.isbn, book.title, book.language, book.quantity, publisher.name from book, publisher where  book.isbn = publisher.book_isbn and book.title = ?";
            PreparedStatement s = c.c.prepareStatement(sql);
            s.setString(1, title);
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
    
    public static ResultSet viewAllBook(){
        String author;
        String category;
        try{
            conn c  = new conn();
            String sql = "select book.isbn, book.title,book. language, book.quantity, publisher.name as publisherName from book, publisher where book.isbn = publisher.book_isbn";
            PreparedStatement s = c.c.prepareStatement(sql);
            
            ResultSet rs =s.executeQuery();
            return rs;
           
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String args[]){
    
        String [] arr =searchByName("harry potter v3");
        for (String i : arr)
            System.out.println(i);
    
    }
    
}
