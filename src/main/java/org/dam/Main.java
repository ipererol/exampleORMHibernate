package org.dam;

import org.dam.controller.StudentController;
import org.dam.model.Student;
import org.dam.util.HibernateUtil;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Student student = new Student();
        student.setCode("bl01312");
        student.setName("Student");
        student.setPassword("pass");
        student.setEmail("false@false.com");
        StudentController studentController = new StudentController();
        studentController.addStudent(student);
        System.out.println("Numero de estudiantes: " +  studentController.getCountStudents());
        System.out.println("Estudiantes con nombre Student");
        String[] fields = {"name", "code"};
        for(Object[] st : studentController.getStudentsByName("Student", fields)){
            System.out.println(Arrays.toString(st));
        }
        System.out.println(studentController.getStudentById(student.getId()));
        student.setPassword("1234");
        studentController.updateStudent(student);
        System.out.println(studentController.getAllStudents());
        studentController.deleteStudent(student.getId());
        HibernateUtil.shutdown();
    }
}