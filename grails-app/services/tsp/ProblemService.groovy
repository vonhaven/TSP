package tsp

import groovy.time.*

class ProblemService {

    /** Construct all TSP objects for all projects */
    def construct() {
        constructFromPath("src/tsp/1", "random")
        constructFromPath("src/tsp/1", "brute")
        constructFromPath("src/tsp/2", "bfs")
        constructFromPath("src/tsp/2", "dfs")
        constructFromPath("src/tsp/3", "greed")
        constructFromPath("src/tsp/4", "genetic")
        constructFromPath("src/tsp/5", "wisdom")
    }

    /** Call to construct all TSP files in src/tsp */
    private void constructFromPath(String path, String set) {
        File tspDir = new File(path)
        assert tspDir.exists()
        assert tspDir.isDirectory()
        def tsps = tspDir.listFiles()
        tsps.each() { file ->
            constructFromFile(file, set)
        }
    }

    /** Solves the given TSP by the file's predermined method */
    def solve(TSP tsp, def options) {
        def timeStart = new Date()
        def solution
        switch (tsp.set) {
            case "random":
                solution = solveByRandom(tsp)
                break
            case "brute":
                solution = solveByForce(tsp)
                break
            case "bfs":
                solution = solveByBFS(tsp)
                break
            case "dfs":
                solution = solveByDFS(tsp)
                break
            case "greed":
                solution = solveByGreed(tsp)
                break
            case "genetic":
                solution = solveByGA(tsp, options[0].toInteger(), options[1], options[2])
                break
            case "wisdom":
                solution = solveByWisdom(tsp)
                break
            default:
                solution = "Error determining solution method of TSP"
                break
        }
        def timeStop = new Date()
        TimeDuration duration = TimeCategory.minus(timeStop, timeStart)
        println "Done! Finished in: ${duration}. Solution: ${solution}"
        return solution
    }

    /** Gets the default path of a TSP, where indices are ordered
        by the order in which they are listed in the TSP file */
    private def getDefaultPath(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf).getDefaultPath()
    }

    /** Solves the TSP by brute force, returning a String
        of node indices in order of shortest calculated path */
    private def solveByForce(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf).solveByForce()
    }

    /** Paths the TSP randomly, returning a String of node
        indices representing a random path through the TSP */
    private def solveByRandom(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf).solveByRandom()
    }

    /** Paths the TSP by a breadth-first search for the
        shortest path to the destination */
    private def solveByBFS(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf, tsp.paths).solveByBFS()
    }

    /** Paths the TSP by a depth-first search for the
        shortest path to the destination */
    private def solveByDFS(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf, tsp.paths).solveByDFS()
    }

    /** Paths the TSP by a greedy heuristic search for
        the best Hamiltonian path through the TSP */
    private def solveByGreed(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf).solveByGreed()
    }

    /** Paths the TSP by a greedy heuristic search for
        the best Hamiltonian path through the TSP */
    private def solveByGA(TSP tsp, int generations, int populationSize, int mutationFactor) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf, tsp.paths).solveByGA(generations, populationSize, mutationFactor)
    }

    /** Paths the TSP by a greedy heuristic search for
        the best Hamiltonian path through the TSP */
    private def solveByWisdom(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf).solveByWisdom()
    }

    /** Turns a randomly generated TSP file into a TSP file
        to be stored in this database and loaded into the browser */
    private void constructFromFile(File file, String set) {
        String name
        String comment = ""
        int dimension = 0
        int index = 0
        String x = ""
        String y = ""
        String paths = ""

        final String typeHeader = "TYPE: "      
        final String nameHeader = "NAME: "      
        final String commentHeader = "COMMENT: "      
        final String dimensionHeader = "DIMENSION: "      
        final String edgeWeightHeader = "EDGE_WEIGHT_TYPE: "      
        final String nodeHeader = "NODE_COORD_SECTION"      

        file.getText().tokenize("\n").each() { line ->
            if (line.startsWith(typeHeader)) {
                assert line.replace(typeHeader, "").trim() == "TSP"
            }
            else if (line.startsWith(edgeWeightHeader)) {
                assert line.replace(edgeWeightHeader, "").trim() == "EUC_2D"
            }
            else if (line.startsWith(nameHeader)) {
                name = line.tokenize(": ")[1].trim()
            }
            else if (line.startsWith(commentHeader)) {
                comment += line.tokenize(": ")[1].trim() + " "
            }
            else if (line.startsWith(dimensionHeader)) {
                dimension = line.tokenize(": ")[1].trim().toInteger()
            }
            else if (line.startsWith(nodeHeader)) {
                assert dimension >= 2
            }
            else if (line.startsWith(String.format("%s ", (index + 1)))) {
                List<String> coords = line.split()
                assert coords.size() == 3 || coords.size() == 4
                assert coords[0].toInteger() == index + 1
                if (index > 0) {
                    x += ","
                    y += ","
                    paths += "."
                }
                if (coords.size() == 4) {
                    paths += coords[3].trim()
                }
                x += coords[1].trim()
                y += coords[2].trim()
                index++
            }
            else {
                println "Invalid line in TSP: " + line
            }
        }    
        def tsp = new TSP(
            name: name,
            set: set,
            comment: comment.trim(),
            dimension: dimension,
            x: x,
            y: y,
            paths: paths
        )
        tsp.save(flush: true, failOnError: true)
        println String.format(" --> TSP for %s created from file: %s [%s]", tsp.set, tsp.name, tsp.dimension)
    }
}
