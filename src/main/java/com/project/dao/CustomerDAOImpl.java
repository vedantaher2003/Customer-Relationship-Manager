package com.project.dao;

import com.project.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    // need to inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

    public CustomerDAOImpl() {
    }

    @Override
    public List<Customer> getCustomers() {

        // get current hibernate session
        Session session = sessionFactory.getCurrentSession();

        // create a query, and sort by last name
        Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);

        // execute the query and get result list
        List<Customer> customers = query.getResultList();

        // return the results
        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {

        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // save/update the customer to the database
        currentSession.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(int customerId) {

        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // read/retrieve customer by id from the database
        Customer theCustomer = currentSession.get(Customer.class, customerId);

        return theCustomer;
    }

    @Override
    public void deleteCustomer(int customerId) {

        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // delete the customer by primary key/id
        Query query = currentSession.createQuery("delete  from Customer c where c.id=:customerId");
        query.setParameter("customerId", customerId);
        query.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomers(String searchName) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Customer> query = null;

        // only search by name if searchName is not empty
        if (searchName != null && searchName.trim().length() > 0) {
            query = currentSession.createQuery("from Customer c where lower(c.firstName) like: searchName " +
                    "or lower(c.lastName) like: searchName", Customer.class);
            query.setParameter("searchName", "%" + searchName.toLowerCase() + "%");
        } else {
            // searchName is empty just get all customers
            query = currentSession.createQuery("from Customer", Customer.class);
        }
        List<Customer> customers = query.getResultList();
        return customers;
    }
}
