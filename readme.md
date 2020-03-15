# Spring boot starter with JWT request filter and JWT REST template

## JWT request filters

* [DZione JWT spring](https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world)
* [java in use](https://medium.com/swlh/spring-boot-security-jwt-hello-world-example-b479e457664c)
* [JWT and roles](https://medium.com/@hantsy/protect-rest-apis-with-spring-security-and-jwt-5fbc90305cc5)
* [JWT resttemplate](https://www.kingsware.de/2019/07/20/spring-boot-passthrough-jwt-with-resttemplate/)
* [testtemplate interceptor](https://stackoverflow.com/questions/46729203/propagate-http-header-jwt-token-over-services-using-spring-rest-template)
* [conditional beans](https://reflectoring.io/spring-boot-conditionals/)
* [conditional logging stackoverflow](https://stackoverflow.com/questions/29429073/spring-boot-logback-and-logging-config-property/29430074)
* [logback conditional logging](http://logback.qos.ch/manual/configuration.html#conditional)
* [Requires Janino](http://logback.qos.ch/setup.html#janino)

 think it is better to add the interceptor specifically to the RestTemplate, like this:
 
 class RestTemplateHeaderModifierInterceptor(private val authenticationService: IAuthenticationService) : ClientHttpRequestInterceptor {
    override fun intercept(request: org.springframework.http.HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        if (!request.headers.containsKey("Authorization")) {
            // don't overwrite, just add if not there.
            val jwt = authenticationService.getCurrentUser()!!.jwt
            request.headers.add("Authorization", "Bearer $jwt")
        }
        val response = execution.execute(request, body)
        return response
    }
}

And add it to the RestTemplate like so:

@Bean
fun restTemplate(): RestTemplate {
    val restTemplate = RestTemplate()
restTemplate.interceptors.add(RestTemplateHeaderModifierInterceptor(authenticationService)) // add interceptor to send JWT along with requests.
    return restTemplate
}


That way, every time you need a RestTemplate you can just use autowiring to get it. You do need to implement the AuthenticationService still to get the token from the TokenStore, like this:


val details = SecurityContextHolder.getContext().authentication.details
if (details is OAuth2AuthenticationDetails) {
   val token = tokenStore.readAccessToken(details.tokenValue)
   return token.value
}

