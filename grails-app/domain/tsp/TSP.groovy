package tsp

class TSP {
    String name
    String comment
    int dimension
    String x
    String y

    static constraints = {
        name blank: false, unique: true
        comment blank: false
        dimension size: 2..50 
        x blank: false
        y blank: false
    }
}
