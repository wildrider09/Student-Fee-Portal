package com.example.studentfeepayment.dao;

import com.example.studentfeepayment.bean.Bills;
import com.example.studentfeepayment.bean.Students;
import com.example.studentfeepayment.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class StudentOperationsDAOImpl implements DAO {

    @Override
    public Session createSession() {
        return SessionUtil.getSessionFactory().openSession();
    }

    @Override
    public void terminateSession(Session session) {
        session.close();
    }

    public boolean registerStudent(Students student) {
        Session session = SessionUtil.getSessionFactory().openSession();
        Transaction transaction;
        try {
            transaction = session.beginTransaction();
            List<Bills> bills = student.getBills();
            session.save(student);
            for (Bills bill : bills) {
                session.save(bill);
            }
            transaction.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            terminateSession(session);
            return false;
        } finally {
            terminateSession(session);
        }
        return true;
    }

    public Students validateAndRetrieveStudent(final Students student, boolean requirePassword) {
        Session session = SessionUtil.getSessionFactory().openSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Students> criteriaQuery = criteriaBuilder.createQuery(Students.class);
            Root<Students> studentsRoot = criteriaQuery.from(Students.class);
            criteriaQuery.select(studentsRoot);
            Predicate userName = criteriaBuilder.like(studentsRoot.get("userName"), student.getUserName());
            Predicate password = criteriaBuilder.like(studentsRoot.get("password"), student.getPassword());

            if (requirePassword)
                criteriaQuery.where(criteriaBuilder.and(userName, password));
            else
                criteriaQuery.where(userName);

            Query<Students> query = session.createQuery(criteriaQuery);
            List<Students> students = query.getResultList();

            Students response = students.isEmpty() ? null : students.get(0).shallowCopy();
            terminateSession(session);
            return response;

        } catch (Exception ex) {
            terminateSession(session);
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Integer getMaxId() {
        Session session = SessionUtil.getSessionFactory().openSession();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Students> studentsRoot = criteriaQuery.from(Students.class);
            criteriaQuery.select(criteriaBuilder.count(studentsRoot.get("studentId")));

            Query<Long> query = session.createQuery(criteriaQuery);
            List<Long> ids = query.getResultList();

            terminateSession(session);
            return ids == null || ids.isEmpty() || ids.get(0) == null ? 0 : ids.get(0).intValue();

        } catch (Exception ex) {
            terminateSession(session);
            System.out.println(ex.getMessage());
            return 0;
        }
    }
}
