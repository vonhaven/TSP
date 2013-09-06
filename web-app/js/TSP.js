(function() {
  var TSP;

  TSP = (function() {

    TSP.prototype.name = null;

    TSP.prototype.comment = null;

    TSP.prototype.nodes = null;

    TSP.prototype.canvas = null;

    TSP.prototype.context = null;

    function TSP(name, comment, nodes) {
      this.name = name;
      this.comment = comment;
      this.nodes = nodes;
      alert("" + this.name + ": " + this.comment);
      this.canvas = document.getElementById('map');
      this.context = this.canvas.getContext('2d');
    }

    return TSP;

  })();

  window.TSP = TSP;

}).call(this);
