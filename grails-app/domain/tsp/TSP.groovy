package tsp

class TSP {
    String name
    String comment
    List<Node> nodes

    static constraints = {
        name blank: false
        comment blank: false
        nodes size: 0..15 
    }
}
