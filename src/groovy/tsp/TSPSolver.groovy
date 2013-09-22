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
        return Math.sqrt((distX * distX) + (distY * distY)).toFloat()
    }
    
    /** Euclidian distance between two nodes, 'nuff said */ 
    private float getDistanceBetweenPoints(float x1, float x2, float y1, float y2) {
        float distX = x2 - x1
        float distY = y2 - y1
        return Math.sqrt((distX * distX) + (distY * distY)).toFloat()
    }

    /** Finds a depth-first search solution to the given TSP */
    public def solveByDFS() {
        println "Depth-first solving: ${nodeList}"
        return dfs(nodeList)
    }

    /** Performs a depth-first filtered permutation on a
        set of nodes with a pre-existing to/from path map,
        and returns the first path found */
    private def dfs(def nodeList) {
        def q = [0] as Queue
        float runningDistance = 0.0
        while (!q.isEmpty()) {
            int n = q.poll()
            if (n == nodeList.size() - 1) {
                def finalList = []
                int prev = 0
                nodeList.each() { node ->
                    if (node <= 0) {
                        finalList.add(node.abs())
                        runningDistance += getDistanceBetweenNodes(prev, node.abs())
                    }
                }
                println " --> ${finalList}: ${runningDistance}"
                return finalList
            }
            paths[n].each() { node ->
                if (nodeList[node] >= 0) {
                    q << node
                    nodeList[node] = -node
                }
            }
        }
        return null
    }

    /** Finds a breadth-first search solution to the given TSP */
    public def solveByBFS() {
        println "Breadth-first solving: ${nodeList}"
        bfs(nodeList)
        return runningShortestPath
    }

    /** Performs a breadth-first filtered permutation on a
        set of nodes with a pre-existing to/from path map
        and returns the first path found */
    private void bfs(def nodeList, def nodeString = [0], float runningDistance = 0.0, int currentNode = 0) {
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
                    bfs(nodeList, increasedNodeString, distance, nextNode) 
                }
            }
        }
    }

    /** Uses a greedy-heuristic approach to find the best
        Hamiltonian path through the TSP */
    public def solveByGreed() {
        int newNode
        def sortedNodes = []
        float dist = 9000.0
        println "Greedily solving: ${nodeList}"

        //start with node closest to origin
        nodeList.each() { node ->
            float d = getDistanceBetweenPoints(0.0, xs[node], 0.0, ys[node])
            if (d < dist) {
                newNode = node
                dist = d
            }
        }
        sortedNodes.push(newNode)
        nodeList = nodeList.minus(newNode)

        //add next closest node
        dist = 9000.0
        nodeList.each() { node ->
            float d = getDistanceBetweenPoints(0.0, xs[node], 0.0, ys[node])
            if (d < dist) {
                newNode = node
                dist = d
            }
        }
        sortedNodes.push(newNode)
        nodeList = nodeList.minus(newNode)

        //repeat processes for remaining nodes until none remain
        while (!nodeList.isEmpty()) {
            float runningDistance = getDistanceOfNodeList(sortedNodes)
            println " --> Path: ${sortedNodes} --> Running Distance: ${runningDistance}"

            //get next-closest node to origin
            dist = 9000.0
            nodeList.each() { node ->
                float d = getDistanceBetweenPoints(0.0, xs[node], 0.0, ys[node])
                if (d < dist) {
                    newNode = node
                    dist = d
                }
            }

            //find the closest edge
            dist = 9000.0
            int edgeIndex = -1
            for (int i=0; i<sortedNodes.size() - 1; i++) {
                def edge = getEdgePoint(
                    xs[sortedNodes[i]], 
                    xs[sortedNodes[i + 1]], 
                    ys[sortedNodes[i]], 
                    ys[sortedNodes[i + 1]])
                float d = getDistanceBetweenPoints(edge.x, xs[newNode], edge.y, ys[newNode])
                if (d < dist) {
                    edgeIndex = i 
                    dist = d
                }
            }
            if (edgeIndex < 0) {
                throw new IllegalArgumentException("No edge was found. What the hell?")
            }

            //insert new node between nodes of found edge
            sortedNodes = sortedNodes.plus(edgeIndex, [newNode])
            nodeList = nodeList.minus(newNode)
        }
        return sortedNodes
    }

    /** Gets the center point of the edge that connects
        two nodes */
    private def getEdgePoint(float x1, float x2, float y1, float y2) {
        return [
            x: ((x1 + x2) / 2.0).toFloat(),
            y: ((y1 + y2) / 2.0).toFloat()
        ]
    }

    /** Gets the final distance of the given path of nodes */
    private float getDistanceOfNodeList(def nodeList) {
        float runningDistance = 0.0
        for (int i=0; i<nodeList.size() - 1; i++) {
            runningDistance += getDistanceBetweenNodes(nodeList[i], nodeList[i + 1])
        }
        return runningDistance
    }
}
