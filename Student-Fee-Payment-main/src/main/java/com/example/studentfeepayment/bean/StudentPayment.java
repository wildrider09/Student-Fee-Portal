package com.example.studentfeepayment.bean;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "StudentPayment")
public class StudentPayment implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                                 //Primary Key
    private String description;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Students student;

    @ManyToOne
    @JoinColumn(name = "billId")
    private Bills bill;

    public StudentPayment(String description, Integer amount, LocalDateTime paymentDate) {
        this.description = description;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public StudentPayment() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate.getDayOfMonth() + "-" + paymentDate.getMonth() + "-" + paymentDate.getYear() +
                " " + paymentDate.getHour()+":"+ paymentDate.getMinute() ;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonbTransient
    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    @JsonbTransient
    public Bills getBill() {
        return bill;
    }

    public void setBill(Bills bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "StudentPayment{" +
                "id=" + id +
                ", description=" + description +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate.getDayOfMonth() + "-" + paymentDate.getMonth() + "-" + paymentDate.getYear() + " " + paymentDate.getHour()+":"+ paymentDate.getMinute() +
                ", student=" + student +
                ", bill=" + bill +
                '}';
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public StudentPayment shallowCopy() throws CloneNotSupportedException {
        StudentPayment clonedStudentPayment = (StudentPayment) this.clone();
        clonedStudentPayment.setStudent(null);
        clonedStudentPayment.setBill(null);

        return clonedStudentPayment;
    }
}
