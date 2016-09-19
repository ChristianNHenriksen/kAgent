import Method.*

/**
 * Created by Christian on 18/09/2016.
 */
object Client {
    fun get(url: String): RequestChain = RequestChain(url, Get)

    fun post(url: String): RequestChain = RequestChain(url, Post)

    fun put(url: String): RequestChain = RequestChain(url, Put)

    fun delete(url: String): RequestChain = RequestChain(url, Delete)
}