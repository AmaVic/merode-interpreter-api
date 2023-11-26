
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
 *       &lt;attribute name="methodid" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="safeid" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="methodname" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "metatransitionmethod", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Metatransitionmethod {

    @XmlAttribute(name = "methodid", required = true)
    protected BigInteger methodid;
    @XmlAttribute(name = "safeid")
    protected BigInteger safeid;
    @XmlAttribute(name = "methodname")
    protected String methodname;

    /**
     * Gets the value of the methodid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMethodid() {
        return methodid;
    }

    /**
     * Sets the value of the methodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMethodid(BigInteger value) {
        this.methodid = value;
    }

    /**
     * Gets the value of the safeid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSafeid() {
        return safeid;
    }

    /**
     * Sets the value of the safeid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSafeid(BigInteger value) {
        this.safeid = value;
    }

    /**
     * Gets the value of the methodname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethodname() {
        return methodname;
    }

    /**
     * Sets the value of the methodname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodname(String value) {
        this.methodname = value;
    }

}
