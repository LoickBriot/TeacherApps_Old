function httpAsync(theUrl, method, data, callback)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            callback(JSON.parse(xmlHttp.responseText));
    };
    xmlHttp.open(method, theUrl, true); // true for asynchronous
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(JSON.stringify(data));
}

function getProjectsTasksStates(callback) {
    var dateFilter = d3.select('input[name="date-filter-group"]:checked').node().value;
    var elements = d3.select('input[name="elements-filter-group"]:checked').node().value;
    httpAsync("/welcome/ajaxapi/shared/AjaxApi_WelcomePage/getProjectsTasksStates", "POST", {elements: elements, dateFilter: dateFilter}, loadData);
}

d3.selectAll('input[name="date-filter-group"]').on("change", function() { getProjectsTasksStates() });
d3.selectAll('input[name="elements-filter-group"]').on("change", function() { getProjectsTasksStates() });

var zoom = d3.zoom()
    .scaleExtent([1, 10])
    .on("zoom", zoomed);

var margin = {top: 20, right: 20, bottom: 200, left: 40},
container = d3.select(".svg"),

widthFull = parseInt(container.style("width"), 10),
heightFull = 600,
aspect = widthFull / heightFull,
widthContent = widthFull - margin.left - margin.right,
heightContent = heightFull - margin.top - margin.bottom;

var svg = container
    .append("svg")
        .attr("width", widthFull)
        .attr("height", heightFull)
        .attr("viewBox", "0 0 "+ widthFull +" " + heightFull +"")
        .attr("preserveAspectRatio", "xMidYMid")
        .attr("id", "chart")
    .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
        .call(zoom);

svg.append("rect")
    .attr("width", widthFull)
    .attr("height", heightFull)
    .style("fill", "none")
    .style("pointer-events", "all");

var elementsFilterContainer = container.append("ul").attr("class", "elements-toggle");

var x0 = d3.scaleBand().rangeRound([0, widthContent]).padding(0.2),
    x1 = d3.scaleBand().padding(0.2);
    y = d3.scaleLinear().rangeRound([heightContent, 0]);

var g = svg.append("g");
    // .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

var axisX = g.append("g")
    .attr("class", "axis axis--x")
    .attr("transform", "translate(0," + heightContent + ")");

var axisY = g.append("g")
    .attr("class", "axis axis--y");

getProjectsTasksStates();


function color(name) {
    switch(name) {
        case "done": return "#66BB6A"; break;
        case "error": return "#ef5350"; break;
        case "in_progress": return "#26C6DA"; break;
        case "todo": return "black"; break;
    }
}

function zoomed() {
    g.attr('transform', 'translate(' + d3.event.transform.x + ', 1) scale(' + d3.event.transform.k + ', 1)');
}

function loadData(data) {
    var elemDataFilterContainer = elementsFilterContainer.selectAll('.element-name a')
        .data(data);
    elemDataFilterContainer
        .enter()
        .append("li")
        .attr("class", "element-name")
        .append("a");

    elemDataFilterContainer
        .exit().remove();

    elemDataFilterContainer = elementsFilterContainer.selectAll('.element-name a')
        .data(data);

    elemDataFilterContainer
        .html(function(d) { return d.name + "  " })
        .style("text-decoration", null)
        .on("click", function(d, i) {
            if(d3.select(this).style("text-decoration") === "line-through") {
                d3.select(this).style("text-decoration", null);
                d3.select(this).select("span").attr("class", "fa fa-eye");
                delete data[i].disabled;
            } else {
                d3.select(this).style("text-decoration", "line-through");
                d3.select(this).select("span").attr("class", "fa fa-eye-slash");
                data[i].disabled = true;
            }
            updateWithFilters();
        })
        .append("span")
        .attr("class", function() {
            if(d3.select(this.parentNode).style("text-decoration") === "line-through") {
                return "fa fa-eye-slash"
            } else {
                return "fa fa-eye"
            }
        });

    function updateWithFilters() {
        update(data.filter(function(e) { console.log(e); return e.disabled !== true}))
    }

    update(data);
}

function update(data) {
    var states = [];
    data.forEach(function(d) {
        d.states.forEach(function(s) {
            if(states.indexOf(s.state) <= -1) states.push(s.state)
        })
    });


    x0.domain(data.map(function(d) { return d.name + "(" + d.id + ")" }));
    x1.domain(states).rangeRound([0, x0.bandwidth()]);
    y.domain([0, d3.max(data, function(d) { return d3.max(d.states, function(t) {return t.count})})]);


    axisX.call(d3.axisBottom(x0))
    .selectAll("text")
        .attr("y", 0)
        .attr("x", 9)
        .attr("transform", "rotate(45)")
        .attr("dy", ".35em")
        .style("font-size", "12px")
        .style("text-anchor", "start")
        .style("opacity", 0)
        .transition()
        .duration(800)
        .style("opacity", 1);

    axisY.call(
        d3.axisLeft(y)
            .tickSize(-x0.step() * data.length)
            .ticks(10)
    );

    var projects = g.selectAll(".project")
        .data(data, function(d) {return d.id + d.name});

    projects
        .enter().append("g")
        .attr("class", "project")
        .attr("transform", function(d) {return "translate(" + x0(d.name + '(' + d.id + ')') + ",0)"; });

    projects
        .exit()
        .attr("class", null)
        .selectAll("rect")
        .attr("class", null)
        .transition()
        .duration(500)
        .attr("y", heightContent)
        .attr("height", "0")
        .style("opacity", "0");

    projects.exit()
        .transition()
        .duration(500)
        .style("opacity", "0")
        .remove();

    //Force update project elements
    projects = g.selectAll(".project")
        .data(data, function(d) {return d.id + d.name});

    var projectElements = projects.selectAll("rect")
        .data(function(d) {return d.states});

    projectElements
        .enter().append("rect")
        .attr("class", "bar")
        .attr("width", x1.bandwidth())
        .attr("x", function(d) { return x1(d.state); })
        .attr("y", heightContent)
        .attr("stroke", "black")
        .style("fill", function(d) { return color(d.state); })
        .style("opacity", 0)
    // .style("rx", "9.9");
        .on('mouseover', function(d){
        var barPos = d3.select(this).node().getBoundingClientRect();
        var xPosition = barPos.left + d3.select(this).attr("width") / 2;
        var yPosition = barPos.top + window.scrollY;

        d3.select(this).style("opacity", 1);

        var div = d3.select(".svg").append("div")
            .attr("class", "tetrao-tooltip")
            .style("opacity", 0)
            .style("opacity", 1);
        var table = div.append("table");
        d3.select(this.parentNode).data()[0].states.forEach(function(state) {
            var tr = table.append("tr").attr("cellpadding", "5");
            if(state.state === d.state) {
                tr.attr("class", "text-selected")
            }
            tr.append("td").html(state.state.toUpperCase());
            tr.append("td").html(state.count).style("text-align", "right")
        });
        var divRect = div.node().getBoundingClientRect();

        div.style("left", (xPosition - divRect.width / 2) + "px")
            .style("top", (yPosition - divRect.height) + "px");
    })
        .on('mouseout', function(d) {
            d3.select(this).style("opacity", .8);
            d3.select(".tetrao-tooltip").remove();
        });


    //Force update projectElements
    projectElements = projects.selectAll("rect")
        .data(function(d) {return d.states}, function(d) {return d.state});

    projectElements.transition()
        .duration(500)
        .attr("width", x1.bandwidth())
        .attr("x", function(d) { return x1(d.state); })
        .attr("height", function(d) { return heightContent - y(d.count); })
        .attr("y", function(d) { return y(d.count); })
        .style("fill", function(d) { return color(d.state); })
        .style("opacity", .8);

    projectElements.exit().transition()
        .duration(500)
        // .attr("width", x1.bandwidth())
        // .attr("x", function(d) { return x1(d.state); })
        .attr("y", heightContent)
        .attr("height", "0")
        .style("opacity", "0")
        .remove();

    //Update positions after the bars have been updated
    projects.transition()
        .duration(500)
        .attr("transform", function(d) {return "translate(" + x0(d.name + '(' + d.id + ')') + ",0)"; });
}


//Resize
$(window).on("resize", function() {
    var svgElement = $("#chart");
    var svgParent = svgElement.parent();

    var targetWidth = svgParent.width();
    svgElement.attr("width", targetWidth);
    svgElement.attr("height", Math.round(targetWidth / aspect));

}).trigger("resize");

