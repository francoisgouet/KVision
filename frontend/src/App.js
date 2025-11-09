"use client";
import React, { useEffect, useState } from "react";
import MainDomList from "./features/mainDom/MainDomList";
function App() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/api/maindom/")
      .then(response => response.text())
      .then(data => setMessage(data))
      .catch(error => console.error(error));
  }, []);

  return (
    <div>
      <h1>KVision</h1>
      <p>Message du backend : {message}</p>
	    <MainDomList />
	</div>
  );
}

export default App;
