package com.godel.simplewebapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Objects;

@ApiModel(value = "Employee ")
@Entity
@Table(name = "employee")
public class Employee {

    @ApiModelProperty(value = "Employee id", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @ApiModelProperty(value = "Employee first name", example = "Ivan")
    @NotNull(message = "First name must be not null")
    @Size(min = 1, message = "First name size must be greater or equal 1")
    private String firstName;

    @ApiModelProperty(value = "Employee last name", example = "Ivanov")
    @NotNull(message = "Last name must be not null")
    @Size(min = 1, message = "Last name size must be greater or equal 1")
    private String lastName;

    @ApiModelProperty(value = "Department id", example = "2")
    @NotNull(message = "Department id must be not null")
    @Min(1)
    private Integer departmentId;

    @ApiModelProperty(value = "Job title", example = "Some title")
    @NotNull(message = "Job title must be not null")
    @Size(min = 3, max = 30, message = "Job title size must be between 3 and 30")
    private String jobTitle;

    @ApiModelProperty(value = "Gender", example = "MALE")
    @NotNull(message = "Gender must be not null")
    @Enumerated
    private Gender gender;

    @ApiModelProperty(value = "Date of birth", example = "1994-12-03")
    @NotNull(message = "Date of birth must be not null")
    @Past(message = "Employee can't be born in the future")
    @Temporal(TemporalType.DATE)
    private Calendar dateOfBirth;

    public Employee() {
    }


    public Employee(@NotNull(message = "First name must be not null") @Size(min = 1, message = "First name size must be greater or equal 1") String firstName, @NotNull(message = "Last name must be not null") @Size(min = 1, message = "Last name size must be greater or equal 1") String lastName, @NotNull(message = "Department id must be not null") @Min(1) Integer departmentId, @NotNull(message = "Job title must be not null") @Size(min = 3, max = 30, message = "Job title size must be between 3 and 30") String jobTitle, @NotNull(message = "Gender must be not null") Gender gender, @NotNull(message = "Date of birth must be not null") @Past(message = "Employee can't be born in the future") Calendar dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", departmentId=" + departmentId +
                ", jobTitle='" + jobTitle + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(departmentId, employee.departmentId) &&
                Objects.equals(jobTitle, employee.jobTitle) &&
                gender == employee.gender &&
                Objects.equals(dateOfBirth, employee.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, departmentId, jobTitle, gender, dateOfBirth);
    }
}
