package org.gecko.playground.xslt;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.util.JAXBSource;
//import net.sf.saxon.BasicTransformerFactory;
//import net.sf.saxon.TransformerFactoryImpl;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.gecko.playground.jaxb.RequireJaXB;
import org.osgi.service.component.annotations.*;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;

@RequireJaXB
@Component
public class ExampleXSLT {
	
	private TransformerFactory tf;

	@Activate
	public void activate(BundleContext ctx) {
		System.out.println("Transform some book XML into HTML");
//		TransformerFactory tf = new TransformerFactoryImpl();
		Optional<Bundle> saxon = Arrays.stream(ctx.getBundles()).
				filter(b->"org.gecko.playground.saxon".equals(b.getSymbolicName())).
				findFirst();
		saxon.ifPresent(b->{
			BundleWiring wiring = b.adapt(BundleWiring.class);
			tf = TransformerFactory.newInstance("net.sf.saxon.BasicTransformerFactory", wiring.getClassLoader());
		});
		if (Objects.isNull(tf)) {
			tf = TransformerFactory.newInstance();
		}
		System.out.println("Factory: " + tf.getClass().getName());
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
			long start = System.currentTimeMillis();
			Transformer transformer = templates.newTransformer();

			// Source
			JAXBContext jc = JAXBContext.newInstance(Library.class);
			JAXBSource source = new JAXBSource(jc, catalog);

			// Result
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(baos);

			// Transform
			transformer.transform(source, result);
			start = System.currentTimeMillis() - start;
			System.out.println("Transformation resulted in: ");
			System.out.println(new String(baos.toByteArray()));
			System.out.println("1. Transformation took : " + start + " ms (Transformer warm-up)");
			for (int i = 0; i < 5; i++) {
				start = System.currentTimeMillis();
				transformer.transform(source, result);
				start = System.currentTimeMillis() - start;
				System.out.println((i + 2) + ". Transformation took : " + start + " ms");
				
			}
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
