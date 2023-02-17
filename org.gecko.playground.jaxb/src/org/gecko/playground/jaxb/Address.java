package org.gecko.playground.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Address {

    @XmlElement(name = "ETag")
    private String etag;

    @XmlElement(name = "street")
    private String street;
    
    @XmlElement(name = "city")
    private String city;

    @XmlElement(name = "zip")
    private String zip;

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZIP() {
        return zip;
    }

    public void setZIP(String zip) {
        this.zip = zip;
    }

}
