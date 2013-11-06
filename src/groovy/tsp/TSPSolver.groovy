package tsp

import java.util.Random

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
    boolean usingCrowds

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
        usingCrowds = false
    }

    /** Gets the default path, ordered by the index of the
        TSP's nodes listed in the TSP file */
    private def getDefaultPath() {
        return nodeList
    }
    
    /** Returns a randomly generated Hamiltonian path through
        the TSP's set of nodes */
    private def solveByRandom() {
        println "Randomly solving: ${nodeList}"
        Collections.shuffle(nodeList)
        return nodeList += nodeList[0]
    }

    /** Returns a path which represents the shortest possible
        Hamiltonian path between all permutations of nodes */
    private def solveByForce() {
        println "Brute Force solving: ${nodeList}"
        permHamiltonianCycle(nodeList.minus(0)) 
        return runningShortestPath
    }

    /** Generates all permutations of a set of nodes and keeps
        track of the shortest path and its distance */
    private void permHamiltonianCycle(def remainingNodes, def nodeString = [0], float runningDistance = 0.0, int currentNode = 0) {
        if (remainingNodes.size() == 0) {
            runningDistance += getDistanceBetweenNodes(currentNode, 0)
            if (runningShortestDistance < 0.0 || runningDistance < runningShortestDistance) {
                //println " --> ${nodeString}: ${runningDistance}"
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
    private def solveByDFS() {
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
    private def solveByBFS() {
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
    private def solveByGreed() {
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
            println " --> +[${newNode}] = ${sortedNodes}, d=${runningDistance}"

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
            sortedNodes = sortedNodes.plus(edgeIndex + 1, [newNode])
            /*runningShortestDistance = -1.0
            permHamiltonianCycle([sortedNodes[edgeIndex], sortedNodes[edgeIndex + 1], sortedNodes[edgeIndex + 2]], [])
            sortedNodes[edgeIndex] = runningShortestPath[0]
            sortedNodes[edgeIndex + 1] = runningShortestPath[1]
            sortedNodes[edgeIndex + 2] = runningShortestPath[2]*/
            nodeList = nodeList.minus(newNode)
        }

        //calculate final jump
        //sortedNodes += [sortedNodes[0]]
        float runningDistance = getDistanceOfNodeList(sortedNodes)
        println " --> Returning to first node: ${sortedNodes} --> Running Distance: ${runningDistance}"
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

    /** Gets the final distance of the given path of nodes */
    private float getDistanceOfHamiltonianPath(def nodeList) {
        float runningDistance = 0.0
        for (int i=0; i<nodeList.size() - 1; i++) {
            runningDistance += getDistanceBetweenNodes(nodeList[i], nodeList[i + 1])
        }
        runningDistance += getDistanceBetweenNodes(nodeList[0], nodeList[nodeList.size() - 1])
        return runningDistance
    }

    /** Returns a randomly generated Hamiltonian path through
        the TSP's set of nodes */
    private def solveByGA(int generations, int populationSize, int mutationFactor) {
        //let's get started
        if (!usingCrowds) println "Using genetics to solve: ${nodeList}"
        Random rand = new Random()

        //analysis vars
        def mutationLog = []

        //track population and ratings
        def population = []
        def fitness = []
        
        //initialize parents
        def daddy = []
        float daddyFitness
        def mommy = []
        float mommyFitness
        
        //initialize child
        def child = []
        float childFitness
        
        //initialize fitness comparator function
        def cmpMin = [compare: {a, b -> a.equals(b) ? 0: a<b ? -1: 1}] as Comparator
        if (!usingCrowds) {
            println "Population size: ${populationSize}"
            println "Mutation Factor: ${mutationFactor}"
            println "Generations: ${generations}"
        }

        //initialize a population randomly and calculate fitnesses
        for (int i=0; i<populationSize; i++) {
            population[i] = nodeList.clone()
            Collections.shuffle(population[i])
            //evaluate fitness of path
            fitness[i] = getDistanceOfHamiltonianPath(population[i])
        }
        mutationLog += fitness[0]

        //until generation criteria is satisfied
        for (int i=0; i<generations; i++) {
            //select the best of the population as daddy
            daddyFitness = fitness.min(cmpMin)
            daddy = population[fitness.indexOf(daddyFitness)]
            fitness = fitness.minus(daddyFitness)
            
            //select the next-best of the population as mommy
            mommyFitness = fitness.min(cmpMin)
            mommy = population[fitness.indexOf(mommyFitness)]
            fitness = fitness.minus(mommyFitness)

            //ensure that no nodes were lost in the selection
            assert daddy.size() == nodeList.size()
            assert mommy.size() == nodeList.size()

            //produce a child from the parent set
            child = []
            //1. take the first half of jumps from dad's first half
            for (int j=0; j<daddy.size() / 2; j++) {
                child += daddy[j]
            }
            //2. take the remaining nodes in order as they appear in mommy
            int c = 0;
            while (child.size() < daddy.size()) {
                if (!child.contains(mommy[c])) {
                    child += mommy[c]
                }
                c++;
            }
            //ensure that no nodes were lost in the reproduction
            assert child.size() == nodeList.size()
            childFitness = getDistanceOfHamiltonianPath(child)
            mutationLog += childFitness

            //create new population from parents, child, and mutations of the child
            //1. clear old populations and fitnesses
            population.clear()
            fitness.clear()
            //2. add parents and child manually to next population
            population += [daddy]
            fitness += daddyFitness
            population += [mommy]
            fitness += mommyFitness
            population += [child]
            fitness += childFitness
            //3. finish population with mutations of child
            for (int j=3; j<populationSize; j++) {
                //carbon copy the child, optimal or not
                population[j] = child.clone()

                //mutate! swap some cells at random
                for (int k=0; k<mutationFactor; k++) {
                    Collections.swap(population[j], rand.nextInt(child.size()), rand.nextInt(child.size()))
                }
                fitness[j] = getDistanceOfHamiltonianPath(population[j])
            }
        }
        
        //return the father as the best path, unless the child is fitter
        if (usingCrowds) {
            return [path: daddy, distance: daddyFitness]
        }

        //debug messages for copypasta analysis into graphing tool
        println " --> Mutation Log:"
        println " --> ${mutationLog}"

        if (daddyFitness < childFitness) {
            println " --> Final parent distance (fitness): ${daddyFitness}"
            return daddy += daddy[0]
        }
        else {
            println " --> Final child distance (fitness): ${childFitness}"
            return child += child[0]
        }
    }

    /** Runs a set of GAs repeatedly and finds 
        similarities among solutions */
    private def solveByWisdom() {
        println "Solving by Wisdom of the Crowds: ${nodeList}"
        usingCrowds = true
        def crowd = []        
        def fitnesses = []        
        def edges = [:]
        (0..9).each() { i ->
            crowd[i] = nodeList.clone()
            def newb = solveByGA(500, 10, 1)
            crowd[i] = newb.path
            fitnesses[i] = newb.distance
            crowd[i].eachWithIndex() { node, j ->
                if (j < nodeList.size() - 1) {
                    if (i == 0) edges[j] = []
                    edges[j] += crowd[i][j + 1]
                }
            }
        }

        //find the best among the crowds
        //int lowest = fitnesses.min(cmpMin)
        //def sol = crowd[fitnesses.indexOf(lowest)]

        //start with origin and determine the next node
        //by its popularity within the crowd
        for (int i=0; i<nodeList.size() - 1; i++) {
            def votes = [:]

            //initialize voting system for all nodes
            //start with zero votes
            for (int j=0; j<nodeList.size(); j++) {
                votes[j] = 0
            }

            //for this node, compute the number of
            //total votes for each next node
            edges[i].each() { nextNode ->
                votes[nextNode] += 1
            }

            //print the votes
            println " --> Votes: ${votes}"
            
            //for each node, add the best next node
            //based on the votes
        }

        //process votes and determine a coolio path
        def sol = [0]
        while (sol.size() < nodeList.size()) {
            //voting system

            //determine the best next
            def bestNext
            boolean done = false
            while (!done) {
                bestNext = votes.max { it.value }.key
                println " --> bestNext = ${bestNext}"
                votes.remove(bestNext)
                if (!sol.contains(bestNext)) {
                    println "Current sol: ${sol}"
                    println "Try adding: ${bestNext} ?"
                    done = true
                }
            }
            sol += bestNext
        }

        //print solution and return
        println " --> Crowdsourced Solution: ${sol}"
        return sol
    }
}
