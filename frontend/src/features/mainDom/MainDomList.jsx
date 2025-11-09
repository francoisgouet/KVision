import React, { useEffect, useState } from "react";
import { getMainDom } from "../../api/mainDomApi";

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
      <h2>Liste des utilisateurs</h2>
      <ul>
        {MainDom.map((u) => (
          <li key={u.id}>{u.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default MainDomList;
