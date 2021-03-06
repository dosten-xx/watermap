/*
The MIT License (MIT)
Copyright (c) 2015 Darren Osten
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.03.05 at 06:45:28 PM PST
//

package net.osten.watermap.pct.xml;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * wpt represents a waypoint, point of interest, or named feature on a map.
 *
 *
 * <p>
 * Java class for wptType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="wptType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ele" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="magvar" type="{http://www.topografix.com/GPX/1/1}degreesType" minOccurs="0"/>
 *         &lt;element name="geoidheight" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cmt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="desc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="src" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="link" type="{http://www.topografix.com/GPX/1/1}linkType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sym" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fix" type="{http://www.topografix.com/GPX/1/1}fixType" minOccurs="0"/>
 *         &lt;element name="sat" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="hdop" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="vdop" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="pdop" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="ageofdgpsdata" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="dgpsid" type="{http://www.topografix.com/GPX/1/1}dgpsStationType" minOccurs="0"/>
 *         &lt;element name="extensions" type="{http://www.topografix.com/GPX/1/1}extensionsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lat" use="required" type="{http://www.topografix.com/GPX/1/1}latitudeType" />
 *       &lt;attribute name="lon" use="required" type="{http://www.topografix.com/GPX/1/1}longitudeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wptType", propOrder = { "ele", "time", "magvar", "geoidheight", "name", "cmt", "desc", "src", "link", "sym", "type", "fix", "sat", "hdop", "vdop", "pdop", "ageofdgpsdata", "dgpsid",
"extensions" })
public class WptType
{

   protected BigDecimal ele;
   @XmlSchemaType(name = "dateTime")
   protected XMLGregorianCalendar time;
   protected BigDecimal magvar;
   protected BigDecimal geoidheight;
   protected String name;
   protected String cmt;
   protected String desc;
   protected String src;
   protected List<LinkType> link;
   protected String sym;
   protected String type;
   protected String fix;
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger sat;
   protected BigDecimal hdop;
   protected BigDecimal vdop;
   protected BigDecimal pdop;
   protected BigDecimal ageofdgpsdata;
   protected Integer dgpsid;
   protected ExtensionsType extensions;
   @XmlAttribute(name = "lat", required = true)
   protected BigDecimal lat;
   @XmlAttribute(name = "lon", required = true)
   protected BigDecimal lon;

   /**
    * Gets the value of the ageofdgpsdata property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getAgeofdgpsdata()
   {
      return ageofdgpsdata;
   }

   /**
    * Gets the value of the cmt property.
    *
    * @return possible object is {@link String }
    *
    */
   public String getCmt()
   {
      return cmt;
   }

   /**
    * Gets the value of the desc property.
    *
    * @return possible object is {@link String }
    *
    */
   public String getDesc()
   {
      return desc;
   }

   /**
    * Gets the value of the dgpsid property.
    *
    * @return possible object is {@link Integer }
    *
    */
   public Integer getDgpsid()
   {
      return dgpsid;
   }

   /**
    * Gets the value of the ele property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getEle()
   {
      return ele;
   }

   /**
    * Gets the value of the extensions property.
    *
    * @return possible object is {@link ExtensionsType }
    *
    */
   public ExtensionsType getExtensions()
   {
      return extensions;
   }

   /**
    * Gets the value of the fix property.
    *
    * @return possible object is {@link String }
    *
    */
   public String getFix()
   {
      return fix;
   }

   /**
    * Gets the value of the geoidheight property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getGeoidheight()
   {
      return geoidheight;
   }

   /**
    * Gets the value of the hdop property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getHdop()
   {
      return hdop;
   }

   /**
    * Gets the value of the lat property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getLat()
   {
      return lat;
   }

   /**
    * Gets the value of the link property.
    *
    * <p>
    * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is
    * not a <CODE>set</CODE> method for the link property.
    *
    * <p>
    * For example, to add a new item, do as follows:
    *
    * <pre>
    * getLink().add(newItem);
    * </pre>
    *
    *
    * <p>
    * Objects of the following type(s) are allowed in the list {@link LinkType }
    *
    *
    */
   public List<LinkType> getLink()
   {
      if (link == null) {
         link = new ArrayList<LinkType>();
      }
      return this.link;
   }

   /**
    * Gets the value of the lon property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getLon()
   {
      return lon;
   }

   /**
    * Gets the value of the magvar property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getMagvar()
   {
      return magvar;
   }

   /**
    * Gets the value of the name property.
    *
    * @return possible object is {@link String }
    *
    */
   public String getName()
   {
      return name;
   }

   /**
    * Gets the value of the pdop property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getPdop()
   {
      return pdop;
   }

   /**
    * Gets the value of the sat property.
    *
    * @return possible object is {@link BigInteger }
    *
    */
   public BigInteger getSat()
   {
      return sat;
   }

   /**
    * Gets the value of the src property.
    *
    * @return possible object is {@link String }
    *
    */
   public String getSrc()
   {
      return src;
   }

   /**
    * Gets the value of the sym property.
    *
    * @return possible object is {@link String }
    *
    */
   public String getSym()
   {
      return sym;
   }

   /**
    * Gets the value of the time property.
    *
    * @return possible object is {@link XMLGregorianCalendar }
    *
    */
   public XMLGregorianCalendar getTime()
   {
      return time;
   }

   /**
    * Gets the value of the type property.
    *
    * @return possible object is {@link String }
    *
    */
   public String getType()
   {
      return type;
   }

   /**
    * Gets the value of the vdop property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getVdop()
   {
      return vdop;
   }

   /**
    * Sets the value of the ageofdgpsdata property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setAgeofdgpsdata(BigDecimal value)
   {
      this.ageofdgpsdata = value;
   }

   /**
    * Sets the value of the cmt property.
    *
    * @param value allowed object is {@link String }
    *
    */
   public void setCmt(String value)
   {
      this.cmt = value;
   }

   /**
    * Sets the value of the desc property.
    *
    * @param value allowed object is {@link String }
    *
    */
   public void setDesc(String value)
   {
      this.desc = value;
   }

   /**
    * Sets the value of the dgpsid property.
    *
    * @param value allowed object is {@link Integer }
    *
    */
   public void setDgpsid(Integer value)
   {
      this.dgpsid = value;
   }

   /**
    * Sets the value of the ele property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setEle(BigDecimal value)
   {
      this.ele = value;
   }

   /**
    * Sets the value of the extensions property.
    *
    * @param value allowed object is {@link ExtensionsType }
    *
    */
   public void setExtensions(ExtensionsType value)
   {
      this.extensions = value;
   }

   /**
    * Sets the value of the fix property.
    *
    * @param value allowed object is {@link String }
    *
    */
   public void setFix(String value)
   {
      this.fix = value;
   }

   /**
    * Sets the value of the geoidheight property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setGeoidheight(BigDecimal value)
   {
      this.geoidheight = value;
   }

   /**
    * Sets the value of the hdop property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setHdop(BigDecimal value)
   {
      this.hdop = value;
   }

   /**
    * Sets the value of the lat property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setLat(BigDecimal value)
   {
      this.lat = value;
   }

   /**
    * Sets the value of the lon property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setLon(BigDecimal value)
   {
      this.lon = value;
   }

   /**
    * Sets the value of the magvar property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setMagvar(BigDecimal value)
   {
      this.magvar = value;
   }

   /**
    * Sets the value of the name property.
    *
    * @param value allowed object is {@link String }
    *
    */
   public void setName(String value)
   {
      this.name = value;
   }

   /**
    * Sets the value of the pdop property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setPdop(BigDecimal value)
   {
      this.pdop = value;
   }

   /**
    * Sets the value of the sat property.
    *
    * @param value allowed object is {@link BigInteger }
    *
    */
   public void setSat(BigInteger value)
   {
      this.sat = value;
   }

   /**
    * Sets the value of the src property.
    *
    * @param value allowed object is {@link String }
    *
    */
   public void setSrc(String value)
   {
      this.src = value;
   }

   /**
    * Sets the value of the sym property.
    *
    * @param value allowed object is {@link String }
    *
    */
   public void setSym(String value)
   {
      this.sym = value;
   }

   /**
    * Sets the value of the time property.
    *
    * @param value allowed object is {@link XMLGregorianCalendar }
    *
    */
   public void setTime(XMLGregorianCalendar value)
   {
      this.time = value;
   }

   /**
    * Sets the value of the type property.
    *
    * @param value allowed object is {@link String }
    *
    */
   public void setType(String value)
   {
      this.type = value;
   }

   /**
    * Sets the value of the vdop property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setVdop(BigDecimal value)
   {
      this.vdop = value;
   }

}
