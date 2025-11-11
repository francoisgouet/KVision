import * as d3 from 'd3';
import { getMainDomD3 } from "../../../api/mainDomApi";

const datas = await getMainDomD3();

var margin = { top: 20, right: 90, bottom: 30, left: 90 };
const width = 400, height = 300;
const treeLayout = d3.tree().size([height, width - 120]); // Définir la taille de l'arbre

var root = d3.hierarchy(datas, d => d.children); // Créer une hiérarchie D3 à partir des données
var svg;

function collapse(node) {
    if (node.children) {
        node._children = node.children;
        node._children.forEach(collapse);
        node.children = null;
    }
}

function update(source) {
    const treeData = treeLayout(root);

    const nodes = treeData.descendants()
    const links = treeData.links();

    constructNodes(svg, nodes, source);
    constructLinks(svg, links, source);

    // Save positions for transition
    nodes.forEach(d => {
        d.x0 = d.x;
        d.y0 = d.y;
    });
}

// Fonction pour créer le chemin courbé entre deux points
function diagonal(s, t) {
    return `M${s.y},${s.x}
          C${(s.y + t.y) / 2},${s.x}
           ${(s.y + t.y) / 2},${t.x}
           ${t.y},${t.x}`;
}

function click(event, d) {
    if (d.children) {
        d._children = d.children;
        d.children = null;
    } else {
        d.children = d._children;
        d._children = null;
    }
    update(d);
}

function D3Init(svgRef) {

    //const height = right.x - left.x + margin.top + margin.bottom;
    //const width = window.innerWidth - 50;
    //const height = 400;
    //const margin = 20; // Marge autour de l'arbre
    const initialZoom = 1;

    const viewBoxWidth = width - 2 * margin;
    const viewBoxHeight = height - 2 * margin;

    svg = d3.select(svgRef.current)
        .attr("viewBox", `0 0 ${viewBoxWidth} ${viewBoxHeight}`)
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    svg.style("border", "1px solid red");
    //.append("g")
    //.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    /*svg.append("circle")
        .attr("cx", 100)
        .attr("cy", 100)
        .attr("r", 50)
        .style("fill", "blue");*/

    root.x0 = height / 2;
    root.y0 = 0;

    // Collapse all children initially
    root.children.forEach(collapse);

    update(root);
}

function constructNodes(svg, nodes, source) {
    // Nodes SECTION
    const node = svg.selectAll('g.node')
        .data(nodes);

    const nodeEnter = node.enter()
        .append('g')
        .attr('class', 'node')
        .attr('transform', d => `translate(${d.y},${d.x})`)
    //.on('click', click);

    nodeEnter.append('circle')
        .attr('r', 8)
        .style('fill', d => d._children ? 'lightsteelblue' : '#c81a1aff')
        .on('click', click);

    nodeEnter.append('text')
        .attr('dy', 3)
        .attr('x', d => d.children || d._children ? -14 : 14)
        .attr('text-anchor', d => d.children || d._children ? "end" : "start")
        .text(d => d.data.name);


    // Transition nodes to their new position.
    const nodeUpdate = node.merge(nodeEnter)
        //.transition(transition)
        .attr("transform", d => `translate(${d.y},${d.x})`)
        .attr("fill-opacity", 1)
        .attr("stroke-opacity", 1);

    // Transition exiting nodes to the parent's new position.
    const nodeExit = node.exit()
        //.transition(transition).remove()
        .attr("transform", d => `translate(${source.y},${source.x})`)
        .attr("fill-opacity", 0)
        .attr("stroke-opacity", 0);

}

function constructLinks(svg, links, source) {
    // Links SECTION
    const link = svg.selectAll('path.link')
        .data(links, d => d.target.id);

    // Enter any new links at the parent's previous pos
    const linkEnter = link.enter()
        .append('path')
        .attr('class', 'link')
        //.attr('fill', 'none')
        .attr('stroke', '#e99696ff')
        .attr('d', d => {
            /*return`
      M${d.source.y},${d.source.x}
      C${(d.source.y + d.target.y) / 2},${d.source.x}
       ${(d.source.y + d.target.y) / 2},${d.target.x}
       ${d.target.y},${d.target.x}
    `*/
            // subtil mais ici crée juste la position initial parent
            const o = { x: source.x0, y: source.y0 }
            return diagonal(o, o);
        });

    // Fusion des nouveaux liens avec les existants, puis transition
    const linkUpadate = link.merge(linkEnter);

    linkUpadate.transition()
        .duration(100) // durée de l'animation en ms
        .attrTween('d', function (d) {
            const previous = this.getAttribute('d');
            const current = diagonal(d.source, d.target);
            const interpolator = d3.interpolateString(previous, current);
            return t => interpolator(t);
        });
    //.tween("resize", window.ResizeObserver ? null : () => () => svg.dispatch("toggle"))
    /* .attr(`
    M${d.source.y},${d.source.x}
    C${(d.source.y + d.target.y) / 2},${d.source.x}
     ${(d.source.y + d.target.y) / 2},${d.target.x}
     ${d.target.y},${d.target.x}
  `)*/

    const linkExit = link.exit().transition().remove()
        .duration(500)
        .attr('d', function (d) {
            const previous = this.getAttribute('d');
            const current = diagonal(d.source, d.target);
            const interpolator = d3.interpolateString(previous, current);
            return t => interpolator(t);
        })
    //.remove();

}


export { D3Init }