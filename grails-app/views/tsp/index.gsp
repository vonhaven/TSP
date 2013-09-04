<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>NDH's TSP</title>
	</head>
	<body>
    <div id="main">
      <div class="title">
        TSP: Brute Force Edition
      </div>
      <div class="content">
        The Traveling Salesperson Problem<br/>
        CECS 545<br/>
        Solution by Nicholas Harshfield
      </div>
      <g:each var="tsp" in="${tsps}">
        <a href="/TSP/${tsp.name}">
          ${tsp.name}
        </a>
      </g:each>
      <div>activeProblem: ${activeProblem}</div>
      <g:if test="${activeProblem}">
        <canvas width="800" height="600"/>
      </g:if>
    </div>
	</body>
</html>
