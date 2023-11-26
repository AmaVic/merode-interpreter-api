
package be.vamaralds.merode.mxp;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;


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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metamodel"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guimodel"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="mxp.version" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" /&gt;
 *       &lt;attribute name="jmermaid.version" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" /&gt;
 *       &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metamodel",
    "guimodel"
})
@XmlRootElement(name = "mermaidmodel", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Mermaidmodel {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metamodel metamodel;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Guimodel guimodel;
    @XmlAttribute(name = "mxp.version", required = true)
    protected BigDecimal mxpVersion;
    @XmlAttribute(name = "jmermaid.version", required = true)
    protected BigDecimal jmermaidVersion;
    @XmlAttribute(name = "timestamp", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String timestamp;

    /**
     * Gets the value of the metamodel property.
     * 
     * @return
     *     possible object is
     *     {@link Metamodel }
     *     
     */
    public Metamodel getMetamodel() {
        return metamodel;
    }

    /**
     * Sets the value of the metamodel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metamodel }
     *     
     */
    public void setMetamodel(Metamodel value) {
        this.metamodel = value;
    }

    /**
     * Gets the value of the guimodel property.
     * 
     * @return
     *     possible object is
     *     {@link Guimodel }
     *     
     */
    public Guimodel getGuimodel() {
        return guimodel;
    }

    /**
     * Sets the value of the guimodel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guimodel }
     *     
     */
    public void setGuimodel(Guimodel value) {
        this.guimodel = value;
    }

    /**
     * Gets the value of the mxpVersion property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMxpVersion() {
        return mxpVersion;
    }

    /**
     * Sets the value of the mxpVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMxpVersion(BigDecimal value) {
        this.mxpVersion = value;
    }

    /**
     * Gets the value of the jmermaidVersion property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getJmermaidVersion() {
        return jmermaidVersion;
    }

    /**
     * Sets the value of the jmermaidVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setJmermaidVersion(BigDecimal value) {
        this.jmermaidVersion = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

}
