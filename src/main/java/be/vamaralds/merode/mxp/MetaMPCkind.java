
package be.vamaralds.merode.mxp;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for metaMPCkind complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="metaMPCkind"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="pathsEqual" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "metaMPCkind", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class MetaMPCkind {

    @XmlAttribute(name = "pathsEqual")
    protected Boolean pathsEqual;

    /**
     * Gets the value of the pathsEqual property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPathsEqual() {
        return pathsEqual;
    }

    /**
     * Sets the value of the pathsEqual property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPathsEqual(Boolean value) {
        this.pathsEqual = value;
    }

}
