package com.example.demo.service;

import com.example.demo.model.Dog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.List;

@Repository
public class DogService {

    SessionFactory sessionFactory;

    DogService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    //@Lock(LockModeType.PESSIMISTIC_READ)
    public List<Dog> getAllDogs() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Dog", Dog.class);
        Dog dog = (Dog) query.getSingleResult();
        session.lock(dog, LockModeType.PESSIMISTIC_READ);
        session.getTransaction().commit();
        return query.getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Dog getDogById(int id) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Dog d WHERE d.id = :id", Dog.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
