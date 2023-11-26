
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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guidescription"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guiobjects"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guidependencies"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guiinheritances"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guievents"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guiownedmethods"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guiacquiredmethods"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guiinheritedmethods"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guispecialisedmethods"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guifsms" minOccurs="0"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guistates" minOccurs="0"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}guitransitions" minOccurs="0"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}edgview"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}oetview"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}fsmview"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="isdefault" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "guidescription",
    "guiobjects",
    "guidependencies",
    "guiinheritances",
    "guievents",
    "guiownedmethods",
    "guiacquiredmethods",
    "guiinheritedmethods",
    "guispecialisedmethods",
    "guifsms",
    "guistates",
    "guitransitions",
    "edgview",
    "oetview",
    "fsmview"
})
@XmlRootElement(name = "view", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class View {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected String guidescription;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Guiobjects guiobjects;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Guidependencies guidependencies;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Guiinheritances guiinheritances;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Guievents guievents;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Guiownedmethods guiownedmethods;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Guiacquiredmethods guiacquiredmethods;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Guiinheritedmethods guiinheritedmethods;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Guispecialisedmethods guispecialisedmethods;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
    protected Guifsms guifsms;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
    protected Guistates guistates;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
    protected Guitransitions guitransitions;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Edgview edgview;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Oetview oetview;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Fsmview fsmview;
    @XmlAttribute(name = "id", required = true)
    protected BigInteger id;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "isdefault", required = true)
    protected boolean isdefault;

    /**
     * Gets the value of the guidescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuidescription() {
        return guidescription;
    }

    /**
     * Sets the value of the guidescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuidescription(String value) {
        this.guidescription = value;
    }

    /**
     * Gets the value of the guiobjects property.
     * 
     * @return
     *     possible object is
     *     {@link Guiobjects }
     *     
     */
    public Guiobjects getGuiobjects() {
        return guiobjects;
    }

    /**
     * Sets the value of the guiobjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guiobjects }
     *     
     */
    public void setGuiobjects(Guiobjects value) {
        this.guiobjects = value;
    }

    /**
     * Gets the value of the guidependencies property.
     * 
     * @return
     *     possible object is
     *     {@link Guidependencies }
     *     
     */
    public Guidependencies getGuidependencies() {
        return guidependencies;
    }

    /**
     * Sets the value of the guidependencies property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guidependencies }
     *     
     */
    public void setGuidependencies(Guidependencies value) {
        this.guidependencies = value;
    }

    /**
     * Gets the value of the guiinheritances property.
     * 
     * @return
     *     possible object is
     *     {@link Guiinheritances }
     *     
     */
    public Guiinheritances getGuiinheritances() {
        return guiinheritances;
    }

    /**
     * Sets the value of the guiinheritances property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guiinheritances }
     *     
     */
    public void setGuiinheritances(Guiinheritances value) {
        this.guiinheritances = value;
    }

    /**
     * Gets the value of the guievents property.
     * 
     * @return
     *     possible object is
     *     {@link Guievents }
     *     
     */
    public Guievents getGuievents() {
        return guievents;
    }

    /**
     * Sets the value of the guievents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guievents }
     *     
     */
    public void setGuievents(Guievents value) {
        this.guievents = value;
    }

    /**
     * Gets the value of the guiownedmethods property.
     * 
     * @return
     *     possible object is
     *     {@link Guiownedmethods }
     *     
     */
    public Guiownedmethods getGuiownedmethods() {
        return guiownedmethods;
    }

    /**
     * Sets the value of the guiownedmethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guiownedmethods }
     *     
     */
    public void setGuiownedmethods(Guiownedmethods value) {
        this.guiownedmethods = value;
    }

    /**
     * Gets the value of the guiacquiredmethods property.
     * 
     * @return
     *     possible object is
     *     {@link Guiacquiredmethods }
     *     
     */
    public Guiacquiredmethods getGuiacquiredmethods() {
        return guiacquiredmethods;
    }

    /**
     * Sets the value of the guiacquiredmethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guiacquiredmethods }
     *     
     */
    public void setGuiacquiredmethods(Guiacquiredmethods value) {
        this.guiacquiredmethods = value;
    }

    /**
     * Gets the value of the guiinheritedmethods property.
     * 
     * @return
     *     possible object is
     *     {@link Guiinheritedmethods }
     *     
     */
    public Guiinheritedmethods getGuiinheritedmethods() {
        return guiinheritedmethods;
    }

    /**
     * Sets the value of the guiinheritedmethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guiinheritedmethods }
     *     
     */
    public void setGuiinheritedmethods(Guiinheritedmethods value) {
        this.guiinheritedmethods = value;
    }

    /**
     * Gets the value of the guispecialisedmethods property.
     * 
     * @return
     *     possible object is
     *     {@link Guispecialisedmethods }
     *     
     */
    public Guispecialisedmethods getGuispecialisedmethods() {
        return guispecialisedmethods;
    }

    /**
     * Sets the value of the guispecialisedmethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guispecialisedmethods }
     *     
     */
    public void setGuispecialisedmethods(Guispecialisedmethods value) {
        this.guispecialisedmethods = value;
    }

    /**
     * Gets the value of the guifsms property.
     * 
     * @return
     *     possible object is
     *     {@link Guifsms }
     *     
     */
    public Guifsms getGuifsms() {
        return guifsms;
    }

    /**
     * Sets the value of the guifsms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guifsms }
     *     
     */
    public void setGuifsms(Guifsms value) {
        this.guifsms = value;
    }

    /**
     * Gets the value of the guistates property.
     * 
     * @return
     *     possible object is
     *     {@link Guistates }
     *     
     */
    public Guistates getGuistates() {
        return guistates;
    }

    /**
     * Sets the value of the guistates property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guistates }
     *     
     */
    public void setGuistates(Guistates value) {
        this.guistates = value;
    }

    /**
     * Gets the value of the guitransitions property.
     * 
     * @return
     *     possible object is
     *     {@link Guitransitions }
     *     
     */
    public Guitransitions getGuitransitions() {
        return guitransitions;
    }

    /**
     * Sets the value of the guitransitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Guitransitions }
     *     
     */
    public void setGuitransitions(Guitransitions value) {
        this.guitransitions = value;
    }

    /**
     * Gets the value of the edgview property.
     * 
     * @return
     *     possible object is
     *     {@link Edgview }
     *     
     */
    public Edgview getEdgview() {
        return edgview;
    }

    /**
     * Sets the value of the edgview property.
     * 
     * @param value
     *     allowed object is
     *     {@link Edgview }
     *     
     */
    public void setEdgview(Edgview value) {
        this.edgview = value;
    }

    /**
     * Gets the value of the oetview property.
     * 
     * @return
     *     possible object is
     *     {@link Oetview }
     *     
     */
    public Oetview getOetview() {
        return oetview;
    }

    /**
     * Sets the value of the oetview property.
     * 
     * @param value
     *     allowed object is
     *     {@link Oetview }
     *     
     */
    public void setOetview(Oetview value) {
        this.oetview = value;
    }

    /**
     * Gets the value of the fsmview property.
     * 
     * @return
     *     possible object is
     *     {@link Fsmview }
     *     
     */
    public Fsmview getFsmview() {
        return fsmview;
    }

    /**
     * Sets the value of the fsmview property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fsmview }
     *     
     */
    public void setFsmview(Fsmview value) {
        this.fsmview = value;
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
     * Gets the value of the isdefault property.
     * 
     */
    public boolean isIsdefault() {
        return isdefault;
    }

    /**
     * Sets the value of the isdefault property.
     * 
     */
    public void setIsdefault(boolean value) {
        this.isdefault = value;
    }

}
