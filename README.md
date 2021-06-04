**Лабораторная работа 3**

# Обработка ошибок в SOAP-сервисе

## Задание

Основываясь на информации из раздела 2.8 добавить поддержку обработки ошибок в сервис, 
разработанный в Лабораторной работе 2 (см. `lab2_crud_ws`). 
Возможные ошибки, которые могут происходить при добавлении новых записей — например, 
неверное значение одного из полей, при изменении, удалении — попытка изменить или удалить несуществующую запись. 
Соответствующим образом необходимо обновить клиентское приложение.

## Ход работы

В разделе 2.8 приводится информация о том, как осуществить возврат сервисом информации об исключительной ситуации, что предполагает содержимое блока `soap:Fault`. Клиент при этом может проанализировать полученный код ошибки и сообщение. 

Для начала будем использовать SoapUI для тестирования возвращаемых сервисом ответом. 

Результат обработки ошибки - возврат сервисом SOAP-сообщения, содержащего `soap:Fault` с указанием информации об ошибке. Используется стандартный для Java подход к обработке исключений, т.е. использование ключевого слова `throw`, при этом в JAX-WS также делается наследование от класса java.lang.Exception, но требуется аннотация `@WebFault` с указанием в качестве параметра `faultBean` соответствующего класса, представляющего определенную ошибку. Пример аннотации для класса-исключения:

```java
@WebFault(faultBean="packageName.FaultBean")
```

 Класс исключения должен определять метод `getFaultInfo()`, возвращающий экземпляр `FaultBean` и иметь 2 конструктора: 

1. Конструктор с параметрами `String message, FaultBean fault`;
2. Конструктор с параметрами `String message, WebServiceFault fault, Throwable cause`.

Класс `FaultBean` должен определять метод String getMessage().

> Обозначенные выше правила обязательны для JAX-WS



### Изменения в StudentWebService

Теперь будем вносить изменения в ранее разработанный веб-сервис для обработки ошибок. 

Основной класс сервиса `StudentWebService` содержит веб-методы:

* getStudentsByFields()
* createStudent()
* deleteStudent()
* updateStudent()

Далее будем поочередно рассматривать обозначенные методы и добавлять к ним обработку ошибок. 



#### Метод getStudentsByFields()

Метод `getStudentsByFields()` - принимает в качестве параметров значения полей `@WebParam(name = "fieldValue") String[] searchArgs`  и возвращает `LinkedHashSet<Student>`, т.е. множество уникальных записей из таблицы БД, соответствующих классу `Student`.



#### Метод createStudent()

Метод `createStudent()` - принимает в качестве параметров значения полей:

```java
@WebParam(name = "studentName") String name,
@WebParam(name = "studentSurname") String surname,
@WebParam(name = "studentAge") int age,
@WebParam(name = "studentId") int studentId,
@WebParam(name = "studentMark") String mark
```

Возвращает `String status`, т. е. строку с указанием статуса операции в виде нуля ("0" - операция не выполнена) или единицы ("1" - операция успешно выполнена) .



#### Метод deleteStudent()

Метод `deleteStudent()` - принимает в качестве параметров значение идентификатора записи в таблице в БД `@WebParam(name = "rowId") int rowId`  и возвращает `String status`, т. е. строку с указанием статуса операции в виде нуля ("0" - операция не выполнена) или единицы ("1" - операция успешно выполнена).



#### Метод updateStudent()

Метод `updateStudent()` - принимает в качестве параметров значения полей, причем данный метод может принимать как отдельные значения для изменения, так и все значения для полного изменения записи:

```java
@WebParam(name = "rowId") int rowId,
@WebParam(name = "studentName") String name,
@WebParam(name = "studentSurname") String surname,
@WebParam(name = "studentAge") int age,
@WebParam(name = "studentId") int studentId,
@WebParam(name = "studentMark") String mark
```

Возвращает `String status`, т. е. строку с указанием статуса операции в виде нуля ("0" - операция не выполнена) или единицы ("1" - операция успешно выполнена) .



## Обновление клиентского приложения

Изначально сгенерируем новые классы по WSDL-описанию:

```shell
wsimport -keep -p com.labs.client.generated http://localhost:8080/CRUDService?wsdl
```