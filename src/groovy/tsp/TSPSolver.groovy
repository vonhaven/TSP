package tsp

/** A class which provides a variety of methods to solve the
    Traveling Salesman Problem */
class TSPSolver {
    def xs
    def ys
    def nodeList = []
    def paths = []
    int originalSize
    List<String> runningShortestPath = []
    float runningShortestDistance = -1.0

    /** Construct a TSP solver for a two-dimensional system,
        requiring two lists of floating point numbers */
    public TSPSolver(def xs, def ys, def pathsString = "") {
        assert xs.size() == ys.size()
        this.xs = xs
        this.ys = ys
        for (int i=0; i < xs.size(); i++) {
            nodeList += i
        }
        if (pathsString != "") {
            pathsString.tokenize(".").each() { pathString ->
                def path = []
                pathString.tokenize(",").each() { node ->
                    path.add(node.toInteger())
                }
                paths.add(path)
            }
        }
        println paths
    }

    /** Gets the default path, ordered by the index of the
        TSP's nodes listed in the TSP file */
    public def getDefaultPath() {
        return nodeList
    }
    
    /** Returns a randomly generated Hamiltonian path through
        the TSP's set of nodes */
    public def solveByRandom() {
        println "Randomly solving: ${nodeList}"
        Collections.shuffle(nodeList)
        return nodeList
    }

    /** Returns a path which represents the shortest possible
        Hamiltonian path between all permutations of nodes */
    public def solveByForce() {
        println "Brute Force solving: ${nodeList}"
        permHamiltonianCycle(nodeList) 
        return runningShortestPath
    }

    /** Generates all permutations of a set of nodes and keeps
        track of the shortest path and its distance */
    private void permHamiltonianCycle(def remainingNodes, def nodeString = [], float runningDistance = 0.0, int currentNode = 0) {
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
                permHamiltonianCycle(reducedNodes, increasedNodeString, distance, nextNode)
            }   
        }   
    }   
    
    /** Euclidian distance between two nodes, 'nuff said */ 
    private float getDistanceBetweenNodes(int index1, int index2) {
        float distX = xs[index2] - xs[index1]
        float distY = ys[index2] - ys[index1]
        return Math.sqrt((distX * distX) + (distY * distY))
    }

    /** Finds a breadth-first search solution to the given TSP */
    public def solveByBFS() {
        println "Breadth-first solving: ${nodeList}"
        bfs(nodeList)
        return runningShortestPath
    }

    /** Performs a breadth-first filtered permutation on a
        set of nodes with a pre-existing to/from path map */
    private void bfs(def nodeList) {
        /*SynchronousQueue<BFS> queue = new SynchronousQueue()
        def bfsList = []
        nodeList.each() { node ->
            bfsList.add(new BFS(node: node, marked: false, runningDistance: 0.0))
        }
        def bfs = bfsList[0]
        queue.put(bfs)
        while (!queue.isEmpty()) {
            bfs = queue.pull()
            if (bfs.node == nodeList[nodeList.size() - 1]) {
                println " --> ${queue}: ${runningDistance}"
                if (bfs.runningDistance < runningDistance || runningDistance < 0.0) {
                    runningDistance = bfs.runningDistance
                    runningShortestPath = []
                    while (!queue.isEmpty()) {
                        runningShortestPath.add(queue.pull())
                    }
                }
            }
        }*/
    }

    private class BFS {
        int node
        boolean marked
        float runningDistance
    }

    /** Finds a depth-first search solution to the given TSP */
    public def solveByDFS() {
        println "Depth-first solving: ${nodeList}"
        dfs(nodeList, [0])
        println "Done!"
        return runningShortestPath
    }

    /** Performs a depth-first filtered permutation on a
        set of nodes with a pre-existing to/from path map */
    private void dfs(def nodeList, def nodeString = [], float runningDistance = 0.0, int currentNode = 0) {
        if (currentNode == (nodeList.size() - 1)) {
            if (runningShortestDistance < 0.0 || runningDistance < runningShortestDistance) {
                println " --> ${nodeString}: ${runningDistance}"
                runningShortestDistance = runningDistance
                runningShortestPath = nodeString
            }
        }
        else {
            paths[currentNode].each() { nextNode ->
                if (!nodeString.contains(nextNode)) {
                    def increasedNodeString = nodeString.clone()
                    increasedNodeString += nextNode
                    float distance = runningDistance + getDistanceBetweenNodes(currentNode, nextNode - 1)
                    dfs(nodeList, increasedNodeString, distance, nextNode) 
                }
            }
        }
    }
}
