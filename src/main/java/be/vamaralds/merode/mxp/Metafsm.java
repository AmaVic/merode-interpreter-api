
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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metastates"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metatransitions"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="codegeneration" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metastates",
    "metatransitions"
})
@XmlRootElement(name = "metafsm", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Metafsm {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metastates metastates;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metatransitions metatransitions;
    @XmlAttribute(name = "id", required = true)
    protected BigInteger id;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "codegeneration", required = true)
    protected boolean codegeneration;

    /**
     * Gets the value of the metastates property.
     * 
     * @return
     *     possible object is
     *     {@link Metastates }
     *     
     */
    public Metastates getMetastates() {
        return metastates;
    }

    /**
     * Sets the value of the metastates property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metastates }
     *     
     */
    public void setMetastates(Metastates value) {
        this.metastates = value;
    }

    /**
     * Gets the value of the metatransitions property.
     * 
     * @return
     *     possible object is
     *     {@link Metatransitions }
     *     
     */
    public Metatransitions getMetatransitions() {
        return metatransitions;
    }

    /**
     * Sets the value of the metatransitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metatransitions }
     *     
     */
    public void setMetatransitions(Metatransitions value) {
        this.metatransitions = value;
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
     * Gets the value of the codegeneration property.
     * 
     */
    public boolean isCodegeneration() {
        return codegeneration;
    }

    /**
     * Sets the value of the codegeneration property.
     * 
     */
    public void setCodegeneration(boolean value) {
        this.codegeneration = value;
    }

}
