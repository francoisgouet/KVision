// MainDomListContainer.jsx (Smart)
import React, { useEffect, useState } from "react";
import { getUsers } from "../api/MainDomApi";
import MainDomList from "../components/MainDomList";

const MainDomListContainer = () => {
  const [MainDom, setMainDom] = useState([]);

  useEffect(() => {
    getMainDom().then(setMainDom);
  }, []);

  return <MainDomList MainDom={MainDom} />;
};

export default MainDomListContainer;
