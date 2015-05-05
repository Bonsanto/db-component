
package pack;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pack package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryJSONResponse_QNAME = new QName("http://server/", "queryJSONResponse");
    private final static QName _QueryJSON_QNAME = new QName("http://server/", "queryJSON");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pack
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryJSONResponse }
     * 
     */
    public QueryJSONResponse createQueryJSONResponse() {
        return new QueryJSONResponse();
    }

    /**
     * Create an instance of {@link QueryJSON }
     * 
     */
    public QueryJSON createQueryJSON() {
        return new QueryJSON();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryJSONResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "queryJSONResponse")
    public JAXBElement<QueryJSONResponse> createQueryJSONResponse(QueryJSONResponse value) {
        return new JAXBElement<QueryJSONResponse>(_QueryJSONResponse_QNAME, QueryJSONResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryJSON }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "queryJSON")
    public JAXBElement<QueryJSON> createQueryJSON(QueryJSON value) {
        return new JAXBElement<QueryJSON>(_QueryJSON_QNAME, QueryJSON.class, null, value);
    }

}
