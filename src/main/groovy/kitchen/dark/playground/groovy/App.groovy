package kitchen.dark.playground.groovy

import io.javalin.Javalin
import org.slf4j.LoggerFactory

import kitchen.dark.playground.kotlin.Message

class App {
    def final private static logger = LoggerFactory.getLogger(App)

    static void main(String... args) {
        def app = Javalin.create()
                .disableStartupBanner()
                .contextPath(System.getenv('CONTEXT_ROOT') ?: '/')
                .disableDynamicGzip()

        app.requestLogger({ ctx, timeMs ->
            logger.info("${ctx.method()} ${ctx.path()} returns ${ctx.status()} in ${timeMs}ms")
        })
        app.get("/hello", { ctx -> ctx.json(new Message('Hello.')) })

        def port = System.getenv('PORT')?.toInteger() ?: 8888
        app.start(port)
        logger.info("listening on port $port")
    }
}
