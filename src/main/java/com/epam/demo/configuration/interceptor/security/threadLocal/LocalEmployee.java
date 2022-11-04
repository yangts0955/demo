package com.epam.demo.configuration.interceptor.security.threadLocal;

import com.epam.demo.Entity.Employee;
import com.epam.demo.vo.EmployeeVO;

import java.util.HashMap;
import java.util.Map;

public class LocalEmployee {

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static void set(Employee employee) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", employee);
        LocalEmployee.threadLocal.set(map);
    }

    public static void remove() {
        LocalEmployee.threadLocal.remove();
    }

    public static Employee getEmployee() {
        Map<String, Object> map = LocalEmployee.threadLocal.get();
        Employee employee = (Employee) map.get("user");
        return employee;
    }

}
