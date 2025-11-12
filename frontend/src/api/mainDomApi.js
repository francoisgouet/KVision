// api/mainDomApi.js
export async function getMainDom() {
  const res = await fetch("http://localhost:8080/api/maindom/");
  if (!res.ok) {
    console.error(res.status);
    throw new Error("Erreur API")
  };
  return res.json();
}

export async function getMainDomD3() {
  const res = await fetch("http://localhost:8080/api/dom/D3/maindom");
  if (!res.ok) {
    console.error(res.status);
    throw new Error("Erreur API")
  };
  return res.json();
}

export async function getAllDomD3() {
  //const res = await fetch("http://localhost:8080/api/dom/D3");
  const res = await fetch("http://localhost:8080/api/dom/D3/dom");
  if (!res.ok) {
    console.error(res.status);
    throw new Error("Erreur API")
  };
  return res.json();
}