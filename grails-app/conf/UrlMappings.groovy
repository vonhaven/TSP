class UrlMappings {

	static mappings = {

		"/"(controller: "tsp")
        "/reset"(controller: "tsp", action: "reset")
		"/loadSet/$tspSet"(controller: "tsp", action: "loadSet")
		"/load/$tspId"(controller: "tsp", action: "load")
		"/solve/$tspId"(controller: "tsp", action: "solve")
		"/getDefaultPath/$tspId"(controller: "tsp", action: "getDefaultPath")
	}
}
