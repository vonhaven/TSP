package tsp

class TSPSolver {
    def xs
    def ys
    def nodeList = []
    int originalSize
    List<String> runningShortestPath = []
    float runningShortestDistance = -1.0

    public TSPSolver(def xs, def ys) {
        assert xs.size() == ys.size()
        this.xs = xs
        this.ys = ys
        for (int i=0; i < xs.size(); i++) {
            nodeList += i
        }
    }

    public def getDefaultPath() {
        return nodeList
    }
    
    public def solveByForce() {
        println "Brute Force solving: ${nodeList}"
        permutate([], nodeList, 0.0, 0) 
        return runningShortestPath
    }

    public def solveByRandom() {
        Collections.shuffle(nodeList)
        return nodeList
    }

    private void permutate(def nodeString, def remainingNodes, float runningDistance, int currentNode) {
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
