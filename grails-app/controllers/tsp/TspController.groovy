package tsp

class TspController {

    def problemService

    static def activeProblem
    static def path

    def index() {
        def tsps = TSP.findAll()
        return [
            tsps: tsps, 
            activeProblem: activeProblem,
            path: path
        ]  
    }

    def load() {
        def tspName = params.tspName
        if (tspName != null) {
            activeProblem = TSP.findByName(tspName)
            path = null
            redirect(controller: "tsp", action: "index")
        }
        else {
            redirect(view: "error")
        }
    }

    def reset() {
        activeProblem = null
        path = null
        redirect(controller: "tsp", action: "index")
    }

    def getDefaultPath() {
        def tspName = params.tspName
        if (tspName != null) {
            activeProblem = TSP.findByName(tspName)
            path = problemService.getDefaultPath(activeProblem)
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
            path = problemService.solveByForce(activeProblem)
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
            path = problemService.solveByRandom(activeProblem)
            redirect(controller: "tsp", action: "index")
        }
        else {
            redirect(view: "error")
        }
    }
}
