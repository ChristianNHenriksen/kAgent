import org.apache.http.entity.ContentType
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockserver.integration.ClientAndProxy
import org.mockserver.integration.ClientAndProxy.startClientAndProxy
import org.mockserver.integration.ClientAndServer
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.Parameter
import kotlin.test.assertEquals

/**
 * Created by Christian on 19/09/2016.
 */
class ClientTest {
    var server: ClientAndServer? = null
    var proxy: ClientAndProxy? = null

    @Before
    fun setUp() {
        server = startClientAndServer(1080)
        proxy = startClientAndProxy(1090)
    }

    @Test
    fun shouldExecuteGetRequest() {
        server?.`when`(
            request()
                .withMethod("GET")
                .withPath("/get")

        )?.respond(response().withBody("get").withStatusCode(200))

        val response = Client.get("http://localhost:1080/get").execute()
        assertEquals("get", response.text)
    }

    @Test
    fun shouldExecutePostRequest() {
        server?.`when`(
                request()
                        .withMethod("POST")
                        .withPath("/post")

        )?.respond(response().withBody("post").withStatusCode(200))

        val response = Client.post("http://localhost:1080/post").execute()
        assertEquals("post", response.text)
    }

    @Test
    fun shouldExecutePutRequest() {
        server?.`when`(
                request()
                        .withMethod("PUT")
                        .withPath("/put")

        )?.respond(response().withBody("put").withStatusCode(200))

        val response = Client.put("http://localhost:1080/put").execute()
        assertEquals("put", response.text)
    }

    @Test
    fun shouldExecuteDeleteRequest() {
        server?.`when`(
                request()
                        .withMethod("DELETE")
                        .withPath("/delete")

        )?.respond(response().withBody("delete").withStatusCode(200))

        val response = Client.delete("http://localhost:1080/delete").execute()
        assertEquals("delete", response.text)
    }


    @Test
    fun shouldParseStatusCode() {
        server?.`when`(
                request()
                        .withMethod("GET")
                        .withPath("/status")

        )?.respond(response().withStatusCode(201))

        val response = Client.get("http://localhost:1080/status").execute()
        assertEquals(201, response.status)
    }

    @Test
    fun shouldParseHeaders() {
        server?.`when`(
                request()
                        .withMethod("GET")
                        .withPath("/headers")

        )?.respond(response().withHeaders(
                Header("name", "Peter"),
                Header("age", "31")
        ))

        val response = Client.get("http://localhost:1080/headers").execute()
        assertEquals("Peter", response.headers["name"])
        assertEquals("31", response.headers["age"])
    }

    @Test
    fun shouldSendBody() {
        server?.`when`(
                request()
                        .withMethod("POST")
                        .withPath("/body")
                        .withBody("Peter")

        )?.respond(response().withBody("body"))

        val response = Client.post("http://localhost:1080/body").body("Peter").execute()
        assertEquals("body", response.text)
    }

    @Test
    fun shouldSendHeaders() {
        server?.`when`(
                request()
                        .withMethod("GET")
                        .withPath("/headers")
                        .withHeaders(Header("name", "Peter"), Header("age", "31"))

        )?.respond(response().withBody("headers"))

        val response = Client.get("http://localhost:1080/headers").header("name" to "Peter", "age" to "31").execute()
        assertEquals("headers", response.text)
    }

    @Test
    fun shouldSendQueryParams() {
        server?.`when`(
                request()
                        .withMethod("GET")
                        .withPath("/queries")
                        .withQueryStringParameters(Parameter("name", "Peter"), Parameter("age", "31"))

        )?.respond(response().withBody("queries"))

        val response = Client.get("http://localhost:1080/queries").query("name" to "Peter").query("age" to "31").execute()
        assertEquals("queries", response.text)
    }

    @Test
    fun shouldUseContentType() {
        server?.`when`(
                request()
                        .withMethod("POST")
                        .withPath("/type")
                        .withBody("Peter")
                        .withHeader("Content-Type", "application/json; charset=UTF-8")

        )?.respond(response().withBody("type"))

        val response = Client.post("http://localhost:1080/type").body("Peter").contentType(ContentType.APPLICATION_JSON).execute()
        assertEquals("type", response.text)
    }

    @After
    fun tearDown() {
        server?.stop()
        proxy?.stop()
    }
}