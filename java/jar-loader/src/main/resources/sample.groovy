import com.asiafrank.jarloader.TestObj
import com.fasterxml.jackson.databind.ObjectMapper

void scriptMain() {
    println "groovy invoked!"
    ObjectMapper mapper = new ObjectMapper()
    TestObj obj = mapper.readValue("{\"id\": 10, \"name\": \"test obj\"}", TestObj.class)

    println obj
}