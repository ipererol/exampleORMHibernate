package org.dam.controller;

import org.dam.dao.StudentDAO;
import org.dam.model.Student;
import org.dam.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentController implements StudentDAO {
    @Override
    public void addStudent(Student student) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            System.err.println(e);
        }
    }

    @Override
    public Student getStudentById(int studentId) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, studentId);
        } catch (Exception e){
            System.err.println(e);
            return null;
        }
    }

    @Override
    public void updateStudent(Student student) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(student);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            System.err.println(e);
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if(student != null) {
                session.remove(student);
            }
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            System.err.println(e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery("FROM Student", Student.class);
            return query.list();
        } catch (Exception e){
            System.err.println(e);
            return null;
        }
    }
}
