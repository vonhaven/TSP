<!DOCTYPE html>
<html>
    <head>
		<meta name="layout" content="main"/>
		<title>NDH's TSP</title>
	</head>
	<body>
        <div id="main">
            <div class="title">
                <a href="reset">
                    The Traveling Salesman Problem
                </a>
                <div class="subtitle">
                    by Nicholas Harshfield | CECS-545 | Fall 2013
                </div>
            </div>
            <div class="content subtitle">
                <a class="item" href="loadSet/random">
                    I.&nbsp;&nbsp;&nbsp;Random Hamiltonian Cycle
                </a>
                <a class="item" href="loadSet/brute">
                    II.&nbsp;&nbsp;Brute Force Best Hamiltonian Cycle
                </a>
                <a class="item" href="loadSet/bfs">
                    III. Search Breadth-First for Shortest Path
                </a>
                <a class="item" href="loadSet/dfs">
                    IV.&nbsp;&nbsp;Search Depth-First for Shortest Path
                </a>
            </div>
            <g:if test="${tsps}">
                <div class="content">
                    <g:each var="tsp" in="${tsps}">
                        <a class="tsp" href="load/${tsp.id}">
                            ${tsp.name}
                        </a>
                    </g:each>
                </div>
            </g:if>
            <g:if test="${activeProblem}">
                <div class="content">
                    <span class="bigger blue">File</span>${activeProblem.name}<br/>
                    <span class="bigger green">Dimension</span>${activeProblem.dimension} nodes
                </div>
                <div class="content">
                    <a class="tsp" href="solve/${activeProblem.id}">
                        Solve
                    </a>
                    <g:if test="${path}">
                        ${path}
                    </g:if>
                </div>
                <canvas id="map" width="800" height="600"/>
                <g:javascript src="TSP.js"/>
                <script type="text/JavaScript">
                    var tsp = new TSP(
                        "${activeProblem.name}", 
                        "${activeProblem.comment}", 
                        "${activeProblem.x}", 
                        "${activeProblem.y}",
                        "${path}" 
                    );
                </script>
            </g:if>
        </div>
	</body>
</html>
