(function() {
  var TSP;

  TSP = (function() {

    TSP.prototype.name = null;

    TSP.prototype.comment = null;

    TSP.prototype.x = null;

    TSP.prototype.y = null;

    TSP.prototype.path = null;

    TSP.prototype.canvas = null;

    TSP.prototype.context = null;

    TSP.prototype.colors = ["#dcc", "#cdc", "#ccd", "#ddc", "#cdd", "#dcd", "#ecc", "#cec", "#cce", "#cde", "#ecd", "#dec"];

    function TSP(name, comment, x, y, path) {
      var i, _ref, _ref2;
      this.name = name;
      this.comment = comment;
      this.x = x;
      this.y = y;
      this.path = path;
      x = this.x.split(",");
      y = this.y.split(",");
      this.canvas = document.getElementById('map');
      this.context = this.canvas.getContext('2d');
      if (this.path) {
        path = this.path.replace("[", "").replace("]", "").replace(/\s+/g, ' ').split(", ");
        console.log("Path List: " + path);
        for (i = 0, _ref = path.length - 1; 0 <= _ref ? i <= _ref : i >= _ref; 0 <= _ref ? i++ : i--) {
          if (path[i] === path[path.length - 1]) {
            this.drawLine(x[path[i]], x[path[0]], y[path[i]], y[path[0]]);
          } else {
            this.drawLine(x[path[i]], x[path[i + 1]], y[path[i]], y[path[i + 1]]);
          }
        }
      }
      for (i = 0, _ref2 = x.length; 0 <= _ref2 ? i <= _ref2 : i >= _ref2; 0 <= _ref2 ? i++ : i--) {
        this.drawNode(x[i], y[i], i);
      }
    }

    TSP.prototype.drawNode = function(x, y, i) {
      this.context.fillStyle = this.colors[i % this.colors.length];
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

    TSP.prototype.drawLine = function(x1, x2, y1, y2) {
      this.context.strokeStyle = "#bbb";
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
