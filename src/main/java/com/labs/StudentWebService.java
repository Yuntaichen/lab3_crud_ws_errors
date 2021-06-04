package com.labs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@WebService(serviceName = "CRUDService")
public class StudentWebService {
    @WebMethod(operationName = "getStudentsByFields")
    public LinkedHashSet<Student> getStudentsByFields(@WebParam(name = "fieldValue") String[] searchArgs) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.getStudentsByFields(searchArgs);
    }

    @WebMethod(operationName = "createStudent")
    public String createStudent(@WebParam(name = "studentName") String name,
                                       @WebParam(name = "studentSurname") String surname,
                                       @WebParam(name = "studentAge") int age,
                                       @WebParam(name = "studentId") int studentId,
                                       @WebParam(name = "studentMark") String mark) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.createStudent(name, surname, age, studentId, mark);
    }

    @WebMethod(operationName = "deleteStudent")
    public String deleteStudent(@WebParam(name = "rowId") int rowId) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.deleteStudent(rowId);
    }

    @WebMethod(operationName = "updateStudent")
    public String updateStudent(@WebParam(name = "rowId") int rowId,
                                @WebParam(name = "studentName") String name,
                                @WebParam(name = "studentSurname") String surname,
                                @WebParam(name = "studentAge") int age,
                                @WebParam(name = "studentId") int studentId,
                                @WebParam(name = "studentMark") String mark) {

        List<String> updateArgs = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) updateArgs.add("name = '" + name + "'");
        if (surname != null && !surname.trim().isEmpty()) updateArgs.add("surname = '" + surname + "'");
        if (age != 0) updateArgs.add("age = '" + age + "'");
        if (studentId != 0) updateArgs.add("studentId = '" + studentId + "'");
        if (mark != null && !mark.trim().isEmpty()) updateArgs.add("mark = '" + mark + "'");


        PostgreSQLDAO dao = new PostgreSQLDAO();
        return dao.updateStudent(rowId, updateArgs);
    }

}