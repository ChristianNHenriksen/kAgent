import org.apache.http.entity.ContentType

/**
 * Created by Christian on 19/09/2016.
 */

fun main(args: Array<String>) {
    val response = Client.get("http://localhost:8080/users").execute()

    println(response.status)
    println(response.text)
}

fun getExample() {
    val response = Client.get("http://localhost:8080/users").execute()

    println(response.status)
    println(response.text)
}

fun postExample() {
    val response = Client.post("http://localhost:8080/users")
            .body("{\"name\": \"Peter\"}")
            .contentType(ContentType.APPLICATION_JSON)
            .execute()

    println(response.status)
    println(response.text)
}

fun headerAnQueryExample() {
    val response = Client.get("http://localhost:8080/users")
            .header("Authorization" to "password")
            .header("Accept" to "text/plain", "Accept-Charset" to "utf-8")
            .query("id" to "12345")
            .query("fields" to "name")
            .execute()

    println(response.status)
    println(response.text)
    println(response.headers["name"])
}