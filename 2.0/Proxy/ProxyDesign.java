package designPatterns;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@AllArgsConstructor
class EmployeeDAO{
    String name;
    String role;
}
interface Employee{
    void addEmployee(EmployeeDAO employeeDAO);
    void removeEmployee(EmployeeDAO employeeDAO);
}

class EmployeeManager implements Employee{
    List<EmployeeDAO> employeeDAOList = new ArrayList<>();
    @Override
    public void addEmployee(EmployeeDAO employeeDAO) {
        employeeDAOList.add(employeeDAO);
    }

    @Override
    public void removeEmployee(EmployeeDAO employeeDAO) {
        employeeDAOList.remove(employeeDAO);
    }
}

class EmployeeManagerProxy implements Employee{
    Employee employee;

    EmployeeManagerProxy(){
        this.employee= new EmployeeManager();
    }

    @Override
    public void addEmployee(EmployeeDAO employeeDAO) {
        if(Objects.equals(employeeDAO.role, "ADMIN")){
            employee.addEmployee(employeeDAO);
        }
    }

    @Override
    public void removeEmployee(EmployeeDAO employeeDAO) {
        if(Objects.equals(employeeDAO.role, "ADMIN")){
            employee.removeEmployee(employeeDAO);
        }
    }
}
public class ProxyDesign {
    static void main(String[] args) {
        EmployeeDAO emp = new EmployeeDAO("user1", "IT");
        Employee employee= new EmployeeManagerProxy();
        employee.addEmployee(emp);
    }
}
