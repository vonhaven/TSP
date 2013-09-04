import tsp.*

class BootStrap {

    def problemService

    def init = { servletContext ->
        problemService.construct()
    }

    def destroy = {
    }
}
