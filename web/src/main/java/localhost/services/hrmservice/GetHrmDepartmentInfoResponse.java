
package localhost.services.hrmservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import weaver.hrm.webservice.ArrayOfDepartmentBean;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="out" type="{http://webservice.hrm.weaver}ArrayOfDepartmentBean"/&gt;
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
    "out"
})
@XmlRootElement(name = "getHrmDepartmentInfoResponse")
public class GetHrmDepartmentInfoResponse {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfDepartmentBean out;

    /**
     * ��ȡout���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDepartmentBean }
     *     
     */
    public ArrayOfDepartmentBean getOut() {
        return out;
    }

    /**
     * ����out���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDepartmentBean }
     *     
     */
    public void setOut(ArrayOfDepartmentBean value) {
        this.out = value;
    }

}
