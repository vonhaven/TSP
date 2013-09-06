#TSP visualization service for canvas
class TSP
  name: null
  comment: null
  nodes: null
  canvas: null
  context: null
  
  #construct TSP from GSP JSON
  constructor: (@name, @comment, @nodes) ->
    alert "#{@name}: #{@comment}"
    @canvas = document.getElementById 'map'
    @context = @canvas.getContext '2d'

window.TSP = TSP
