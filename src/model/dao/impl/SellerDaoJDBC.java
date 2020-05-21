package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        
        Seller seller = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        
        try {
            String sql = 
                "SELECT seller.* " +
                "      ,department.Name AS DepartmentName " +
                "  FROM seller " +
                " INNER JOIN department " +
                "    ON department.Id = seller.DepartmentId " +
                " WHERE seller.Id = ?";                    
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();
            
            if (result.next()) {
                Department department = instantiateDepartment(result);                         
                seller = instantiateSeller(result, department);            
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultSet(result);
        }
        
        return seller;
    }

    private Department instantiateDepartment(ResultSet result) throws SQLException {
        Department department = new Department();
        department.setId(result.getInt("DepartmentId"));
        department.setName(result.getString("DepartmentName"));
        return department;
    }
    
    private Seller instantiateSeller(ResultSet result, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(result.getInt("Id"));
        seller.setName(result.getString("Name"));
        seller.setEmail(result.getString("Email"));
        seller.setBaseSalary(result.getDouble("BaseSalary"));
        seller.setBirthDate(result.getDate("BirthDate"));
        seller.setDepartment(department);            
        return seller;
    }    

    @Override
    public List<Seller> findAll() {

        List<Seller> sellers = new ArrayList<>();        
        PreparedStatement statement = null;
        ResultSet result = null;
        
        try {
            String sql = 
                "SELECT seller.*\r\n" + 
                "      ,department.Name AS DepartmentName\r\n" + 
                "  FROM seller\r\n" + 
                " INNER JOIN department\r\n" + 
                "    ON department.Id = seller.DepartmentId\r\n" +  
                " ORDER BY seller.Name";                   
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();
                        
            if  (result.next()) {
                Department department = null;        
                Seller seller = null;                
                Map<Integer, Department> map = new HashMap<>();
                
                do {
                    department = map.get(result.getInt("DepartmentId"));
                    if (department == null) {
                        department = instantiateDepartment(result);
                        map.put(department.getId(), department);                        
                    }
                    
                    seller = instantiateSeller(result, department);
                    sellers.add(seller);                    
                } while (result.next());
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultSet(result);
        }
        
        return sellers;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        
        List<Seller> sellers = new ArrayList<>();        
        Seller seller = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        
        try {
            String sql = 
                "SELECT seller.*\r\n" + 
                "      ,department.Name AS DepartmentName\r\n" + 
                "  FROM seller\r\n" + 
                " INNER JOIN department\r\n" + 
                "    ON department.Id = seller.DepartmentId\r\n" + 
                " WHERE DepartmentId = ?\r\n" + 
                " ORDER BY seller.Name";                   
            statement = connection.prepareStatement(sql);
            statement.setInt(1, department.getId());
            result = statement.executeQuery();
                        
            if  (result.next()) {
                department = instantiateDepartment(result);
                do {
                    seller = instantiateSeller(result, department);
                    sellers.add(seller);                    
                } while (result.next());
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultSet(result);
        }
        
        return sellers;
    }

}
