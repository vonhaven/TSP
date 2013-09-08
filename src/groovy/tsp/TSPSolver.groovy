package tsp

class TSPSolver {
    def xs
    def ys
    def startingList = []
    int originalSize
    float runningShortest

    public TSPSolver(def xs, def ys) {
        assert xs.size() == ys.size()
        this.xs = xs
        this.ys = ys
        for (int i=0; i < xs.size(); i++) {
            startingList += i
        }
    }
    
    public def solveByForce() {
        //permutate(startingList) 
        return startingList
    }

    public def solveByRandom() {
        Collections.shuffle(startingList)
        return startingList
    }

    private String permutate(def runningList, runningDistance) {
        if (remainingNodes.size() == 0) {
            
        }    
        else {
            reducedList.each() { node ->
                def reducedList = remainingNodes.clone()
                reducedList.remove(node)
                permutate(reducedList)
            }   
        }   
    }   
    
    /** Euclidian distance between two nodes, 'nuff said */ 
    private float getDistanceBetweenNodes(int index1, int index2) {
        float distX = x[index2] - x[index1]
        float distY = y[index2] - y[index1]
        return Math.sqrt((distX * distX) + (distY * distY))
    }

}
