package tsp

/** A class which provides a variety of methods to solve the
    Traveling Salesman Problem */
class TSPSolver {
    def xs
    def ys
    def nodeList = []
    int originalSize
    List<String> runningShortestPath = []
    float runningShortestDistance = -1.0

    /** Construct a TSP solver for a two-dimensional system,
        requiring two lists of floating point numbers */
    public TSPSolver(def xs, def ys) {
        assert xs.size() == ys.size()
        this.xs = xs
        this.ys = ys
        for (int i=0; i < xs.size(); i++) {
            nodeList += i
        }
    }

    /** Gets the default path, ordered by the index of the
        TSP's nodes listed in the TSP file */
    public def getDefaultPath() {
        return nodeList
    }
    
    /** Returns a path which represents the shortest possible
        Hamiltonian path between all permutations of nodes */
    public def solveByForce() {
        println "Brute Force solving: ${nodeList}"
        permutate([], nodeList) 
        return runningShortestPath
    }

    /** Returns a randomly generated Hamiltonian path through
        the TSP's set of nodes */
    public def solveByRandom() {
        Collections.shuffle(nodeList)
        return nodeList
    }

    /** Generates all permutations of a set of nodes and keeps
        track of the shortest path and its distance */
    private void permutate(def nodeString, def remainingNodes, float runningDistance = 0.0, int currentNode = 0) {
        if (remainingNodes.size() == 0) {
            if (runningShortestDistance < 0.0 || runningDistance < runningShortestDistance) {
                println " --> ${nodeString}: ${runningDistance}"
                runningShortestDistance = runningDistance
                runningShortestPath = nodeString
            }
        }    
        else {
            remainingNodes.each() { nextNode ->
                def increasedNodeString = nodeString.clone()
                increasedNodeString += nextNode

                def reducedNodes = remainingNodes.clone()
                reducedNodes.remove(reducedNodes.indexOf(nextNode))

                float distance = runningDistance + getDistanceBetweenNodes(currentNode, nextNode)

                permutate(increasedNodeString, reducedNodes, distance, nextNode)
            }   
        }   
    }   
    
    /** Euclidian distance between two nodes, 'nuff said */ 
    private float getDistanceBetweenNodes(int index1, int index2) {
        float distX = xs[index2] - xs[index1]
        float distY = ys[index2] - ys[index1]
        return Math.sqrt((distX * distX) + (distY * distY))
    }

}
