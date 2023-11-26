
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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}oetobjects"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}oetevents"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="highlightercolor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="linecolor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="backgroundcolor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="hidedisabledmethods" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="headerrotation" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="scale" use="required" type="{http://www.w3.org/2001/XMLSchema}double" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "oetobjects",
    "oetevents"
})
@XmlRootElement(name = "oetview", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Oetview {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Oetobjects oetobjects;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Oetevents oetevents;
    @XmlAttribute(name = "highlightercolor", required = true)
    protected String highlightercolor;
    @XmlAttribute(name = "linecolor", required = true)
    protected String linecolor;
    @XmlAttribute(name = "backgroundcolor", required = true)
    protected String backgroundcolor;
    @XmlAttribute(name = "hidedisabledmethods", required = true)
    protected boolean hidedisabledmethods;
    @XmlAttribute(name = "headerrotation", required = true)
    protected BigInteger headerrotation;
    @XmlAttribute(name = "scale", required = true)
    protected double scale;

    /**
     * Gets the value of the oetobjects property.
     * 
     * @return
     *     possible object is
     *     {@link Oetobjects }
     *     
     */
    public Oetobjects getOetobjects() {
        return oetobjects;
    }

    /**
     * Sets the value of the oetobjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link Oetobjects }
     *     
     */
    public void setOetobjects(Oetobjects value) {
        this.oetobjects = value;
    }

    /**
     * Gets the value of the oetevents property.
     * 
     * @return
     *     possible object is
     *     {@link Oetevents }
     *     
     */
    public Oetevents getOetevents() {
        return oetevents;
    }

    /**
     * Sets the value of the oetevents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Oetevents }
     *     
     */
    public void setOetevents(Oetevents value) {
        this.oetevents = value;
    }

    /**
     * Gets the value of the highlightercolor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHighlightercolor() {
        return highlightercolor;
    }

    /**
     * Sets the value of the highlightercolor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHighlightercolor(String value) {
        this.highlightercolor = value;
    }

    /**
     * Gets the value of the linecolor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinecolor() {
        return linecolor;
    }

    /**
     * Sets the value of the linecolor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinecolor(String value) {
        this.linecolor = value;
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
     * Gets the value of the hidedisabledmethods property.
     * 
     */
    public boolean isHidedisabledmethods() {
        return hidedisabledmethods;
    }

    /**
     * Sets the value of the hidedisabledmethods property.
     * 
     */
    public void setHidedisabledmethods(boolean value) {
        this.hidedisabledmethods = value;
    }

    /**
     * Gets the value of the headerrotation property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHeaderrotation() {
        return headerrotation;
    }

    /**
     * Sets the value of the headerrotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHeaderrotation(BigInteger value) {
        this.headerrotation = value;
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
