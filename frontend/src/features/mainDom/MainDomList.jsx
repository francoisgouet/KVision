// use client pour la gestion avec 
'use client' 
import React, { useEffect, useState } from "react";
import { getMainDom } from "../../api/mainDomApi";
import Link from 'next/link';


function MainDomList() {
  
  const [MainDom, setMainDom] = useState([]);

  useEffect(() => {
    getMainDom()
      .then((data) => {
        console.log("Réponse API :", data); // 👈 debug
        setMainDom(data);
      })
      .catch((err) => console.error("Erreur fetch :", err));
  }, []);

  return (
    <div>
      <h2>Bienvenue</h2>
      <ul>
        {MainDom.map((u) => (
          //<li key={u.id}>{u.lib}</li>
          <Link key={u.id} href={u.lib}>{u.lib}</Link>
        ))}
      </ul>
    </div>
  );
}

export default MainDomList;   