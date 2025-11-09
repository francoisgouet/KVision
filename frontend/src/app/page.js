'use client'
import React from "react";
import MainDomList from "../features/mainDom/MainDomList";
import { useParams } from 'next/navigation';
function IndexPage() {
    const params = useParams();
  const slug = params.slug; // Si ton chemin est /[slug]

  return (
    <div>
      <h1>KVision</h1>
      <p>Message du backend :</p>
      <h1>Bienvenue sur la page : {slug}</h1>
	    <MainDomList />
	</div>
  );
}

export default IndexPage;