#TSP visualization service for canvas
class TSP
  name: null
  comment: null
  x: null
  y: null
  path: null
  canvas: null
  context: null
  colors: [
    "#dcc",
    "#cdc",
    "#ccd",
    "#ddc",
    "#cdd",
    "#dcd",
    "#ecc",
    "#cec",
    "#cce",
    "#cde",
    "#ecd",
    "#dec"
  ]
  
  #construct TSP from GSP JSON
  constructor: (@name, @comment, @x, @y, @path) ->
    x = @x.split ","
    y = @y.split ","
    @canvas = document.getElementById 'map'
    @context = @canvas.getContext '2d'
    if @path
      path = @path.replace("[", "").replace("]", "").replace(/\s+/g, ' ').split ", "
      console.log "Path List: #{path}"
      for i in [0..path.length - 1]
        if path[i] == path[path.length - 1]
          @drawLine x[path[i]], x[path[0]], y[path[i]], y[path[0]]
        else
          @drawLine x[path[i]], x[path[i+1]], y[path[i]], y[path[i + 1]]
    for i in [0..(x.length)]
      @drawNode x[i], y[i], i

  drawNode: (x, y, i) ->
    @context.fillStyle = @colors[i]
    @context.strokeStyle = "#555"
    @context.beginPath()
    @context.arc @convertX(x), @convertY(y), 8, 0, 2 * Math.PI
    @context.closePath()
    @context.fill()
    @context.stroke()
    @context.fillStyle= "#555"
    @context.font = "14px Courier New"
    @context.fillText "#{i + 1}", @convertX(x) - 4.5, @convertY(y) - 13

  drawLine: (x1, x2, y1, y2) ->
    @context.strokeStyle = "#bbb"
    @context.moveTo @convertX(x1), @convertY(y1)
    @context.lineTo @convertX(x2), @convertY(y2)
    @context.stroke()

  convertX: (x) ->
    return ((x * 790) / 100) + 5

  convertY: (y) ->
    return ((y * 580) / 100) + 15

window.TSP = TSP
