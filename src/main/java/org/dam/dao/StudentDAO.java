package org.dam.dao;

import org.dam.model.Student;

import java.util.List;

public interface StudentDAO {
    void addStudent(Student student);

    Student getStudentById(int studentId);

    Student getStudentByName(String studentName);

    Student getStudentByCode(String code);

    void updateStudent(Student student);

    void deleteStudent(int studentId);

    List<Student> getAllStudents();

    List<Object[]> getStudentsByName(String name, String[] fields);

    Long getCountStudents();
}
