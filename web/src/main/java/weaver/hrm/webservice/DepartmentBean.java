
package weaver.hrm.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DepartmentBean complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="DepartmentBean"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="_canceled" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="_departmentid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="_fullname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="_shortname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="_showorder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="_subcompanyid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="_supdepartmentid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lastChangdate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepartmentBean", propOrder = {
    "canceled",
    "code",
    "departmentid",
    "fullname",
    "shortname",
    "showorder",
    "subcompanyid",
    "supdepartmentid",
    "action",
    "lastChangdate"
})
public class DepartmentBean {

    @XmlElementRef(name = "_canceled", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> canceled;
    @XmlElementRef(name = "_code", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> code;
    @XmlElementRef(name = "_departmentid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> departmentid;
    @XmlElementRef(name = "_fullname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fullname;
    @XmlElementRef(name = "_shortname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> shortname;
    @XmlElementRef(name = "_showorder", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> showorder;
    @XmlElementRef(name = "_subcompanyid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> subcompanyid;
    @XmlElementRef(name = "_supdepartmentid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> supdepartmentid;
    @XmlElementRef(name = "action", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> action;
    @XmlElementRef(name = "lastChangdate", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastChangdate;

    /**
     * ��ȡcanceled���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCanceled() {
        return canceled;
    }

    /**
     * ����canceled���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCanceled(JAXBElement<String> value) {
        this.canceled = value;
    }

    /**
     * ��ȡcode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCode() {
        return code;
    }

    /**
     * ����code���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCode(JAXBElement<String> value) {
        this.code = value;
    }

    /**
     * ��ȡdepartmentid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDepartmentid() {
        return departmentid;
    }

    /**
     * ����departmentid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDepartmentid(JAXBElement<String> value) {
        this.departmentid = value;
    }

    /**
     * ��ȡfullname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFullname() {
        return fullname;
    }

    /**
     * ����fullname���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFullname(JAXBElement<String> value) {
        this.fullname = value;
    }

    /**
     * ��ȡshortname���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getShortname() {
        return shortname;
    }

    /**
     * ����shortname���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setShortname(JAXBElement<String> value) {
        this.shortname = value;
    }

    /**
     * ��ȡshoworder���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getShoworder() {
        return showorder;
    }

    /**
     * ����showorder���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setShoworder(JAXBElement<String> value) {
        this.showorder = value;
    }

    /**
     * ��ȡsubcompanyid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSubcompanyid() {
        return subcompanyid;
    }

    /**
     * ����subcompanyid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSubcompanyid(JAXBElement<String> value) {
        this.subcompanyid = value;
    }

    /**
     * ��ȡsupdepartmentid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSupdepartmentid() {
        return supdepartmentid;
    }

    /**
     * ����supdepartmentid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSupdepartmentid(JAXBElement<String> value) {
        this.supdepartmentid = value;
    }

    /**
     * ��ȡaction���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAction() {
        return action;
    }

    /**
     * ����action���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAction(JAXBElement<String> value) {
        this.action = value;
    }

    /**
     * ��ȡlastChangdate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLastChangdate() {
        return lastChangdate;
    }

    /**
     * ����lastChangdate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLastChangdate(JAXBElement<String> value) {
        this.lastChangdate = value;
    }

}
