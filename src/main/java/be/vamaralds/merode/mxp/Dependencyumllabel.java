
package be.vamaralds.merode.mxp;

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
 *       &lt;attribute name="masterside" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "dependencyumllabel", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Dependencyumllabel {

    @XmlAttribute(name = "masterside", required = true)
    protected boolean masterside;
    @XmlAttribute(name = "position", required = true)
    protected String position;

    /**
     * Gets the value of the masterside property.
     * 
     */
    public boolean isMasterside() {
        return masterside;
    }

    /**
     * Sets the value of the masterside property.
     * 
     */
    public void setMasterside(boolean value) {
        this.masterside = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

}
