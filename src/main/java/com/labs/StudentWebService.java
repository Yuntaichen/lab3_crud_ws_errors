package com.labs;

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

        String status;

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
    public String deleteStudent(@WebParam(name = "rowId") String rowId) throws CastToIntException, RowIsNotExistsException {
        String status;
        try {
            int rowIdInt = Integer.parseInt(rowId.trim());
            PostgreSQLDAO dao = new PostgreSQLDAO();
            status = dao.deleteStudent(rowIdInt);
            System.out.println(status);
            if (status.equals("0")) {
                RowIsNotExistsFault fault = RowIsNotExistsFault.defaultInstance();
                throw new RowIsNotExistsException("Error was occurred in class: " + StudentWebService.class.getName() +
                        ", method - deleteStudent(). \n We get 'status = 0' after deletion row in table in DB with rowId " + rowId, fault);
            }

        } catch (NumberFormatException ex) {
            CastToIntFault fault = CastToIntFault.defaultInstance();
            throw new CastToIntException("Error was occurred in class: " + StudentWebService.class.getName() +
                    ", method - deleteStudent(). \n We get 'NumberFormatException': " + ex +
                    ", when trying convert rowId to int.", fault);
        }

        return status;
    }

    @WebMethod(operationName = "updateStudent")
    public String updateStudent(@WebParam(name = "rowId") String rowId,
                                @WebParam(name = "studentName") String name,
                                @WebParam(name = "studentSurname") String surname,
                                @WebParam(name = "studentAge") String age,
                                @WebParam(name = "studentId") String studentId,
                                @WebParam(name = "studentMark") String mark)
            throws EmptyFieldException,
            RowIsNotExistsException,
            CastToIntException,
            FieldValueException {

        String status;

        try {
            int rowIdInt = Integer.parseInt(rowId.trim());

            Integer.parseInt(age.trim());
            Integer.parseInt(studentId.trim());

            List<String> updateArgs = new ArrayList<>();

            if (name != null && !name.trim().isEmpty()) updateArgs.add("name = '" + name + "'");
            if (surname != null && !surname.trim().isEmpty()) updateArgs.add("surname = '" + surname + "'");

            if (!age.trim().isEmpty()) {
                try {
                    Integer.parseInt(age.trim());
                    updateArgs.add("age = '" + age + "'");
                } catch (NumberFormatException ex) {
                    CastToIntFault fault = CastToIntFault.defaultInstance();
                    throw new CastToIntException("Error was occurred in class: " + StudentWebService.class.getName() +
                            ", method - updateStudent(). \n We get 'NumberFormatException': " + ex +
                            ", when trying convert 'age' to int.", fault);
                }
            }

            if (!studentId.trim().isEmpty()) {
                try {
                    Integer.parseInt(studentId.trim());
                    updateArgs.add("studentId = '" + studentId + "'");
                } catch (NumberFormatException ex) {
                    CastToIntFault fault = CastToIntFault.defaultInstance();
                    throw new CastToIntException("Error was occurred in class: " + StudentWebService.class.getName() +
                            ", method - updateStudent(). \n We get 'NumberFormatException': " + ex +
                            ", when trying convert 'studentId' to int.", fault);
                }
            }

            if (mark != null && !mark.trim().isEmpty()) {
                if (mark.equals("неудовлетворительно") ||
                        mark.equals("удовлетворительно") ||
                        mark.equals("хорошо") ||
                        mark.equals("отлично")) {
                    updateArgs.add("mark = '" + mark + "'");
                } else {
                    FieldValueFault fault = FieldValueFault.defaultInstance();
                    throw new FieldValueException("Error was occurred in class: " + StudentWebService.class.getName() +
                            ", method - updateStudent(). \n Field 'mark' has not one of " +
                            "the required values: 'неудовлетворительно', 'удовлетворительно', 'хорошо', 'отлично'.",
                            fault);
                }
            }

            int i = 0;
            for (String param : updateArgs) {
                if (param != null && !param.trim().isEmpty()) {
                    i++;
                }
            }
            if (i > 0) {

                PostgreSQLDAO dao = new PostgreSQLDAO();
                status = dao.updateStudent(rowIdInt, updateArgs);

            } else {
                EmptyFieldFault fault = EmptyFieldFault.defaultInstance();
                fault.setMessage("All required parameters are empty. Please, input at least one of them.");
                throw new EmptyFieldException("Error was occurred in class " + StudentWebService.class.getName() +
                        ", method updateStudent().", fault);
            }

            if (status.equals("0")) {
                RowIsNotExistsFault fault = RowIsNotExistsFault.defaultInstance();
                throw new RowIsNotExistsException("Error was occurred in class: " + StudentWebService.class.getName() +
                        ", method - updateStudent(). \n We get 'status = 0' after deletion row in table in DB with rowId " + rowId, fault);
            }

        } catch (NumberFormatException ex) {
            CastToIntFault fault = CastToIntFault.defaultInstance();
            throw new CastToIntException("Error was occurred in class: " + StudentWebService.class.getName() +
                    ", method - updateStudent(). \n We get 'NumberFormatException': " + ex +
                    ", when trying convert rowId to int.", fault);
        }

        return status;
    }
}