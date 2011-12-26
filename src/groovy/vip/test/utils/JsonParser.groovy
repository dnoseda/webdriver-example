package vip.test.utils

import java.io.StringReader
import net.sf.json.JSON
import net.sf.json.groovy.JsonSlurper

class JsonParser {

	def static JSON parse(jsonString) throws Exception {
		StringReader reader= new StringReader(jsonString);
		try {
			JSON jsonParsed= new JsonSlurper().parse(reader);
			return jsonParsed;
		} catch ( e) {
			throw new Exception("Error parsing ["+jsonString+"]", e);
		}
	}

}
