package tsp

class TspController {

    static def activeProblem

    def index() {
        def tsps = TSP.findAll()
        [tsps: tsps, activeProblem: activeProblem]  
    }

    def loadProblem() {
        def tsps = TSP.findAll()
        def tspName = params.tspName
        if (tspName != null) {
            activeProblem = TSP.findByName(tspName)
        }
        redirect(controller: "tsp", action: "index")
    }
}
