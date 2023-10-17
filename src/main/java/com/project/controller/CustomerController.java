package com.project.controller;

import com.project.dao.CustomerDAO;
import com.project.entity.Customer;
import com.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // need to inject customer service
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model theModel) {

        // get customer list from the service
        List<Customer> customers = customerService.getCustomers();

        // add list to the SpringMVC model
        theModel.addAttribute("customers", customers);

        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Customer theCustomer = new Customer();
        theModel.addAttribute("customer", theCustomer);
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute(name = "customer") Customer customer) {

        // save the customer using service
        customerService.saveCustomer(customer);

        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam(name = "customerId") int customerId, Model theModel) {

        // get customer by id from database
        Customer customer = customerService.getCustomer(customerId);

        // set customer as model attribute to pre-populate form
        theModel.addAttribute("customer", customer);

        // send over to form
        return "customer-form";
    }

    @GetMapping(value = "/delete")
    public String deleteCustomer(@RequestParam(name = "customerId") int customerId) {

        // delete the customer
        customerService.deleteCustomer(customerId);

        return "redirect:/customer/list";
    }

    @PostMapping(value = "/search")
    public String searchCustomers(@RequestParam(name = "theSearchName") String searchName, Model theModel) {

        // search customers from the service
        List<Customer> customers = customerService.searchCustomers(searchName);

        theModel.addAttribute("customers", customers);

        return "list-customers";
    }
}
