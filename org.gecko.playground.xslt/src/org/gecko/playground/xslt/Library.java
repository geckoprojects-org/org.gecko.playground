package org.gecko.playground.xslt;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Library {

    private List<Book> books = new ArrayList<Book>();

    @XmlElement(name="book")
    public List<Book> getBooks() {
        return books;
    }

}