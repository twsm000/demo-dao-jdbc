package application;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

    public static void main(String[] args) {

        DepartmentDao departmentDao = null;
        Department department = null;
        
        departmentDao = DaoFactory.createDepartmentDao();
        
        System.out.println(" ==== TEST 1: department findById ==== ");
        department = departmentDao.findById(1);
        System.out.println(department);
        
        DB.closeConnection();
    }
}
