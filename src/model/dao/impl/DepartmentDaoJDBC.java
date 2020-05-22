package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {

    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet result = null;
        Department department = null;
        try {
            String sql = "SELECT * FROM department WHERE Id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();
            if (result.next()) {
                department = instantiateDepartment(result);                
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(result);
            DB.closeStatement(statement);
        }

        return department;
    }

    private Department instantiateDepartment(ResultSet result) throws SQLException {
        Department department = new Department();
        department.setId(result.getInt("Id"));
        department.setName(result.getString("Name"));
        return department;
    }

    @Override
    public List<Department> findAll() {
        return null;
    }

}
