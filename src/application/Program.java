package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
   
   public static void main(String[] args) {

       SellerDao sellerDao = null;
       Seller seller = null;
       List<Seller> sellers = null;
       
       sellerDao = DaoFactory.createSellerDao();
       
       System.out.println(" ==== TEST 1: seller findById ==== ");
       seller = sellerDao.findById(3);
       System.out.println(seller);
       
       System.out.println("--------------------------------------------------------------");
       
       System.out.println(" ==== TEST 2: seller findByDepartment ==== ");
       sellers = sellerDao.findByDepartment(new Department(1, null));
       sellers.forEach(System.out::println);
       
       System.out.println("--------------------------------------------------------------");
       
       System.out.println(" ==== TEST 3: seller findAll ==== ");
       sellers = sellerDao.findAll();
       sellers.forEach(System.out::println);
       
       System.out.println("--------------------------------------------------------------");
       
       System.out.println(" ==== TEST 4: seller insert ==== ");
       seller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, new Department(1, null)); 
       sellerDao.insert(seller);
       System.out.println(seller);         
   } 
}
