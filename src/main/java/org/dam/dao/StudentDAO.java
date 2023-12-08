package org.dam.dao;

import org.dam.model.Student;

import java.util.List;

public interface StudentDAO {
    void addStudent(Student student);

    Student getStudentById(int studentId);

    void updateStudent(Student student);

    void deleteStudent(int studentId);

    List<Student> getAllStudents();
}
