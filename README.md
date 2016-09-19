# kAgent

[![GitHub version](https://badge.fury.io/gh/ChristianNHenriksen%2FkAgent@2x.png)](https://badge.fury.io/gh/ChristianNHenriksen%2FkAgent)

#### GET request
```Kotlin
val response = Client.get("http://localhost:8080/users").execute()

println(response.status)
println(response.text)
```

#### POST request
```Kotlin
val response = Client.post("http://localhost:8080/users")
    .body("{\"name\": \"Peter\"}")
    .contentType(ContentType.APPLICATION_JSON)
    .execute()

println(response.status)
println(response.text)
```

#### Header and Query params
```Kotlin
val response = Client.get("http://localhost:8080/users")
    .header("Authorization" to "password")
    .header("Accept" to "text/plain", "Accept-Charset" to "utf-8")
    .query("id" to "12345")
    .query("fields" to "name")
    .execute()

println(response.status)
println(response.text)
println(response.headers["name"])
```
