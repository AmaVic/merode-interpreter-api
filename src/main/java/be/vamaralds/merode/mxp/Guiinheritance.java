
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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}edgecontrolpoints"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="refid" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="linewidth" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="linecolor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="textcolor" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="fontfamily" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="fontsize" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="fontbold" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="fontitalic" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="labelvisible" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="labelposition" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "edgecontrolpoints"
})
@XmlRootElement(name = "guiinheritance", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Guiinheritance {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Edgecontrolpoints edgecontrolpoints;
    @XmlAttribute(name = "refid", required = true)
    protected BigInteger refid;
    @XmlAttribute(name = "linewidth", required = true)
    protected BigInteger linewidth;
    @XmlAttribute(name = "linecolor", required = true)
    protected String linecolor;
    @XmlAttribute(name = "textcolor", required = true)
    protected String textcolor;
    @XmlAttribute(name = "fontfamily", required = true)
    protected String fontfamily;
    @XmlAttribute(name = "fontsize", required = true)
    protected BigInteger fontsize;
    @XmlAttribute(name = "fontbold", required = true)
    protected boolean fontbold;
    @XmlAttribute(name = "fontitalic", required = true)
    protected boolean fontitalic;
    @XmlAttribute(name = "labelvisible", required = true)
    protected boolean labelvisible;
    @XmlAttribute(name = "labelposition", required = true)
    protected String labelposition;

    /**
     * Gets the value of the edgecontrolpoints property.
     * 
     * @return
     *     possible object is
     *     {@link Edgecontrolpoints }
     *     
     */
    public Edgecontrolpoints getEdgecontrolpoints() {
        return edgecontrolpoints;
    }

    /**
     * Sets the value of the edgecontrolpoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link Edgecontrolpoints }
     *     
     */
    public void setEdgecontrolpoints(Edgecontrolpoints value) {
        this.edgecontrolpoints = value;
    }

    /**
     * Gets the value of the refid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRefid() {
        return refid;
    }

    /**
     * Sets the value of the refid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRefid(BigInteger value) {
        this.refid = value;
    }

    /**
     * Gets the value of the linewidth property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLinewidth() {
        return linewidth;
    }

    /**
     * Sets the value of the linewidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLinewidth(BigInteger value) {
        this.linewidth = value;
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
     * Gets the value of the textcolor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextcolor() {
        return textcolor;
    }

    /**
     * Sets the value of the textcolor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextcolor(String value) {
        this.textcolor = value;
    }

    /**
     * Gets the value of the fontfamily property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontfamily() {
        return fontfamily;
    }

    /**
     * Sets the value of the fontfamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontfamily(String value) {
        this.fontfamily = value;
    }

    /**
     * Gets the value of the fontsize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFontsize() {
        return fontsize;
    }

    /**
     * Sets the value of the fontsize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFontsize(BigInteger value) {
        this.fontsize = value;
    }

    /**
     * Gets the value of the fontbold property.
     * 
     */
    public boolean isFontbold() {
        return fontbold;
    }

    /**
     * Sets the value of the fontbold property.
     * 
     */
    public void setFontbold(boolean value) {
        this.fontbold = value;
    }

    /**
     * Gets the value of the fontitalic property.
     * 
     */
    public boolean isFontitalic() {
        return fontitalic;
    }

    /**
     * Sets the value of the fontitalic property.
     * 
     */
    public void setFontitalic(boolean value) {
        this.fontitalic = value;
    }

    /**
     * Gets the value of the labelvisible property.
     * 
     */
    public boolean isLabelvisible() {
        return labelvisible;
    }

    /**
     * Sets the value of the labelvisible property.
     * 
     */
    public void setLabelvisible(boolean value) {
        this.labelvisible = value;
    }

    /**
     * Gets the value of the labelposition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelposition() {
        return labelposition;
    }

    /**
     * Sets the value of the labelposition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelposition(String value) {
        this.labelposition = value;
    }

}
