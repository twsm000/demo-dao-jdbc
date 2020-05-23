package application;

import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

    public static void main(String[] args) {

        DepartmentDao departmentDao = null;
        Department department = null;
        List<Department> departments = null;
        
        departmentDao = DaoFactory.createDepartmentDao();
        
        System.out.println(" ==== TEST 1: department findById ==== ");
        department = departmentDao.findById(1);
        System.out.println(department);
        
        System.out.println("--------------------------------------------------------------");
        
        System.out.println(" ==== TEST 2: department findAll ==== ");
        departments = departmentDao.findAll();
        departments.forEach(System.out::println);        
        
        DB.closeConnection();
    }
}
