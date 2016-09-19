import Method.*
import org.apache.http.HttpEntity
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by Christian on 18/09/2016.
 */

class RequestChain(val url: String, val method: Method) {
    private val client = HttpClientBuilder.create().build()

    val headers = mutableMapOf<String, String>()
    val queries = mutableMapOf<String, String>()
    var body: String = ""
    var contentType = ContentType.DEFAULT_TEXT

    fun header(vararg headers: Pair<String, String>): RequestChain {
        headers.forEach { this.headers[it.first] = it.second }
        return this
    }

    fun query(vararg queries: Pair<String, String>): RequestChain {
        queries.forEach { this.queries[it.first] = it.second }
        return this
    }

    fun body(body: String): RequestChain {
        this.body = body
        return this
    }

    fun contentType(contentType: ContentType): RequestChain {
        this.contentType = contentType
        return this
    }

    fun execute(): Response {
        val request = buildRequest()
        val response = client.execute(request)
        return parseResponse(response)
    }

    private fun buildRequest(): HttpUriRequest? {
        val request = requestBuilder

        request.entity = buildEntity()
        headers.forEach { request.addHeader(it.key, it.value) }
        queries.forEach { request.addParameter(it.key, it.value) }

        return request.build()
    }

    private fun buildEntity(): HttpEntity? {
        return StringEntity(body, contentType)
    }

    private fun parseResponse(response: CloseableHttpResponse): Response {
        val body = parseBody(response)
        val status = parseStatus(response)
        val headers = parseHeaders(response)
        val contentType = parseContentType(response)

        return Response(status, body, headers, contentType)
    }

    private fun parseBody(response: CloseableHttpResponse): String {
        val reader = BufferedReader(InputStreamReader(response.entity.content))
        return reader.readText()
    }

    private fun parseStatus(response: CloseableHttpResponse): Int {
        return response.statusLine.statusCode
    }

    private fun parseHeaders(response: CloseableHttpResponse): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        response.allHeaders.forEach { headers[it.name] = it.value }
        return headers
    }

    private fun parseContentType(response: CloseableHttpResponse): String? {
        if (response.entity.contentType == null) return null

        return response.entity.contentType.value
    }

    private val requestBuilder: RequestBuilder = when (method) {
        Get -> RequestBuilder.get(url)
        Post -> RequestBuilder.post(url)
        Put -> RequestBuilder.put(url)
        Delete -> RequestBuilder.delete(url)
    }
}

enum class Method {
    Get, Post, Put, Delete
}
