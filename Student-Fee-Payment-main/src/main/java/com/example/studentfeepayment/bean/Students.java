package com.example.studentfeepayment.bean;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Students")
public class Students implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;              //Primary Key
    @Column(nullable = false, unique = true)
    private String rollNumber;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    private String photographPath;
    @Column(nullable = false)
    private double cgpa;
    @Column(nullable = false)
    private Integer totalCredits;
    private Integer graduationYear;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "StudentBills", joinColumns = {@JoinColumn(name = "studentId")},
            inverseJoinColumns = {@JoinColumn(name = "billId")})
    private List<Bills> bills;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentPayment> studentPaymentList;

    /**
     * Foreign Keys
     * <p>
     * private String domain;
     * private String specialization;
     * private Integer placementId;
     */

    public Students() {
    }

    public Students(Integer studentId, String rollNumber, String firstName,
                    String lastName, String userName, String password,
                    String email, String photographPath, double cgpa, Integer totalCredits,
                    Integer graduationYear, List<Bills> bills) {
        this.studentId = studentId;
        this.rollNumber = rollNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.photographPath = photographPath;
        this.cgpa = cgpa;
        this.totalCredits = totalCredits;
        this.graduationYear = graduationYear;
        this.bills = bills;
        this.studentPaymentList = new ArrayList<>();
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotographPath() {
        return photographPath;
    }

    public void setPhotographPath(String photographPath) {
        this.photographPath = photographPath;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    @JsonbTransient
    public List<Bills> getBills() {
        return bills;
    }

    public void setBills(List<Bills> bills) {
        this.bills = new ArrayList<>(bills);
    }

    @JsonbTransient
    public List<StudentPayment> getStudentPaymentList() {
        return studentPaymentList;
    }

    public void setStudentPaymentList(List<StudentPayment> studentPaymentList) {
        this.studentPaymentList = studentPaymentList;
    }

    @Override
    public String toString() {
        return "Students{" +
                "studentId=" + studentId +
                ", rollNumber=" + rollNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", photographPath='" + photographPath + '\'' +
                ", cgpa=" + cgpa +
                ", totalCredits=" + totalCredits +
                ", graduationYear=" + graduationYear +
                ", bills=" + printBills(bills) +
                '}';
    }

    private String printBills(List<Bills> bills) {
        if (bills == null || bills.isEmpty()) return "no Bills";
        StringBuilder billsToString = new StringBuilder();
        for (Bills bill : bills) {
            billsToString.append(bill.toString()).append("\n");
        }

        return billsToString.toString();
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Students shallowCopy() throws CloneNotSupportedException {
        Students clonedStudent = (Students) this.clone();
        clonedStudent.bills = new ArrayList<>();
        clonedStudent.studentPaymentList = new ArrayList<>();

        for (Bills bill : this.bills) {
            clonedStudent.bills.add(bill.shallowCopy());
        }
        for (StudentPayment billPayment : this.studentPaymentList) {
            clonedStudent.studentPaymentList.add(billPayment.shallowCopy());
        }
        return clonedStudent;
    }
}
