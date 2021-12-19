package com.example.studentfeepayment.utils;

import com.example.studentfeepayment.service.StudentOperationsServiceImpl;

public class GetInstances {
    private static StudentOperationsServiceImpl studentOperationsServiceImpl;

    public static StudentOperationsServiceImpl getInstanceOfStudentOperationService() {
        if (studentOperationsServiceImpl != null) return studentOperationsServiceImpl;

        studentOperationsServiceImpl = new StudentOperationsServiceImpl();
        return studentOperationsServiceImpl;
    }
}
