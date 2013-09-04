package tsp

class Node {
    float x
    float y

    static constraints = {
        x size: 0.0..100.0
        y size: 0.0..100.0
    }
}
