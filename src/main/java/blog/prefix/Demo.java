package blog.prefix;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

public class Demo {

	public static void main(String[] args) throws Exception {
		JAXBContext ctx = JAXBContext.newInstance(Root.class);

		Root root = new Root();
		root.setA("A");
		root.setB("B");
		root.setC("OTHER");

		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		try {
			m.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new MyNamespaceMapper());
			// m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new
			// MyNamespaceMapper());
		} catch (PropertyException e) {
			// In case another JAXB implementation is used
		}
		m.marshal(root, System.out);

	}
}