package tsp

class Node {
    int index
    float x
    float y

    static constraints = {
        index range: 0..99
        x range: 0.0..100.0
        y range: 0.0..100.0
    }
}
