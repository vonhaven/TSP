#TSP visualization service for canvas
class TSP
  name: null
  comment: null
  dimension: null
  x: null
  y: null
  canvas: null
  context: null
  
  #construct TSP from GSP JSON
  constructor: (@name, @comment, @dimension, @x, @y) ->
    x = @x.split ","
    y = @y.split ","
    @canvas = document.getElementById 'map'
    @context = @canvas.getContext '2d'
    for i in [0..(@dimension - 1)]
      if i < (@dimension - 1)
        @drawLine x[i], x[i + 1], y[i], y[i + 1], i
      else
        @drawLine x[i], x[0], y[i], y[0], i
    for i in [0..(@dimension - 1)]
      @drawNode x[i], y[i], i

  drawNode: (x, y, i) ->
    @context.fillStyle= "#ddd"
    @context.strokeStyle = "#555"
    @context.beginPath()
    @context.arc @convertX(x), @convertY(y), 8, 0, 2 * Math.PI
    @context.closePath()
    @context.fill()
    @context.stroke()
    @context.fillStyle= "#555"
    @context.font = "14px Courier New"
    @context.fillText "#{i + 1}", @convertX(x) - 4.5, @convertY(y) - 13

  drawLine: (x1, x2, y1, y2, i) ->
    @context.strokeStyle = "#bbb"
    @context.moveTo @convertX(x1), @convertY(y1)
    @context.lineTo @convertX(x2), @convertY(y2)
    @context.stroke()

  convertX: (x) ->
    return ((x * 790) / 100) + 5

  convertY: (y) ->
    return ((y * 580) / 100) + 15

window.TSP = TSP
