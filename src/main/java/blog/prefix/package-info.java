@XmlSchema(
    elementFormDefault=XmlNsForm.QUALIFIED,
    namespace="http://www.example.com/FOO",
    xmlns={@XmlNs(prefix="bar",
                  namespaceURI="http://www.example.com/BAR")}
)
package blog.prefix;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;