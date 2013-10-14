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
                    I.&nbsp;&nbsp;&nbsp;Random
                </a>
                <a class="item" href="loadSet/brute">
                    II.&nbsp;&nbsp;Brute Force
                </a>
                <a class="item" href="loadSet/bfs">
                    III. Breadth-First Search
                </a>
                <a class="item" href="loadSet/dfs">
                    IV.&nbsp;&nbsp;Depth-First Search
                </a>
                <a class="item" href="loadSet/greed">
                    V.&nbsp;&nbsp;&nbsp;Greedy Heuristic
                </a>
                <a class="item" href="loadSet/genetic">
                    VI.&nbsp;&nbsp;Genetic Algorithm
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
                    <span class="bigger blue">
                        File
                    </span>
                    ${activeProblem.name}
                    <br/>
                    <span class="bigger green">
                        Dimension
                    </span>
                    ${activeProblem.dimension} nodes
                    <br/>
                    <g:if test="${tspSet == 'genetic'}">
                        <span class="bigger">
                            Generations
                        </span>
                        ${generations}
                        <br/>
                        <span class="bigger red">
                            Options
                        </span>
                        <a class="generation" href="setGenerations/1">1</a>
                        <a class="generation" href="setGenerations/5">5</a>
                        <a class="generation" href="setGenerations/25">25</a>
                        <a class="generation" href="setGenerations/100">100</a>
                        <a class="generation" href="setGenerations/250">250</a>
                        <a class="generation" href="setGenerations/500">500</a>
                        <a class="generation" href="setGenerations/1000">1k</a>
                        <a class="generation" href="setGenerations/2000">2k</a>
                        <a class="generation" href="setGenerations/5000">5k</a>
                    </g:if>
                </div>
                <div class="content">
                    <a class="tsp" href="solve/${activeProblem.id}">
                        Solve
                    </a>
                    <g:if test="${path}">
                        ${prettyPath}
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
