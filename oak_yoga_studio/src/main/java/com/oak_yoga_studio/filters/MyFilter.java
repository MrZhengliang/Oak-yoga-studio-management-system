/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oak_yoga_studio.filters;

import com.oak_yoga_studio.domain.Customer;
import com.oak_yoga_studio.domain.User;
import com.oak_yoga_studio.service.ICustomerService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author weldu
 */
public class MyFilter implements Filter {

    private ICustomerService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        userService = context.getBean("customerService", ICustomerService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Inside filter");
        if (((HttpServletRequest) request).getSession().getAttribute("loggedUser") == null) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) ((HttpServletRequest) request).getUserPrincipal();
            if (token != null) {
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) token.getPrincipal();
                ((HttpServletRequest) request).getSession().setAttribute("loggedUser", userService.getUserByUsername(user.getUsername()));

            }
        } 
        //re-attach the customer object to the session
        else {
            User customer = (User) ((HttpServletRequest) request).getSession().getAttribute("loggedUser");
            if (customer instanceof Customer) {
                userService.updateCustomer(customer.getId(), (Customer) customer);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
