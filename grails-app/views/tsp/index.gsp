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
                <a class="subtitle" href="reset">
                    I. Brute Force
                </a>
            </div>
            <div class="content">
                <g:each var="tsp" in="${tsps}">
                    <a class="tsp" href="load/${tsp.name}">
                        ${tsp.name}
                    </a>
                </g:each>
            </div>
            <g:if test="${activeProblem}">
                <div class="content">
                    <span class="bigger red">File</span>${activeProblem.name}<br/>
                    <span class="bigger green">Comments</span>${activeProblem.comment}<br/>
                    <span class="bigger blue">Dimension</span>${activeProblem.dimension} nodes
                </div>
                <div class="content">
                    <a class="tsp" href="solve/${activeProblem.name}">
                        Solve!
                    </a>
                </div>
                <canvas id="map" width="800" height="600"/>
                <g:javascript src="TSP.js"/>
                <script type="text/JavaScript">
                    var tsp = new TSP(
                        "${activeProblem.name}", 
                        "${activeProblem.comment}", 
                        "${activeProblem.dimension}", 
                        "${activeProblem.x}", 
                        "${activeProblem.y}"
                    );
                </script>
            </g:if>
        </div>
	</body>
</html>
