
function logmsg(msg) {
    console.log(msg);
}

logmsg("Loading index2.js");

function processdata(data){
    logmsg("Loaded data");
    logmsg(data);
    var treemapLayout = d3.treemap();
    treemapLayout
        .size([700, 700])
        .paddingOuter(10);
    //logmsg(data);
    var rootNode = d3.hierarchy(data);
    rootNode.sum(function(d) { return d.value;})
    //logmsg(rootNode);
    treemapLayout(rootNode);
    //logmsg(rootNode);
    var nodes = d3.select('svg g')
    .selectAll('g')
    .data(rootNode.descendants())
    .enter()
    .append('g')
    .attr('transform', function(d) {return 'translate(' + [d.x0, d.y0] + ')'})
  
  nodes
    .append('rect')
    .attr('width', function(d) { return d.x1 - d.x0; })
    .attr('height', function(d) { return d.y1 - d.y0; })
  
  nodes
    .append('text')
    .attr('dx', 4)
    .attr('dy', 14)
    .text(function(d) {
      return d.data.name;
    })
  
    var scl = d3.scaleLinear();
    scl.domain(d3.extent(data, d => d["value"]));
    scl.range([0,500]);
    /*d3.select( "svg" )
        .append( "g" ).attr( "id", "tst1" )
        .selectAll( "text" )
        .data(data)
        .enter()
        .append("text")
        .attr( "color", "green" )
        .attr( "cy", d => scl(d["y1"]) );*/

}

function onload(){
    d3.json(
        "http://localhost:8080/init?directory=C%3A%5CUsers%5Cshri%5CDocuments%5Cdocscan%5Csrikanth%5CSLEmp"
    ).then( 
        //function(data){console.log(data);}
        processdata
    );
}
