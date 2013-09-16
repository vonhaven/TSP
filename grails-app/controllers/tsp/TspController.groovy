package tsp

class TspController {

    def problemService

    static def activeProblem
    static def tspSet
    static def path

    def index() {
        def tsps
        if (tspSet) {
            tsps = TSP.findAllBySet(tspSet)
        }
        return [
            tsps: tsps, 
            activeProblem: activeProblem,
            path: path
        ]  
    }

    def load() {
        activeProblem = TSP.findById(params.tspId)
        path = null
        redirect(controller: "tsp", action: "index")
    }

    def loadSet() {
        activeProblem = null
        path = null
        tspSet = params.tspSet
        redirect(controller: "tsp", action: "index")
    }

    def solve() {
        activeProblem = TSP.findById(params.tspId)
        path = problemService.solve(activeProblem)
        redirect(controller: "tsp", action: "index")
    }

    def reset() {
        activeProblem = null
        path = null
        tspSet = null
        redirect(controller: "tsp", action: "index")
    }

    def getDefaultPath() {
        def tspName = params.tspName
        activeProblem = TSP.findById(tspId)
        path = problemService.getDefaultPath(activeProblem)
        redirect(controller: "tsp", action: "index")
    }
}
