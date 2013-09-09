class UrlMappings {

	static mappings = {

		"/"(controller: "tsp")
        "/reset"(controller: "tsp", action: "reset")
		"/load/$tspName"(controller: "tsp", action: "load")
		"/getDefaultPath/$tspName"(controller: "tsp", action: "getDefaultPath")
		"/solveByForce/$tspName"(controller: "tsp", action: "solveByForce")
		"/solveByRandom/$tspName"(controller: "tsp", action: "solveByRandom")
	}
}
