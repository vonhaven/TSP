(function() {
  var TSP;

  TSP = (function() {

    TSP.prototype.name = null;

    TSP.prototype.comment = null;

    TSP.prototype.dimension = null;

    TSP.prototype.x = null;

    TSP.prototype.y = null;

    TSP.prototype.canvas = null;

    TSP.prototype.context = null;

    function TSP(name, comment, dimension, x, y) {
      var i, _ref;
      this.name = name;
      this.comment = comment;
      this.dimension = dimension;
      this.x = x;
      this.y = y;
      x = this.x.split(",");
      y = this.y.split(",");
      this.canvas = document.getElementById('map');
      this.context = this.canvas.getContext('2d');
      for (i = 0, _ref = this.dimension - 1; 0 <= _ref ? i <= _ref : i >= _ref; 0 <= _ref ? i++ : i--) {
        if (i < (this.dimension - 1)) {
          this.drawLine(x[i], x[i + 1], y[i], y[i + 1], i);
        } else {
          this.drawLine(x[i], x[0], y[i], y[0], i);
        }
        this.drawNode(x[i], y[i], i);
      }
    }

    TSP.prototype.drawNode = function(x, y, i) {
      this.context.fillStyle = "#ddd";
      this.context.strokeStyle = "#555";
      this.context.beginPath();
      this.context.arc(this.convertX(x), this.convertY(y), 8, 0, 2 * Math.PI);
      this.context.closePath();
      this.context.fill();
      this.context.stroke();
      this.context.fillStyle = "#555";
      this.context.font = "14px Courier New";
      return this.context.fillText("" + (i + 1), this.convertX(x) - 4.5, this.convertY(y) - 13);
    };

    TSP.prototype.drawLine = function(x1, x2, y1, y2, i) {
      this.context.strokeStyle = "#aaa";
      this.context.moveTo(this.convertX(x1), this.convertY(y1));
      this.context.lineTo(this.convertX(x2), this.convertY(y2));
      return this.context.stroke();
    };

    TSP.prototype.convertX = function(x) {
      return ((x * 790) / 100) + 5;
    };

    TSP.prototype.convertY = function(y) {
      return ((y * 580) / 100) + 15;
    };

    return TSP;

  })();

  window.TSP = TSP;

}).call(this);
