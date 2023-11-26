
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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}datatypes" minOccurs="0"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metaobjects"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metaevents"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metaspecialisedevents"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metadependencies"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metainheritances"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metamethods"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="lastid" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datatypes",
    "metaobjects",
    "metaevents",
    "metaspecialisedevents",
    "metadependencies",
    "metainheritances",
    "metamethods"
})
@XmlRootElement(name = "metamodel", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Metamodel {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
    protected Datatypes datatypes;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metaobjects metaobjects;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metaevents metaevents;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metaspecialisedevents metaspecialisedevents;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metadependencies metadependencies;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metainheritances metainheritances;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metamethods metamethods;
    @XmlAttribute(name = "lastid", required = true)
    protected BigInteger lastid;

    /**
     * Gets the value of the datatypes property.
     * 
     * @return
     *     possible object is
     *     {@link Datatypes }
     *     
     */
    public Datatypes getDatatypes() {
        return datatypes;
    }

    /**
     * Sets the value of the datatypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Datatypes }
     *     
     */
    public void setDatatypes(Datatypes value) {
        this.datatypes = value;
    }

    /**
     * Gets the value of the metaobjects property.
     * 
     * @return
     *     possible object is
     *     {@link Metaobjects }
     *     
     */
    public Metaobjects getMetaobjects() {
        return metaobjects;
    }

    /**
     * Sets the value of the metaobjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metaobjects }
     *     
     */
    public void setMetaobjects(Metaobjects value) {
        this.metaobjects = value;
    }

    /**
     * Gets the value of the metaevents property.
     * 
     * @return
     *     possible object is
     *     {@link Metaevents }
     *     
     */
    public Metaevents getMetaevents() {
        return metaevents;
    }

    /**
     * Sets the value of the metaevents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metaevents }
     *     
     */
    public void setMetaevents(Metaevents value) {
        this.metaevents = value;
    }

    /**
     * Gets the value of the metaspecialisedevents property.
     * 
     * @return
     *     possible object is
     *     {@link Metaspecialisedevents }
     *     
     */
    public Metaspecialisedevents getMetaspecialisedevents() {
        return metaspecialisedevents;
    }

    /**
     * Sets the value of the metaspecialisedevents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metaspecialisedevents }
     *     
     */
    public void setMetaspecialisedevents(Metaspecialisedevents value) {
        this.metaspecialisedevents = value;
    }

    /**
     * Gets the value of the metadependencies property.
     * 
     * @return
     *     possible object is
     *     {@link Metadependencies }
     *     
     */
    public Metadependencies getMetadependencies() {
        return metadependencies;
    }

    /**
     * Sets the value of the metadependencies property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metadependencies }
     *     
     */
    public void setMetadependencies(Metadependencies value) {
        this.metadependencies = value;
    }

    /**
     * Gets the value of the metainheritances property.
     * 
     * @return
     *     possible object is
     *     {@link Metainheritances }
     *     
     */
    public Metainheritances getMetainheritances() {
        return metainheritances;
    }

    /**
     * Sets the value of the metainheritances property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metainheritances }
     *     
     */
    public void setMetainheritances(Metainheritances value) {
        this.metainheritances = value;
    }

    /**
     * Gets the value of the metamethods property.
     * 
     * @return
     *     possible object is
     *     {@link Metamethods }
     *     
     */
    public Metamethods getMetamethods() {
        return metamethods;
    }

    /**
     * Sets the value of the metamethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metamethods }
     *     
     */
    public void setMetamethods(Metamethods value) {
        this.metamethods = value;
    }

    /**
     * Gets the value of the lastid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLastid() {
        return lastid;
    }

    /**
     * Sets the value of the lastid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLastid(BigInteger value) {
        this.lastid = value;
    }

}
