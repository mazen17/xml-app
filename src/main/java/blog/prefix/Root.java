package blog.prefix;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.example.com/FOO")
public class Root {

	private String a;
	private String b;
	private String c;

	@XmlElement(namespace = "http://www.example.com/BAR")
	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	@XmlElement(namespace = "http://www.example.com/FOO")
	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	@XmlElement(namespace = "http://www.example.com/OTHER")
	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

}
