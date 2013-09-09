package tsp

class ProblemService {

    /** Call to construct all TSP files in src/tsp */
    def construct() {
        File tspDir = new File("src/tsp/")
        assert tspDir.exists()
        assert tspDir.isDirectory()
        def tsps = tspDir.listFiles()
        tsps.each() { file ->
            constructFromFile(file)
        }
    }

    /** Gets the default path of a TSP, where indices are ordered
        by the order in which they are listed in the TSP file */
    def getDefaultPath(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf).getDefaultPath()
    }

    /** Solves the TSP by brute force, returning a String
        of node indices in order of shortest calculated path */
    def solveByForce(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf).solveByForce()
    }

    /** Paths the TSP randomly, returning a String of node
        indices representing a random path through the TSP */
    def solveByRandom(TSP tsp) {
        def nodes = tsp.getNodes()
        return new TSPSolver(nodes.xf, nodes.yf).solveByRandom()
    }

    /** Turns a randomly generated TSP file into a TSP file
        to be stored in this database and loaded into the browser */
    private void constructFromFile(File file) {
        String name
        String comment = ""
        int dimension = 0
        int index = 0
        String x = ""
        String y = ""

        final String typeHeader = "TYPE: "      
        final String nameHeader = "NAME: "      
        final String commentHeader = "COMMENT: "      
        final String dimensionHeader = "DIMENSION: "      
        final String edgeWeightHeader = "EDGE_WEIGHT_TYPE: "      
        final String nodeHeader = "NODE_COORD_SECTION"      

        file.getText().split("\n").each() { line ->
            if (line.startsWith(typeHeader)) {
                assert line.replace(typeHeader, "").trim() == "TSP"
            }
            else if (line.startsWith(edgeWeightHeader)) {
                assert line.replace(edgeWeightHeader, "").trim() == "EUC_2D"
            }
            else if (line.startsWith(nameHeader)) {
                name = line.split(": ")[1].trim()
            }
            else if (line.startsWith(commentHeader)) {
                comment += line.split(": ")[1].trim() + " "
            }
            else if (line.startsWith(dimensionHeader)) {
                dimension = line.split(": ")[1].trim().toInteger()
            }
            else if (line.startsWith(nodeHeader)) {
                assert dimension >= 2
            }
            else if (line.startsWith(String.format("%s ", (index + 1)))) {
                def coords = line.split(" ")
                assert coords.size() == 3
                assert coords[0].toInteger() == index + 1
                if (index > 0) {
                    x += ","
                    y += ","
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
            comment: comment.trim(),
            dimension: dimension,
            x: x,
            y: y
        )
        tsp.save(flush: true, failOnError: true)
        println String.format(" --> TSP created from file: %s [%s] - %s", tsp.name, tsp.dimension, tsp.comment)
    }
}
