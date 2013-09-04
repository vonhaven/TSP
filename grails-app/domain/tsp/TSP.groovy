package tsp

class TSP {
    String name
    String comment

    static hasMany = [nodes: Node]

    static constraints = {
        name blank: false, unique: true
        comment blank: false
        nodes size: 2..15 
    }
}
