package com.labs;

import com.labs.client.WebServiceClient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.labs.errors.*;

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
                                       @WebParam(name = "studentAge") String age,
                                       @WebParam(name = "studentId") String studentId,
                                       @WebParam(name = "studentMark") String mark) throws EmptyFieldException, CastToIntException, FieldValueException {

        String status = "0";
        if (name != null && !name.trim().isEmpty() &&
                surname != null && !surname.trim().isEmpty() &&
                age != null && !age.trim().isEmpty() &&
                studentId != null && !studentId.trim().isEmpty() &&
                mark != null && !mark.trim().isEmpty()) {

                try {
                    int ageInt = Integer.parseInt(age.trim());
                    int studentIdInt = Integer.parseInt(studentId.trim());

                    if (mark.equals("неудовлетворительно") ||
                            mark.equals("удовлетворительно") ||
                            mark.equals("хорошо") ||
                            mark.equals("отлично")) {

                        PostgreSQLDAO dao = new PostgreSQLDAO();
                        status = dao.createStudent(name, surname, ageInt, studentIdInt, mark);

                    } else {
                        FieldValueFault fault = FieldValueFault.defaultInstance();
                        throw new FieldValueException("Error was occurred in class: " + StudentWebService.class.getName() +
                                ", method - createStudent(). \n Field 'mark' has not one of " +
                                "the required values: 'неудовлетворительно', 'удовлетворительно', 'хорошо', 'отлично'.",
                                fault);
                    }

                } catch (NumberFormatException ex) {
                    CastToIntFault fault = CastToIntFault.defaultInstance();
                    throw new CastToIntException("Error was occurred in class: " + StudentWebService.class.getName() +
                            ", method - createStudent(). \n We get 'NumberFormatException': " + ex +
                            ", when trying convert studentAge and studentID to integers.", fault);
                }


        } else {
            EmptyFieldFault fault = EmptyFieldFault.defaultInstance();
            throw new EmptyFieldException("Error was occurred in class " + StudentWebService.class.getName() +
                    ", method createStudent().", fault);
        }

        return status;
    }

    @WebMethod(operationName = "deleteStudent")
    public String deleteStudent(@WebParam(name = "rowId") int rowId) {
        PostgreSQLDAO dao = new PostgreSQLDAO();
        String status = dao.deleteStudent(rowId);
        System.out.println(status);
        return status;
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