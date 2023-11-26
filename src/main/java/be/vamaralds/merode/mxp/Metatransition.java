
package be.vamaralds.merode.mxp;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metatransitionmethods"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="legacy_name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="sourcestateid" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="targetstateid" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metatransitionmethods"
})
@XmlRootElement(name = "metatransition", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Metatransition {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metatransitionmethods metatransitionmethods;
    @XmlAttribute(name = "id", required = true)
    protected BigInteger id;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "legacy_name")
    protected String legacyName;
    @XmlAttribute(name = "sourcestateid", required = true)
    protected BigInteger sourcestateid;
    @XmlAttribute(name = "targetstateid", required = true)
    protected BigInteger targetstateid;

    /**
     * Gets the value of the metatransitionmethods property.
     * 
     * @return
     *     possible object is
     *     {@link Metatransitionmethods }
     *     
     */
    public Metatransitionmethods getMetatransitionmethods() {
        return metatransitionmethods;
    }

    /**
     * Sets the value of the metatransitionmethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metatransitionmethods }
     *     
     */
    public void setMetatransitionmethods(Metatransitionmethods value) {
        this.metatransitionmethods = value;
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
     * Gets the value of the legacyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyName() {
        return legacyName;
    }

    /**
     * Sets the value of the legacyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyName(String value) {
        this.legacyName = value;
    }

    /**
     * Gets the value of the sourcestateid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSourcestateid() {
        return sourcestateid;
    }

    /**
     * Sets the value of the sourcestateid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSourcestateid(BigInteger value) {
        this.sourcestateid = value;
    }

    /**
     * Gets the value of the targetstateid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTargetstateid() {
        return targetstateid;
    }

    /**
     * Sets the value of the targetstateid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTargetstateid(BigInteger value) {
        this.targetstateid = value;
    }

}
