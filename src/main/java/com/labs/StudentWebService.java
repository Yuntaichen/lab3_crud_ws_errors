package com.labs;

import com.labs.client.WebServiceClient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.labs.errors.CRUDServiceCreateStudentEmpryFieldFault;
import com.labs.errors.EmptyFieldException;

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
                                       @WebParam(name = "studentMark") String mark) throws EmptyFieldException {

        String status = "0";
        if (name != null && !name.trim().isEmpty() &&
                surname != null && !surname.trim().isEmpty() &&
                age != null && !age.trim().isEmpty() &&
                studentId != null && !studentId.trim().isEmpty() &&
                mark != null && !mark.trim().isEmpty()) {

                System.out.println("Везде есть значения");

                try {
                    int ageInt = Integer.parseInt(age.trim());
                    int studentIdInt = Integer.parseInt(studentId.trim());

                    if (mark.equals("неудовлетворительно") ||
                            mark.equals("удовлетворительно") ||
                            mark.equals("хорошо") ||
                            mark.equals("отлично")) {
                        System.out.println("mark имеет допустимое значение");

                        PostgreSQLDAO dao = new PostgreSQLDAO();
                        status = dao.createStudent(name, surname, ageInt, studentIdInt, mark);

                    } else {
                        System.out.println("mark не имеет допустимое значение. Выбрасываем исключение.");
                    }

                } catch (NumberFormatException ex) {
                    System.out.println("Невозможно привести к int значения age и studentId");
                    Logger.getLogger(WebServiceClient.class.getName()).log(Level.SEVERE, null, ex);
                }


        } else {
            CRUDServiceCreateStudentEmpryFieldFault fault = CRUDServiceCreateStudentEmpryFieldFault.defaultInstance();
            throw new EmptyFieldException("There are empty strings in createStudent() method arguments", fault);
        }

        System.out.println(status);
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