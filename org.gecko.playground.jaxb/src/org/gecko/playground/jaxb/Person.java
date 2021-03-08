package org.gecko.playground.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PersonList", namespace = "http://playground/test/")
public class Person {

    @XmlElement(name = "address")
    private List<Address> contents = new ArrayList<>();

    @XmlElement(name = "fn")
    private String firstName;
    
    @XmlElement(name = "ln")
    private String lastName;

    @XmlElement(name = "id")
    private String id;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }
    
    public String getLastName() {
    	return lastName;
    }
    
    public void setLastName(String name) {
    	this.lastName = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Address> getAddress() {
        return contents;
    }

    public void setAddress(List<Address> contents) {
        this.contents = contents;
    }


}
