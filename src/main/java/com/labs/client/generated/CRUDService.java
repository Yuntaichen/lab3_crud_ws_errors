
package com.labs.client.generated;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CRUDService", targetNamespace = "http://labs.com/", wsdlLocation = "http://localhost:8080/CRUDService?wsdl")
public class CRUDService
    extends Service
{

    private final static URL CRUDSERVICE_WSDL_LOCATION;
    private final static WebServiceException CRUDSERVICE_EXCEPTION;
    private final static QName CRUDSERVICE_QNAME = new QName("http://labs.com/", "CRUDService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/CRUDService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CRUDSERVICE_WSDL_LOCATION = url;
        CRUDSERVICE_EXCEPTION = e;
    }

    public CRUDService() {
        super(__getWsdlLocation(), CRUDSERVICE_QNAME);
    }

    public CRUDService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CRUDSERVICE_QNAME, features);
    }

    public CRUDService(URL wsdlLocation) {
        super(wsdlLocation, CRUDSERVICE_QNAME);
    }

    public CRUDService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CRUDSERVICE_QNAME, features);
    }

    public CRUDService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CRUDService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns StudentWebService
     */
    @WebEndpoint(name = "StudentWebServicePort")
    public StudentWebService getStudentWebServicePort() {
        return super.getPort(new QName("http://labs.com/", "StudentWebServicePort"), StudentWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StudentWebService
     */
    @WebEndpoint(name = "StudentWebServicePort")
    public StudentWebService getStudentWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://labs.com/", "StudentWebServicePort"), StudentWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CRUDSERVICE_EXCEPTION!= null) {
            throw CRUDSERVICE_EXCEPTION;
        }
        return CRUDSERVICE_WSDL_LOCATION;
    }

}