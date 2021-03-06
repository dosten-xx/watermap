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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * Two lat/lon pairs defining the extent of an element.
 *
 *
 * <p>
 * Java class for boundsType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="boundsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="minlat" use="required" type="{http://www.topografix.com/GPX/1/1}latitudeType" />
 *       &lt;attribute name="minlon" use="required" type="{http://www.topografix.com/GPX/1/1}longitudeType" />
 *       &lt;attribute name="maxlat" use="required" type="{http://www.topografix.com/GPX/1/1}latitudeType" />
 *       &lt;attribute name="maxlon" use="required" type="{http://www.topografix.com/GPX/1/1}longitudeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "boundsType")
public class BoundsType
{

   @XmlAttribute(name = "minlat", required = true)
   protected BigDecimal minlat;
   @XmlAttribute(name = "minlon", required = true)
   protected BigDecimal minlon;
   @XmlAttribute(name = "maxlat", required = true)
   protected BigDecimal maxlat;
   @XmlAttribute(name = "maxlon", required = true)
   protected BigDecimal maxlon;

   /**
    * Gets the value of the maxlat property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getMaxlat()
   {
      return maxlat;
   }

   /**
    * Gets the value of the maxlon property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getMaxlon()
   {
      return maxlon;
   }

   /**
    * Gets the value of the minlat property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getMinlat()
   {
      return minlat;
   }

   /**
    * Gets the value of the minlon property.
    *
    * @return possible object is {@link BigDecimal }
    *
    */
   public BigDecimal getMinlon()
   {
      return minlon;
   }

   /**
    * Sets the value of the maxlat property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setMaxlat(BigDecimal value)
   {
      this.maxlat = value;
   }

   /**
    * Sets the value of the maxlon property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setMaxlon(BigDecimal value)
   {
      this.maxlon = value;
   }

   /**
    * Sets the value of the minlat property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setMinlat(BigDecimal value)
   {
      this.minlat = value;
   }

   /**
    * Sets the value of the minlon property.
    *
    * @param value allowed object is {@link BigDecimal }
    *
    */
   public void setMinlon(BigDecimal value)
   {
      this.minlon = value;
   }

}
