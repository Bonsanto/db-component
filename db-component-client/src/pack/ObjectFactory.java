
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

    private final static QName _WriteSimpleCSV_QNAME = new QName("http://server/", "writeSimpleCSV");
    private final static QName _MakeTransactionResponse_QNAME = new QName("http://server/", "makeTransactionResponse");
    private final static QName _WriteJSON_QNAME = new QName("http://server/", "writeJSON");
    private final static QName _QueryJSONResponse_QNAME = new QName("http://server/", "queryJSONResponse");
    private final static QName _MakeTransaction_QNAME = new QName("http://server/", "makeTransaction");
    private final static QName _WriteSimpleCSVResponse_QNAME = new QName("http://server/", "writeSimpleCSVResponse");
    private final static QName _IOException_QNAME = new QName("http://server/", "IOException");
    private final static QName _WriteEntireCSVResponse_QNAME = new QName("http://server/", "writeEntireCSVResponse");
    private final static QName _QueryJSON_QNAME = new QName("http://server/", "queryJSON");
    private final static QName _WriteEntireCSV_QNAME = new QName("http://server/", "writeEntireCSV");
    private final static QName _WriteJSONResponse_QNAME = new QName("http://server/", "writeJSONResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pack
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WriteEntireCSV }
     * 
     */
    public WriteEntireCSV createWriteEntireCSV() {
        return new WriteEntireCSV();
    }

    /**
     * Create an instance of {@link WriteJSONResponse }
     * 
     */
    public WriteJSONResponse createWriteJSONResponse() {
        return new WriteJSONResponse();
    }

    /**
     * Create an instance of {@link QueryJSON }
     * 
     */
    public QueryJSON createQueryJSON() {
        return new QueryJSON();
    }

    /**
     * Create an instance of {@link MakeTransaction }
     * 
     */
    public MakeTransaction createMakeTransaction() {
        return new MakeTransaction();
    }

    /**
     * Create an instance of {@link WriteSimpleCSVResponse }
     * 
     */
    public WriteSimpleCSVResponse createWriteSimpleCSVResponse() {
        return new WriteSimpleCSVResponse();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link WriteEntireCSVResponse }
     * 
     */
    public WriteEntireCSVResponse createWriteEntireCSVResponse() {
        return new WriteEntireCSVResponse();
    }

    /**
     * Create an instance of {@link QueryJSONResponse }
     * 
     */
    public QueryJSONResponse createQueryJSONResponse() {
        return new QueryJSONResponse();
    }

    /**
     * Create an instance of {@link WriteSimpleCSV }
     * 
     */
    public WriteSimpleCSV createWriteSimpleCSV() {
        return new WriteSimpleCSV();
    }

    /**
     * Create an instance of {@link MakeTransactionResponse }
     * 
     */
    public MakeTransactionResponse createMakeTransactionResponse() {
        return new MakeTransactionResponse();
    }

    /**
     * Create an instance of {@link WriteJSON }
     * 
     */
    public WriteJSON createWriteJSON() {
        return new WriteJSON();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WriteSimpleCSV }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "writeSimpleCSV")
    public JAXBElement<WriteSimpleCSV> createWriteSimpleCSV(WriteSimpleCSV value) {
        return new JAXBElement<WriteSimpleCSV>(_WriteSimpleCSV_QNAME, WriteSimpleCSV.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeTransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "makeTransactionResponse")
    public JAXBElement<MakeTransactionResponse> createMakeTransactionResponse(MakeTransactionResponse value) {
        return new JAXBElement<MakeTransactionResponse>(_MakeTransactionResponse_QNAME, MakeTransactionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WriteJSON }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "writeJSON")
    public JAXBElement<WriteJSON> createWriteJSON(WriteJSON value) {
        return new JAXBElement<WriteJSON>(_WriteJSON_QNAME, WriteJSON.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "makeTransaction")
    public JAXBElement<MakeTransaction> createMakeTransaction(MakeTransaction value) {
        return new JAXBElement<MakeTransaction>(_MakeTransaction_QNAME, MakeTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WriteSimpleCSVResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "writeSimpleCSVResponse")
    public JAXBElement<WriteSimpleCSVResponse> createWriteSimpleCSVResponse(WriteSimpleCSVResponse value) {
        return new JAXBElement<WriteSimpleCSVResponse>(_WriteSimpleCSVResponse_QNAME, WriteSimpleCSVResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WriteEntireCSVResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "writeEntireCSVResponse")
    public JAXBElement<WriteEntireCSVResponse> createWriteEntireCSVResponse(WriteEntireCSVResponse value) {
        return new JAXBElement<WriteEntireCSVResponse>(_WriteEntireCSVResponse_QNAME, WriteEntireCSVResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link WriteEntireCSV }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "writeEntireCSV")
    public JAXBElement<WriteEntireCSV> createWriteEntireCSV(WriteEntireCSV value) {
        return new JAXBElement<WriteEntireCSV>(_WriteEntireCSV_QNAME, WriteEntireCSV.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WriteJSONResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "writeJSONResponse")
    public JAXBElement<WriteJSONResponse> createWriteJSONResponse(WriteJSONResponse value) {
        return new JAXBElement<WriteJSONResponse>(_WriteJSONResponse_QNAME, WriteJSONResponse.class, null, value);
    }

}
