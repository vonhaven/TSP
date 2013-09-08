package tsp

class TspController {

    def problemService

    static def activeProblem
    static def optimalPath

    def index() {
        def tsps = TSP.findAll()
        return [
            tsps: tsps, 
            activeProblem: activeProblem,
            optimalPath: optimalPath
        ]  
    }

    def load() {
        def tspName = params.tspName
        if (tspName != null) {
            optimalPath = null
            activeProblem = TSP.findByName(tspName)
            redirect(controller: "tsp", action: "index")
        }
        else {
            redirect(view: "error")
        }
    }

    def solveByForce() {
        def tspName = params.tspName
        if (tspName != null) {
            activeProblem = TSP.findByName(tspName)
            optimalPath = problemService.solveByForce(activeProblem)
            redirect(controller: "tsp", action: "index")
        }
        else {
            redirect(view: "error")
        }
    }

    def solveByRandom() {
        def tspName = params.tspName
        if (tspName != null) {
            activeProblem = TSP.findByName(tspName)
            optimalPath = problemService.solveByRandom(activeProblem)
            redirect(controller: "tsp", action: "index")
        }
        else {
            redirect(view: "error")
        }
    }
}
