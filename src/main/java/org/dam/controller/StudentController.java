package org.dam.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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

    @Override
    public List<Object[]> getStudentsByName(String name){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> cr = cb.createQuery(Object[].class);
            Root<Student> root = cr.from(Student.class);
            cr.multiselect(root.get("code"),root.get("name"))
                    .where(cb.like(root.get("name"), "%"+name+"%"))
                    .orderBy(cb.asc(root.get("name")));
            Query<Object[]> query = session.createQuery(cr);
            return query.getResultList();

        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public List getStudentsByNameWithHQL(String name){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query query = session.createQuery("SELECT code, name FROM Student WHERE name = :value");
            query.setParameter("value", name);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public Long getCountStudents(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cr = cb.createQuery(Long.class);
            Root<Student> root = cr.from(Student.class);
            cr.select(cb.count(root));

            Query<Long> query = session.createQuery(cr);
            return query.getSingleResult();

        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }
}
