package org.gecko.playground.jaxb.spi;

import java.io.File;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.gecko.playground.jaxb.Address;
import org.gecko.playground.jaxb.Person;
import org.gecko.playground.jaxb.RequireJaXB;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@RequireJaXB
@Component
public class JaXBExample {

	@Activate
	public void activate() {
		Person result = new Person();
    	result.setFirstName("Emil");
    	result.setLastName("Tester");
    	result.setId("test");
    	Address a = new Address();
    	a.setStreet("Teststreet");
    	a.setCity("Jena");
    	a.setZIP("07745");
    	result.setAddress(Collections.singletonList(a));
		try {
			JAXBContext context = JAXBContext.newInstance(Person.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			File xmlFile = new File("testPersonJAXB.xml");
			marshaller.marshal(result, xmlFile);
			System.out.println("Saved file at testPersonJAXB.xml");
			result = (Person) context.createUnmarshaller().unmarshal(xmlFile);
			System.out.println("Loaded Person with name: " + result.getFirstName() + " " + result.getLastName());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
