class UrlMappings {

	static mappings = {

    //generic use case
		"/$controller/$action?/$id?"{
			constraints {
  
			}
		}

		"/"(controller: "tsp")
		"/$tspName"(controller: "tsp", action: "loadProblem")
		"500"(view: "error")
	}
}
