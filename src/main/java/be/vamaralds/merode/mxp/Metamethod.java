
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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metaprecondition"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metaimplementation"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metapostcondition"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metaattributes"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="provenance" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ownerobjectid" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="ownereventid" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="viamethod" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="viadependency" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="viainheritance" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metaprecondition",
    "metaimplementation",
    "metapostcondition",
    "metaattributes"
})
@XmlRootElement(name = "metamethod", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Metamethod {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected String metaprecondition;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected String metaimplementation;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected String metapostcondition;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metaattributes metaattributes;
    @XmlAttribute(name = "id", required = true)
    protected BigInteger id;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "provenance", required = true)
    protected String provenance;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "ownerobjectid", required = true)
    protected BigInteger ownerobjectid;
    @XmlAttribute(name = "ownereventid", required = true)
    protected BigInteger ownereventid;
    @XmlAttribute(name = "viamethod")
    protected BigInteger viamethod;
    @XmlAttribute(name = "viadependency")
    protected BigInteger viadependency;
    @XmlAttribute(name = "viainheritance")
    protected BigInteger viainheritance;

    /**
     * Gets the value of the metaprecondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetaprecondition() {
        return metaprecondition;
    }

    /**
     * Sets the value of the metaprecondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetaprecondition(String value) {
        this.metaprecondition = value;
    }

    /**
     * Gets the value of the metaimplementation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetaimplementation() {
        return metaimplementation;
    }

    /**
     * Sets the value of the metaimplementation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetaimplementation(String value) {
        this.metaimplementation = value;
    }

    /**
     * Gets the value of the metapostcondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetapostcondition() {
        return metapostcondition;
    }

    /**
     * Sets the value of the metapostcondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetapostcondition(String value) {
        this.metapostcondition = value;
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
     * Gets the value of the provenance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvenance() {
        return provenance;
    }

    /**
     * Sets the value of the provenance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvenance(String value) {
        this.provenance = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the ownerobjectid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOwnerobjectid() {
        return ownerobjectid;
    }

    /**
     * Sets the value of the ownerobjectid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOwnerobjectid(BigInteger value) {
        this.ownerobjectid = value;
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
     * Gets the value of the viamethod property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getViamethod() {
        return viamethod;
    }

    /**
     * Sets the value of the viamethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setViamethod(BigInteger value) {
        this.viamethod = value;
    }

    /**
     * Gets the value of the viadependency property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getViadependency() {
        return viadependency;
    }

    /**
     * Sets the value of the viadependency property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setViadependency(BigInteger value) {
        this.viadependency = value;
    }

    /**
     * Gets the value of the viainheritance property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getViainheritance() {
        return viainheritance;
    }

    /**
     * Sets the value of the viainheritance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setViainheritance(BigInteger value) {
        this.viainheritance = value;
    }

}
