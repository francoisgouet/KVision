'useClient'
import React from "react";
import MainDomList from "../features/mainDom/MainDomList";
function LayoutPage({ children }) {
  
  return (
    <html>
+      <body>{children}
    <div>	    
    </div>
  </body>
  </html>
  );
}

export default LayoutPage;