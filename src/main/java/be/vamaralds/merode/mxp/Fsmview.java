
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
 *       &lt;attribute name="gridcolor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="backgroundcolor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="gridenabled" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="gridvisible" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="gridmode" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="gridsize" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="scale" use="required" type="{http://www.w3.org/2001/XMLSchema}double" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "fsmview", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Fsmview {

    @XmlAttribute(name = "gridcolor", required = true)
    protected String gridcolor;
    @XmlAttribute(name = "backgroundcolor", required = true)
    protected String backgroundcolor;
    @XmlAttribute(name = "gridenabled", required = true)
    protected boolean gridenabled;
    @XmlAttribute(name = "gridvisible", required = true)
    protected boolean gridvisible;
    @XmlAttribute(name = "gridmode", required = true)
    protected BigInteger gridmode;
    @XmlAttribute(name = "gridsize", required = true)
    protected BigInteger gridsize;
    @XmlAttribute(name = "scale", required = true)
    protected double scale;

    /**
     * Gets the value of the gridcolor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGridcolor() {
        return gridcolor;
    }

    /**
     * Sets the value of the gridcolor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGridcolor(String value) {
        this.gridcolor = value;
    }

    /**
     * Gets the value of the backgroundcolor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    /**
     * Sets the value of the backgroundcolor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackgroundcolor(String value) {
        this.backgroundcolor = value;
    }

    /**
     * Gets the value of the gridenabled property.
     * 
     */
    public boolean isGridenabled() {
        return gridenabled;
    }

    /**
     * Sets the value of the gridenabled property.
     * 
     */
    public void setGridenabled(boolean value) {
        this.gridenabled = value;
    }

    /**
     * Gets the value of the gridvisible property.
     * 
     */
    public boolean isGridvisible() {
        return gridvisible;
    }

    /**
     * Sets the value of the gridvisible property.
     * 
     */
    public void setGridvisible(boolean value) {
        this.gridvisible = value;
    }

    /**
     * Gets the value of the gridmode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGridmode() {
        return gridmode;
    }

    /**
     * Sets the value of the gridmode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGridmode(BigInteger value) {
        this.gridmode = value;
    }

    /**
     * Gets the value of the gridsize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGridsize() {
        return gridsize;
    }

    /**
     * Sets the value of the gridsize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGridsize(BigInteger value) {
        this.gridsize = value;
    }

    /**
     * Gets the value of the scale property.
     * 
     */
    public double getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     * 
     */
    public void setScale(double value) {
        this.scale = value;
    }

}
