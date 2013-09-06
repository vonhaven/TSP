package tsp

class ProblemService {

    def construct() {
        File tspDir = new File("src/tsp/")
        assert tspDir.exists()
        assert tspDir.isDirectory()
        def tsps = tspDir.listFiles()
        tsps.each() { file ->
            constructFromFile(file)
        }
    }

    private void constructFromFile(File file) {
        String name
        String comment = ""
        int dimension = 0
        int index = 0
        List<Node> nodes = []

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
                def node = new Node(x: coords[0], y: coords[1])
                node.save(flush: true, failOnError: true)
                nodes.add(node)
                index++
            }
            else {
                println "Invalid line in TSP: " + line
            }
        }    
        def tsp = new TSP(
            name: name,
            comment: comment,
            nodes: nodes
        )
        tsp.save(flush: true, failOnError: true)
        println String.format(" --> TSP created from file: %s - %s", tsp.name, tsp.comment, tsp.nodes.size())
    }
}
