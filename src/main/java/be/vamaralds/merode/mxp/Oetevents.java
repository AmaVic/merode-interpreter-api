
package be.vamaralds.merode.mxp;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;



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
 *         &lt;element ref="{http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5}oetevent" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "oetevent"
})
@XmlRootElement(name = "oetevents", namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
public class Oetevents {

    @XmlElement(namespace = "http://merode.econ.kuleuven.ac.be/mermaid/mxp/1.5")
    protected List<Oetevent> oetevent;

    /**
     * Gets the value of the oetevent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the oetevent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOetevent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Oetevent }
     * 
     * 
     */
    public List<Oetevent> getOetevent() {
        if (oetevent == null) {
            oetevent = new ArrayList<Oetevent>();
        }
        return this.oetevent;
    }

}
