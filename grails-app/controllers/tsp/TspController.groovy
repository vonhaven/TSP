package tsp

class TspController {

    def problemService

    static def activeProblem
    static def tspSet
    static def path
    static def prettyPath
    static def generations
    static def populationSize
    static def mutationFactor

    def index() {
        def tsps
        if (tspSet) {
            tsps = TSP.findAllBySet(tspSet)
        }
        if (!generations) {
            generations = 1
        }
        if (!populationSize) {
            populationSize = 5
        }
        if (!mutationFactor) {
            mutationFactor = 1
        }
        return [
            tsps: tsps, 
            tspSet: tspSet,
            activeProblem: activeProblem,
            path: path,
            prettyPath: prettyPath,
            generations: generations,
            populationSize: populationSize,
            mutationFactor: mutationFactor
        ]  
    }

    def load() {
        activeProblem = TSP.findById(params.tspId)
        path = null
        redirect(controller: "tsp", action: "index")
    }

    def loadSet() {
        activeProblem = null
        generations = null
        populationSize = null
        mutationFactor = null
        path = null
        prettyPath = null
        tspSet = params.tspSet
        redirect(controller: "tsp", action: "index")
    }

    def setGenerations() {
        generations = params.generations
        redirect(controller: "tsp", action: "index")
    }

    def solve() {
        activeProblem = TSP.findById(params.tspId)
        def options = [generations, populationSize, mutationFactor]
        path = problemService.solve(activeProblem, options)
        prettyPath = []
        path.each() { node ->
            prettyPath += node + 1
        }
        redirect(controller: "tsp", action: "index")
    }

    def reset() {
        activeProblem = null
        generations = null
        populationSize = null
        mutationFactor = null
        path = null
        prettyPath = null
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
