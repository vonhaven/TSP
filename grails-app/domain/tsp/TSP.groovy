package tsp

class TSP {
    String name
    String set
    String comment
    int dimension
    String x
    String y
    String paths

    static constraints = {
        name blank: false, unique: 'set'
        set blank: false
        comment blank: false
        dimension size: 2..50 
        x blank: false, maxSize: 8192
        y blank: false, maxSize: 8192
        paths nullable: true
    }
    
    static mapping = {
        x type: 'text'
        y type: 'text'
    }

    /** Returns the x and y coordinate Stringsas two lists 
        of Floats */
    public def getNodes() {
        def xs = x.split(',')
        def ys = y.split(',')
        assert xs.size() == ys.size()
        
        def xf = []
        def yf = [] 
        xs.each() { xf += it.toFloat() }
        ys.each() { yf += it.toFloat() }
        assert xf.size() == yf.size()
        
        [xf: xf, yf: yf]
    }
}
