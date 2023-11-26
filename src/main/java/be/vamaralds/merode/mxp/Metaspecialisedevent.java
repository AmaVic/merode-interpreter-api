
package be.vamaralds.merode.mxp;

import java.math.BigInteger;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metadescription"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metaattributes"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ownereventid" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="inheritanceid" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metadescription",
    "metaattributes"
})
@XmlRootElement(name = "metaspecialisedevent", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Metaspecialisedevent {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected String metadescription;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metaattributes metaattributes;
    @XmlAttribute(name = "id", required = true)
    protected BigInteger id;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "ownereventid", required = true)
    protected BigInteger ownereventid;
    @XmlAttribute(name = "inheritanceid", required = true)
    protected BigInteger inheritanceid;

    /**
     * Gets the value of the metadescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetadescription() {
        return metadescription;
    }

    /**
     * Sets the value of the metadescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetadescription(String value) {
        this.metadescription = value;
    }

    /**
     * Gets the value of the metaattributes property.
     * 
     * @return
     *     possible object is
     *     {@link Metaattributes }
     *     
     */
    public Metaattributes getMetaattributes() {
        return metaattributes;
    }

    /**
     * Sets the value of the metaattributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metaattributes }
     *     
     */
    public void setMetaattributes(Metaattributes value) {
        this.metaattributes = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the ownereventid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOwnereventid() {
        return ownereventid;
    }

    /**
     * Sets the value of the ownereventid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOwnereventid(BigInteger value) {
        this.ownereventid = value;
    }

    /**
     * Gets the value of the inheritanceid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getInheritanceid() {
        return inheritanceid;
    }

    /**
     * Sets the value of the inheritanceid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setInheritanceid(BigInteger value) {
        this.inheritanceid = value;
    }

}
