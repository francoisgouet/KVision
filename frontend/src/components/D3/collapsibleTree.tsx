'use client'
import React, { useState, useRef, useEffect } from 'react';

import { D3Init } from './utils/construct.js';
function CollapsibleTree({ data }) {

    const svgRef = useRef<SVGSVGElement>(null);
    //const [collapsedNodes, setCollapsedNodes] = useState([]);
    useEffect(() => {
        if (svgRef.current) {
            var root = D3Init(svgRef)   
        }
    }, [data]); // Déclencher la mise à jour lorsque les données changent

    return (<>
        <svg ref={svgRef}></svg>
    </>
    );
}

export default CollapsibleTree;