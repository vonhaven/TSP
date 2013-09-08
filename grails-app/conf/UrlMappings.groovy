class UrlMappings {

	static mappings = {

		"/"(controller: "tsp")
		"/load/$tspName"(controller: "tsp", action: "load")
		"/solveByForce/$tspName"(controller: "tsp", action: "solveByForce")
		"/solveByRandom/$tspName"(controller: "tsp", action: "solveByRandom")
	}
}
