<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>NDH's TSP</title>
	</head>
	<body>
    <div id="main">
      <div class="title">
        The Traveling Salesman Problem
        <div class="subtitle">
          by Nicholas Harshfield | CECS-545 | Fall 2013
        </div>
      </div>
      <div class="content">
        I. The Brute Force Solution
      </div>
      <div class="content">
      <g:each var="tsp" in="${tsps}">
        <a class="tsp" href="/TSP/${tsp.name}">
          ${tsp.name}
        </a>
      </g:each>
      </div>
      <div class="content">
        activeProblem: ${activeProblem.name}
      </div>
      <g:if test="${activeProblem}">
        <canvas id="map" width="800" height="600"/>
        <g:javascript src="TSP.js"/>
        <script type="text/JavaScript">
          var tsp = new TSP("${activeProblem.name}", "${activeProblem.comment}", "");
        </script>
      </g:if>
    </div>
	</body>
</html>
