class UrlMappings {

	static mappings = {

		"/"(controller: "tsp")
		"/load/$tspName"(controller: "tsp", action: "load")
		"/solve/$tspName"(controller: "tsp", action: "solve")
	}
}
