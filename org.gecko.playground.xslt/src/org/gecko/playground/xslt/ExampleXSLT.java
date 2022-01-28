package org.gecko.playground.xslt;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.gecko.playground.jaxb.RequireJaXB;
import org.osgi.service.component.annotations.*;

@RequireJaXB
@Component
public class ExampleXSLT {

	@Activate
	public void activate() {
		System.out.println("Transform some book XML into HTML");
		TransformerFactory tf = TransformerFactory.newInstance();
		StreamSource xslt = new StreamSource("xsl/toHTML.xsl");
		try {
			Templates templates = tf.newTemplates(xslt);
			// XML Data
			Book book1 = new Book();
			book1.setAuthor("Jane Doe");
			book1.setTitle("Some Book");

			Book book2 = new Book();
			book2.setAuthor("John Smith");
			book2.setTitle("Another Novel");

			Library catalog = new Library();
			catalog.getBooks().add(book1);
			catalog.getBooks().add(book2);

			JAXBContext jaxbContext = JAXBContext.newInstance(Library.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(catalog, sw);
			System.out.println("Source XML: ");
			System.out.println(sw.toString());
			// Create Transformer
			Transformer transformer = templates.newTransformer();

			// Source
			JAXBContext jc = JAXBContext.newInstance(Library.class);
			JAXBSource source = new JAXBSource(jc, catalog);

			// Result
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(baos);

			// Transform
			transformer.transform(source, result);
			System.out.println("Transformation resulted in: ");
			System.out.println(new String(baos.toByteArray()));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
