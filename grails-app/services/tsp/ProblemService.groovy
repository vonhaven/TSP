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

        static final String typeHeader = "TYPE: "      
        static final String nameHeader = "NAME: "      
        static final String commentHeader = "COMMENT: "      
        static final String dimensionHeader = "DIMENSION: "      
        static final String nodeHeader = "NODE_COORD_SECTION"      

        file.eachLine() { line ->
            if (line.startsWith(typeHeader)) {
                if (line.replace(typeHeader, "") != "TSP") {
                    throw new IllegalArgumentException("TSP filemuid nodes.")
                }
            }
            else if (line.startsWith(nameHeader)) {
                name = line.replace(typeHeader, "")
            }
            else if (line.startsWith(commentHeader)) {
                comment += line.replace(commentHeader, "")
            }
            else if (line.startsWith(dimensionHeader)) {
                dimension = line.replace(dimensionHeader, "").toInteger()
            }
            else if (line.startsWith(nodeHeader) {
                if (dimension < 2) {
                    throw new IllegalArgumentException("TSP file must contain at least 2 valid nodes.")
                }
            }
            else if (line.startsWith(index + 1 + " ")) {
                String nodeLine = line.replace(index.toString + " ", "")
                def coords = nodeLine.split(" ")
                nodes.add(new Node(x: coords[0], y: coords[1]), index) 
                index++
            }
        }    
        new TSP(
            name: name,
            comment: comment,
            nodes: nodes
        ).save(flush: true, failOnError: true)
    }
}
