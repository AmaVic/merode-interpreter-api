
package be.vamaralds.merode.mxp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metadescription"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metaconstraints"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metamultiplepropagationconstraints" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metaattributes"/&gt;
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}metafsms"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" /&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="abstract" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metadescription",
    "metaconstraints",
    "metamultiplepropagationconstraints",
    "metaattributes",
    "metafsms"
})
@XmlRootElement(name = "metaobject", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Metaobject {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected String metadescription;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected String metaconstraints;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
    protected List<Metamultiplepropagationconstraints> metamultiplepropagationconstraints;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metaattributes metaattributes;
    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5", required = true)
    protected Metafsms metafsms;
    @XmlAttribute(name = "id", required = true)
    protected BigInteger id;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "abstract", required = true)
    protected boolean _abstract;

    /**
     * Gets the value of the metadescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetadescription() {
        return metadescription;
    }

    /**
     * Sets the value of the metadescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetadescription(String value) {
        this.metadescription = value;
    }

    /**
     * Gets the value of the metaconstraints property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetaconstraints() {
        return metaconstraints;
    }

    /**
     * Sets the value of the metaconstraints property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetaconstraints(String value) {
        this.metaconstraints = value;
    }

    /**
     * Gets the value of the metamultiplepropagationconstraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the metamultiplepropagationconstraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetamultiplepropagationconstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Metamultiplepropagationconstraints }
     * 
     * 
     */
    public List<Metamultiplepropagationconstraints> getMetamultiplepropagationconstraints() {
        if (metamultiplepropagationconstraints == null) {
            metamultiplepropagationconstraints = new ArrayList<Metamultiplepropagationconstraints>();
        }
        return this.metamultiplepropagationconstraints;
    }

    /**
     * Gets the value of the metaattributes property.
     * 
     * @return
     *     possible object is
     *     {@link Metaattributes }
     *     
     */
    public Metaattributes getMetaattributes() {
        return metaattributes;
    }

    /**
     * Sets the value of the metaattributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metaattributes }
     *     
     */
    public void setMetaattributes(Metaattributes value) {
        this.metaattributes = value;
    }

    /**
     * Gets the value of the metafsms property.
     * 
     * @return
     *     possible object is
     *     {@link Metafsms }
     *     
     */
    public Metafsms getMetafsms() {
        return metafsms;
    }

    /**
     * Sets the value of the metafsms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Metafsms }
     *     
     */
    public void setMetafsms(Metafsms value) {
        this.metafsms = value;
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
     * Gets the value of the abstract property.
     * 
     */
    public boolean isAbstract() {
        return _abstract;
    }

    /**
     * Sets the value of the abstract property.
     * 
     */
    public void setAbstract(boolean value) {
        this._abstract = value;
    }

}
