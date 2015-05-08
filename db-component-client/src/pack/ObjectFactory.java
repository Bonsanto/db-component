
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
    private final static QName _MakeTransaction_QNAME = new QName("http://server/", "makeTransaction");
    private final static QName _MakeTransactionResponse_QNAME = new QName("http://server/", "makeTransactionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pack
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MakeTransaction }
     * 
     */
    public MakeTransaction createMakeTransaction() {
        return new MakeTransaction();
    }

    /**
     * Create an instance of {@link MakeTransactionResponse }
     * 
     */
    public MakeTransactionResponse createMakeTransactionResponse() {
        return new MakeTransactionResponse();
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "makeTransaction")
    public JAXBElement<MakeTransaction> createMakeTransaction(MakeTransaction value) {
        return new JAXBElement<MakeTransaction>(_MakeTransaction_QNAME, MakeTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeTransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "makeTransactionResponse")
    public JAXBElement<MakeTransactionResponse> createMakeTransactionResponse(MakeTransactionResponse value) {
        return new JAXBElement<MakeTransactionResponse>(_MakeTransactionResponse_QNAME, MakeTransactionResponse.class, null, value);
    }

}
