package service;

import api.AdminResource;
//import api.HotelResource;
import model.Customer;

import java.util.Collection;

public class CustomerService {
    private final Collection<Customer> customers; // create empty customers list
    private static CustomerService instance;

    private CustomerService(AdminResource adminRes) {
        customers = adminRes.getAllCustomers();
    }

    public static CustomerService getInstance(AdminResource adminRes){
        instance = new CustomerService(adminRes);
        return instance;
    }

    public void addCustomer(String firstName, String lastName, String email) {
        Customer c;

        if (null != (c = getCustomer(email))) {
            System.out.println("Customer already exists: " + c);
            return;
        }

       try {
            c = new Customer(firstName, lastName, email);
            customers.add(c);
        } catch (Exception e) {
            System.out.println("Invalid Email");
            //return;
        }

        // System.out.println(c);
    } // end add customer

    public Customer getCustomer(String customerEmail){
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(customerEmail)) {
                return c;
            }
        }

        return null;
    } // end get customer

    public Collection<Customer> getAllCustomers(){
        return customers;
    }
}
