import Method.Get
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Christian on 18/09/2016.
 */
class RequestChainTest {
    @Test
    fun shouldAddOneHeader() {
        val requestChain = RequestChain("url", Get)
            .header("name" to "Peter")

        assertEquals(1, requestChain.headers.size)
        assertEquals("Peter", requestChain.headers["name"])
    }

    @Test
    fun shouldAddTwoHeaders() {
        val requestChain = RequestChain("url", Get)
            .header("name" to "Peter")
            .header("age" to "25")

        assertEquals(2, requestChain.headers.size)
        assertEquals("Peter", requestChain.headers["name"])
        assertEquals("25", requestChain.headers["age"])
    }

    @Test
    fun shouldAddOneQueryParam() {
        val requestChain = RequestChain("url", Get)
            .query("name" to "Peter")

        assertEquals(1, requestChain.queries.size)
        assertEquals("Peter", requestChain.queries["name"])
    }

    @Test
    fun shouldAddTwoQueryParam() {
        val requestChain = RequestChain("url", Get)
            .query("name" to "Peter")
            .query("age" to "25")

        assertEquals(2, requestChain.queries.size)
        assertEquals("Peter", requestChain.queries["name"])
        assertEquals("25", requestChain.queries["age"])
    }
}