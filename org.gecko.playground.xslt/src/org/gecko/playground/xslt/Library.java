package org.gecko.playground.xslt;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Library {

    private List<Book> books = new ArrayList<Book>();

    @XmlElement(name="book")
    public List<Book> getBooks() {
        return books;
    }

}