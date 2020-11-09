//package com.godel.simplewebapp.dao.impl;
//
//import com.godel.simplewebapp.dao.EmployeeDao;
//import com.godel.simplewebapp.dto.Employee;
//import com.godel.simplewebapp.dto.Gender;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.core.namedparam.SqlParameterSource;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Objects;
//
//@Repository
//public class EmployeeDaoImpl implements EmployeeDao {
//
//    @Value("${employee.select}")
//    private String SELECT_ALL;
//
//    @Value("${employee.selectById}")
//    private String SELECT_BY_ID;
//
//    @Value("${employee.update}")
//    private String UPDATE;
//
//    @Value("${employee.insert}")
//    private String INSERT;
//
//    @Value("${employee.delete}")
//    private String DELETE;
//
//    private static final Logger LOGGER = LogManager.getLogger(EmployeeDaoImpl.class);
//
//    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    @Autowired
//    public EmployeeDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
//    }
//
//    @Override
//    public List<Employee> getAll() {
//        LOGGER.debug("Get all employees");
//        return namedParameterJdbcTemplate
//                .query(SELECT_ALL, new EmployeeRowMapper());
//    }
//
//    @Override
//    public Employee getById(Integer employeeId) {
//        LOGGER.debug("Get employee with id = {}", employeeId);
//        SqlParameterSource params = new MapSqlParameterSource().addValue("employeeId", employeeId);
//        return namedParameterJdbcTemplate
//                .queryForObject(SELECT_BY_ID, params, new EmployeeRowMapper());
//    }
//
//    @Override
//    public void deleteById(Integer employeeId) {
//        LOGGER.debug("Delete employee with id {}", employeeId);
//        SqlParameterSource params = new MapSqlParameterSource().addValue("employeeId", employeeId);
//        namedParameterJdbcTemplate.update(DELETE, params);
//    }
//
//    @Override
//    public Employee add(Employee employee) {
//        LOGGER.debug("Add employee {}", employee);
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        params
//                .addValue("firstName", employee.getFirstName())
//                .addValue("lastName", employee.getLastName())
//                .addValue("departmentId", employee.getDepartmentId())
//                .addValue("jobTitle", employee.getJobTitle())
//                .addValue("gender", employee.getGender().toString())
//                .addValue("dateOfBirth", employee.getDateOfBirth());
//
//        namedParameterJdbcTemplate.update(INSERT, params, keyHolder,new String[] { "employee_id" });
//        employee.setEmployeeId(Objects.requireNonNull(keyHolder.getKey()).intValue());
//        return employee;
//    }
//
//    @Override
//    public Employee update(Employee employee) {
//        LOGGER.debug("Update employee with id {} {}", employee.getEmployeeId(), employee);
//        MapSqlParameterSource params = new MapSqlParameterSource();
//
//        params.addValue("employeeId", employee.getEmployeeId())
//                .addValue("firstName", employee.getFirstName())
//                .addValue("lastName", employee.getLastName())
//                .addValue("departmentId", employee.getDepartmentId())
//                .addValue("jobTitle", employee.getJobTitle())
//                .addValue("gender", employee.getGender().toString())
//                .addValue("dateOfBirth", employee.getDateOfBirth());
//
//        namedParameterJdbcTemplate.update(UPDATE, params);
//        return employee;
//    }
//    private static class EmployeeRowMapper implements RowMapper<Employee>{
//
//        @Override
//        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
//            Employee employee = new Employee();
//            employee.setEmployeeId(resultSet.getInt("employee_id"));
//            employee.setFirstName(resultSet.getString("first_name"));
//            employee.setLastName(resultSet.getString("last_name"));
//            employee.setDepartmentId(resultSet.getInt("department_id"));
//            employee.setJobTitle(resultSet.getString("job_title"));
//            employee.setGender(Gender.valueOf(resultSet.getString("gender")));
//            employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
//            return employee;
//        }
//    }
//}
